package edu.ucr.cs.cs167.nnlap10

/**
 * @author ${user.name}
 */

import org.apache.spark.SparkConf
import org.apache.spark.ml.classification.{LinearSVC, LinearSVCModel}
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.feature.{HashingTF, StringIndexer, Tokenizer}
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.tuning.{ParamGridBuilder, TrainValidationSplit, TrainValidationSplitModel}
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
/**
 * @author ${user.name}
 */
object App {
  def main(args : Array[String]) {

    if (args.length != 1) {
      println("Usage <input file>")
      println("  - <input file> path to a CSV file input")
      sys.exit(0)
    }
//    val inputfile="C:\\Users\\ADMIN\\cs167\\workspace\\sentiment.csv"
    val inputfile = args(0)
    val conf = new SparkConf
    val mastersv="local[*]" //"spark://localhost:7077"
    if (!conf.contains("spark.master"))
      conf.setMaster(mastersv)
    println(s"Using Spark master '${conf.get("spark.master")}'")

    val spark = SparkSession
      .builder()
      .appName("CS167 Lab10")
      .config(conf)
      .getOrCreate()

    val t1 = System.nanoTime
    try {
      // process the sentiment data
      // TDOO A: read CSV file as a DataFrame
      //val sentimentData: DataFrame = // ...
      val sentimentData: DataFrame = spark.read.format("csv")
        .option("header", "true")
        .option("quote", "\"")
        .option("escape", "\"")
        .load(inputfile)
      sentimentData.printSchema()
      sentimentData.show()
      // TODO B: tokenize text (sentences) to array of words
      //val tokenzier = // ...
      val tokenizer = new Tokenizer()
        .setInputCol("text")
        .setOutputCol("words")
      val tokenizedData = tokenizer.transform(sentimentData)
      tokenizedData.select("text", "words").show()
      // TODO C: transform words to numeric features using HashingTF
      //val hashingTF = // ...
      val hashingTF = new HashingTF()
        .setInputCol("words")
        .setOutputCol("features")
        .setNumFeatures(1024)
      val featurizedData = hashingTF.transform(tokenizedData)
      featurizedData.select("words", "features").show()
      // TODO D: transform labels to numbers
      //val stringIndexer = // ...

      val stringIndexer = new StringIndexer()
        .setInputCol("sentiment")
        .setOutputCol("label")
        .setHandleInvalid("skip")
      val indexedData = stringIndexer.fit(featurizedData).transform(featurizedData)
      indexedData.select("sentiment", "label").show()
      // TODO E: create an object for the Linear Support Vector Machine classifier
      //val svc = // ...
      val svc = new LinearSVC()
        .setFeaturesCol("features")
        .setLabelCol("label")
        .setMaxIter(10)
        .setRegParam(0.1)
      // TODO F: create a pipeline that includes all the previous transofrmaitons and the model
      //val pipeline = // ...

      val pipeline = new Pipeline()
        .setStages(Array(
          tokenizer,
          hashingTF,
          stringIndexer,
          svc))
      // TODO G: create a parameter grid to corss validate the model on different hyper parameters
      // val paramGrid: Array[ParamMap] = new ParamGridBuilder()
      //   .addGrid(/* ... */)
      //   .addGrid(/* ... */)
      //   .build()
      val paramGrid: Array[ParamMap] = new ParamGridBuilder()
        .addGrid(hashingTF.numFeatures, Array(1024, 2048))
        .addGrid(svc.fitIntercept, Array(true, false))
        .addGrid(svc.regParam, Array(0.01, 0.0001))
        .addGrid(svc.maxIter, Array(10, 15))
        .addGrid(svc.threshold, Array(0.0, 0.25))
        .addGrid(svc.tol, Array(0.0001, 0.01))
        .build()
      // TDOO H: create a corss validation job that will process the pipeline using all possible combinations in the parameter grid
      val cv = new TrainValidationSplit()
        .setEstimator(pipeline)
        .setEvaluator(new BinaryClassificationEvaluator())
        .setEstimatorParamMaps(paramGrid)
        .setTrainRatio(0.8)
        .setParallelism(2)

      // TODO I: split the data into 80% train and 20% test
      val Array(trainingData: Dataset[Row], testData: Dataset[Row]) = sentimentData.randomSplit(Array(0.8, 0.2))
      // TODO J: Run cross-validation, and choose the best set of parameters.
      val model: TrainValidationSplitModel = cv.fit(trainingData)
      // TODO K: get the parameters of the best model and print them
      val numFeatures: Int = model.bestModel.asInstanceOf[PipelineModel].stages(1).asInstanceOf[HashingTF].getNumFeatures
      val fitIntercept: Boolean = model.bestModel.asInstanceOf[PipelineModel].stages(3).asInstanceOf[LinearSVCModel].getFitIntercept
      val regParam: Double = model.bestModel.asInstanceOf[PipelineModel].stages(3).asInstanceOf[LinearSVCModel].getRegParam
      val maxIter: Double = model.bestModel.asInstanceOf[PipelineModel].stages(3).asInstanceOf[LinearSVCModel].getMaxIter
      val threshold: Double = model.bestModel.asInstanceOf[PipelineModel].stages(3).asInstanceOf[LinearSVCModel].getThreshold
      val tol: Double = model.bestModel.asInstanceOf[PipelineModel].stages(3).asInstanceOf[LinearSVCModel].getTol
      println("Best parameters of the best model:")
      println(s"numFeatures: $numFeatures")
      println(s"fitIntercept: $fitIntercept")
      println(f"regParam: $regParam%.4f")
      println(f"maxIter: $maxIter%.4f")
      println(f"threshold: $threshold%.4f")
      println(f"tol: $tol%.4f");
      // TODO L: apply the model to your test set and show sample of the result
      val predictions: DataFrame = model.transform(testData)
      predictions.select("text", "sentiment", "label", "prediction").show()

      // TODO M: evaluate the test results
      val binaryClassificationEvaluator = new BinaryClassificationEvaluator()
        .setLabelCol("label")
        .setRawPredictionCol("prediction")
      val accuracy: Double = binaryClassificationEvaluator.evaluate(predictions)
      println(s"Accuracy of the test set is $accuracy")


      val t2 = System.nanoTime
      println(s"Applied sentiment analysis algorithm on input $inputfile in ${(t2 - t1) * 1E-9} seconds")
    } finally {
      spark.stop
    }
  }

}

