Lab 10: Design and Implementation of Machine Learning Pipelines on Big Data with Spark Mllib
1.	Cài đặt phần mềm cần thiết
1.1.	Tạo project CS167
-	Vào C:/Users/<your-username-directory> -> Tạo thư mục mới với tên : cs167
 

1.2.	Cài đặt JDK 1.8
-	Truy cập vào link: https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html
 
-	Down load “Window x64 Installer”
-	Chạy file vừa mới tải về
-	Nhấn “Next”
 
 
-	Nhấn “Change”
 
-	Chọn đường dẫn tới thư mục vừa tạo, thêm jdk vào đường dẫn Folder name -> OK
 
 
-	Next -> Đợi quá trình cài đặt hoàn tất -> Close









-	Cấu hình Java home
 
 



-	Hiển thị version Java-
-	JAVA -VERSION
-	JAVAC -VERSION

 
1.3.	Cài đặt Apache Maven
-	Truy cập vào link: https://maven.apache.org/download.cgi
 
-	Download Link “Binary zip archive”
-	Vào directory chứa file zip vừa download
-	Chuột phải -> Extract All... -> Browse
 
-	Dẫn tới thư mục chứa lab, tạo thư mục mới với tên maven:
 
 




-	Chọn “Extract”
 
1.4.	Cài đặt IntelliJ IDEA Community Edition
-	Truy cập vào link: https://www.jetbrains.com/idea/download/?section=windows
-	Chọn phiên bản IntelliJ IDEA Community Edition -> Download
 
-	Chạy file vừa download về
-	Nhấn  “Next”
 
-	Nhấn “Next”
 
-	Chọn Add “bin” folder to the PATH -> Next -> Install
 
-	Reboot now -> Finish
 
1.5.	Cài đặt biến môi trường
1.5.1.	Thêm JAVA_HOME
-	Windows + R, nhập rundll32.exe sysdm.cpl,EditEnvironmentVariables -> OK
 
-	Ở mục User variable for <user-name>, New -> Nhập Variable name: JAVA_HOME -> Chọn Browse Directory... -> chọn tới thư mục nơi chứa jdk đã cài đặt trước đó -> OK.
 
1.5.2.	Thêm MAVEN_HOME
-	Tương tự với MAVEN_HOME
 
1.5.3.	Thêm java và maven vào PATH
-	Chọn Path
 
-	Nhập %JAVA_HOME%\bin và %MAVEN_HOME%\bin -> OK
 
-	Chọn “OK”
 
1.5.4.	Kiểm tra môi trường
-	Kiểm tra: vào cmd -> gõ javac -version và mvn -version, nếu hiển thị như dưới ảnh -> Thành công
 

1.6 Cài đặt hadoop
1.6.1 Tải và Thêm HADOOP_HOME
Hadoop yêu cầu Java Development Kit (JDK) để hoạt động. Nếu chưa cài thì quay lại bước 1.2 
Các bước cài đặt Hadoop bao gồm:
-	Tải xuống phiên bản Hadoop từ trang chủ của Apache Hadoop ở link sau:
https://mirror.downloadvn.com/apache/hadoop/common/hadoop-3.3.0/hadoop-3.3.0.tar.gz
-	Giải nén gói Hadoop vừa tải xuống vào ổ D.
-	Cấu hình các tập tin cần thiết để Hadoop hoạt động.
Tương tự với các bước setup JDK, thiết lập các biến và đường dẫn mới cho Hadoop
 
-	Ở mục “System variables” chọn mục “Path” và nhấn “Edit”.
 
-	Chọn New và thêm các đường dẫn sau  “D:\hadoop\bin” và “D:\hadoop\sbin”
 

1.6.2 Cấu hình các tập tin cho Hadoop
Các tập tin cấu hình chính của Hadoop bao gồm:
●	core-site.xml: Cấu hình các thiết lập chung cho Hadoop.
●	hdfs-site.xml: Cấu hình các thiết lập cho HDFS.
●	mapred-site.xml: Cấu hình các thiết lập cho MapReduce.
●	yarn-site.xml: Cấu hình các thiết lập cho YARN (Yet Another Resource Negotiator).
●	hadoop-env.cmd
-	Cấu hình core-site.xml như dưới đây:
<configuration>
   <property>
       <name>fs.defaultFS</name>
       <value>hdfs://localhost:9000</value>
   </property>
</configuration>
-	Cấu hình mapred-site.xml như dưới đây:
<configuration>
   <property>
       <name>mapreduce.framework.name</name>
       <value>yarn</value>
   </property>
</configuration>
-	Cấu hình hdfs-site.xml như dưới đây:


-	Tạo thư mục “data” trong “D:\hadoop\hadoop-3.3.0\data”
-	Tạo thư mục con “datanode” trong “D:\hadoop\hadoop-3.3.0\data”
-	Tạo thư mục con “namenode” trong “D:\hadoop\hadoop-3.3.0\data”



-	Sau đó cấu hình hdfs-site.xml như sau:
<configuration>
   <property>
       <name>dfs.replication</name>
       <value>1</value>
   </property>
   <property>
       <name>dfs.namenode.name.dir</name>
       <value>D:\hadoop\hadoop-hadoop-3.3.0\data\namenode</value>
   </property>
   <property>
       <name>dfs.datanode.data.dir</name>
       <value> D:\hadoop\hadoop-3.3.0\data\ datanode</value>
   </property>
</configuration>
Chú ý : Do lưu hadoop vào ổ đĩa D nên phần <value></value> cũng thay đổi tương ứng
-	Cấu hình yarn-site.xml như dưới đây:
<configuration>
   <property>
                <name>yarn.nodemanager.aux-services</name>
                <value>mapreduce_shuffle</value>
   </property>
   <property>
               <name>yarn.nodemanager.auxservices.mapreduce.shuffle.class</name>
               <value>org.apache.hadoop.mapred.ShuffleHandler</value>
   </property>
</configuration>
-	Cấu hình hadoop-env.cmd:
Mở file này lên và tìm tới lệnh:
▪	set JAVA_HOME=%JAVA_HOME%
sửa %JAVA_HOME% thành đường dẫn cài JDK trong ổ C:
▪	set JAVA_HOME=C:\Progra~1\Java\jdk1.8.0_211
Sau đó format lại namenode và datanode: 
Mở command line lên, gõ lệnh sau:
▪	hdfs namenode –format
▪	hdfs datanode -format
 
    Bước format này chỉ cần làm 1 lần.

1.6.3 Hoàn thành và chạy thử nghiệm
Gõ lệnh start-all.cmd và jps để kiểm tra
 
Sau khi gõ lệnh trên, hệ thống sẽ chạy Hadoop
Phải đảm bảo các ứng dụng sau được chạy:
– Hadoop Namenode
– Hadoop datanode
– YARN Resource Manager
– YARN Node Manager
 
Như vậy là ta đã khởi chạy thành công Hadoop.

1.6.	Cài đặt dự án
-	Khởi tạo dự án: Vào IntelliJ -> New Project
 
-	Chọn Maven Archetype -> Điền các thông tin như Name, Location: lưu trữ project ở thư mục cs167 đã tạo (chọn JDK 1.8)
-	Chọn jdk: 1.8
-	Catalog: Maven Cantral
-	Archetype: net.alchim31.maven:scala-archetype-simple
-	Version: 1.7
-	 
-	Chon create để tạo dự án.
-	Mở file pom.xml và bổ sung thêm
 
-	Trong pom.xml, thay thế các thành phần sau theo mẫu dưới đây
o   properties:
-        <spark.version>3.4.4</spark.version>
 
o   dependencies:
-        <dependency>
   <groupId>org.apache.spark</groupId>
   <artifactId>spark-core_${scala.compat.version}</artifactId>
   <version>${spark.version}</version>
   <scope>compile</scope>
 </dependency>

 <dependency>
   <groupId>org.apache.spark</groupId>
   <artifactId>spark-mllib_${scala.compat.version}</artifactId>
   <version>${spark.version}</version>
 </dependency>

 <dependency>
   <groupId>org.apache.spark</groupId>
   <artifactId>spark-sql_${scala.compat.version}</artifactId>
   <version>${spark.version}</version>
 </dependency>
 
Thay thế org.scala-lang


<dependency>
  <groupId>org.scala-lang</groupId>
  <artifactId>scala-library</artifactId>
  <version>2.12.15</version>
</dependency>


-	Bên góc phải -> chọn biểu tượng Maven -> Sync All Maven Projects
 
-	Nếu có lỗi xảy ra -> chọn biểu tượng Maven ->  Execute Maven Goal
 
-	Gõ clean -> Chọn mvn clean
 
-	Sau đó vào lại, gõ package -> Đợi cho đến khi load xong
 
-	Vào src -> main -> scala -> bigdata.group3 -> App.scala , thay thế toàn bộ bằng đoạn code dưới đây:
package edu.ucr.cs.cs167.nnlap10

import org.apache.spark.SparkConf
import org.apache.spark.ml.classification.{LinearSVC, LinearSVCModel}
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.feature.{HashingTF, StringIndexer, Tokenizer}
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.tuning.{ParamGridBuilder, TrainValidationSplit, TrainValidationSplitModel}
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object App {

  def main(args : Array[String]) {
    if (args.length != 1) {
      println("Usage <input file>")
      println("  - <input file> path to a CSV file input")
      sys.exit(0)
    }
    val inputfile = args(0)
    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
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

      // TODO B: tokenize text (sentences) to array of words
      //val tokenzier = // ...

      // TODO C: transform words to numeric features using HashingTF
      //val hashingTF = // ...

      // TODO D: transform labels to numbers
      //val stringIndexer = // ...

      // TODO E: create an object for the Linear Support Vector Machine classifier
      //val svc = // ...

      // TODO F: create a pipeline that includes all the previous transofrmaitons and the model
      //val pipeline = // ...

      // TODO G: create a parameter grid to corss validate the model on different hyper parameters
      // val paramGrid: Array[ParamMap] = new ParamGridBuilder()
      //   .addGrid(/* ... */)
      //   .addGrid(/* ... */)
      //   .build()

      // TDOO H: create a corss validation job that will process the pipeline using all possible combinations in the parameter grid

      // TODO I: split the data into 80% train and 20% test

      // TODO J: Run cross-validation, and choose the best set of parameters.

      // TODO K: get the parameters of the best model and print them

      // TODO L: apply the model to your test set and show sample of the result

      // TODO M: evaluate the test results

      val t2 = System.nanoTime
      println(s"Applied sentiment analysis algorithm on input $inputfile in ${(t2 - t1) * 1E-9} seconds")
    } finally {
      spark.stop
    }
  }
}

-	Truy cập vào đây để tải file sentiment.csv:
CS167/Labs/Lab10/sentiment.csv.zip at master · aseldawy/CS167
-	Chọn Dowload raw file
 
-	Tiến hành giải nén vào trong folder cs167

-	Di chuyển sentiment.csv vào thư mục lab10 (nơi chứa project)
 
-	Vào App -> Edit configurations
 
-	Ở mục program arguments -> thêm sentiment.csv -> OK
 

-	Chọn Apply -> OK
1.7.	Đọc dữ liệu đầu vào
1.7.1.	TODO A: Read CSV file as a DataFrame
-	Thêm đoạn code dưới đây:
val sentimentData: DataFrame = spark.read.format("csv")
  .option("header", "true")
  .option("quote", "\"")
  .option("escape", "\"")
  .load(inputfile)
sentimentData.printSchema()
sentimentData.show()

-	Nhấn vào nút Run để chạy
 
-	Kết quả:
 
1.8.	Đào tạo mô hình Máy vectơ hỗ trợ trên dữ liệu tình cảm
1.8.1.	TODO B: tokenize text (sentences) to array of words
val tokenizer = new Tokenizer()
  .setInputCol("text")
  .setOutputCol("words")
val tokenizedData = tokenizer.transform(sentimentData)
tokenizedData.select("text", "words").show()
 
1.8.2.	TODO C: transform words to numeric features using HashingTF
val hashingTF = new HashingTF()
  .setInputCol("words")
  .setOutputCol("features")
  .setNumFeatures(1024)
val featurizedData = hashingTF.transform(tokenizedData)
featurizedData.select("words", "features").show()
 
1.8.3.	TODO D: transform labels to numbers
val stringIndexer = new StringIndexer()
  .setInputCol("sentiment")
  .setOutputCol("label")
  .setHandleInvalid("skip")
val indexedData = stringIndexer.fit(featurizedData).transform(featurizedData)
indexedData.select("sentiment", "label").show()
 
1.8.4.	TODO E: create an object for the Linear Support Vector Machine classifier
val svc = new LinearSVC()
  .setFeaturesCol("features")
  .setLabelCol("label")
  .setMaxIter(10)
  .setRegParam(0.1)

1.8.5.	TODO F: create a pipeline that includes all the previous transformations and the model
val pipeline = new Pipeline()
  .setStages(Array(
    tokenizer,
    hashingTF,
    stringIndexer,
    svc))

1.8.6.	TODO G: create a parameter grid to cross validate the model on different hyper parameters
-	Đoạn mã trong Scala sử dụng thư viện MLlib của Apache Spark để xây dựng tập hợp các cấu hình tham số (param grid) cho quá trình tuning mô hình — cụ thể là một mô hình SVM (Support Vector Classification) dùng LinearSVC.
val paramGrid: Array[ParamMap] = new ParamGridBuilder()
  .addGrid(hashingTF.numFeatures, Array(1024, 2048))
  .addGrid(svc.fitIntercept, Array(true, false))
  .addGrid(svc.regParam, Array(0.01, 0.0001))
  .addGrid(svc.maxIter, Array(10, 15))
  .addGrid(svc.threshold, Array(0.0, 0.25))
  .addGrid(svc.tol, Array(0.0001, 0.01))
  .build()

-	.addGrid(hashingTF.numFeatures, Array(1024, 2048)): số lượng (tính năng) cụ thể được tạo bởi HashingTF. Số càng lớn → mô hình càng có nhiều thông tin để học, nhưng cũng tốn RAM và thời gian hơn
-	.addGrid(svc.fitIntercept, Array(true, false)): Cho biết có tính thêm hệ số chệch (intercept) hay không. True: có tính → mô hình linh hoạt hơn, đặc biệt nếu dữ liệu không cân bằng; False: không tính → đơn giản hơn nhưng có thể làm giảm độ chính xác nếu dữ liệu không cân bằng quanh gốc.
-	.addGrid(svc.regParam, Array(0.01, 0.0001)):Đây là hệ số điều chuẩn (regularization) — giúp mô hình không học kỹ năng vào dữ liệu huấn luyện (overfitting).0.01 ràng buộc mạnh hơn → tránh overfitting; 0.0001 ràng buộc yếu hơn → mô hình dễ học tốt hơn trên train, nhưng có nguy cơ overfit.
-	.addGrid(svc.maxIter, Array(10, 15)): Là số vòng lặp tối đa để huấn luyện mô hình. Nếu mô hình chưa “học xong” trong 10 vòng → có thể cần tăng lên.
-	.addGrid(svc.threshold, Array(0.0, 0.25)): Là ngưỡng phân loại: xác định từ bao nhiêu điểm thì mô hình gán là nhóm 1, còn lại là nhóm 0. 0.0 nhạy nhất, gần như cái gì cũng gán là nhóm 1 ; 0.25 mô hình cần chắc chắn hơn mới gán là nhóm 1.
-	.addGrid(svc.tol, Array(0.0001, 0.01)): Đây là ngưỡng dừng: nếu mô hình học hoàn thành đến mức độ chính xác này thì nó sẽ dừng lại. 0.0001 rất chặt → cần chính xác hơn mới dừng (chậm hơn); 0.01 dễ tính hơn → dừng sớm hơn, nhanh hơn

1.8.7.	TODO H: create a cross validation job that will process the pipeline using all possible combinations in the parameter grid
val cv = new TrainValidationSplit()
  .setEstimator(pipeline)
  .setEvaluator(new BinaryClassificationEvaluator())
  .setEstimatorParamMaps(paramGrid)
  .setTrainRatio(0.8)
  .setParallelism(2)
TrainValidationSplit là một kỹ thuật để tìm kiếm và chọn ra mô hình tốt nhất từ một tập hợp các siêu tham số (hyperparameters) đã cho. Thay vì sử dụng Cross Validation K-Fold phức tạp, nó chỉ chia dữ liệu thành hai phần: tập huấn luyện (training set) và tập kiểm định (validation set).
.setTrainRatio(0.8):
●	.setTrainRatio(): Phương thức này xác định tỷ lệ chia dữ liệu giữa tập huấn luyện và tập kiểm định.
●	0.8: Nghĩa là 80% dữ liệu đầu vào sẽ được sử dụng để huấn luyện mô hình, và 20% còn lại (1 - 0.8) sẽ được sử dụng làm tập kiểm định để đánh giá các mô hình được huấn luyện với các bộ siêu tham số khác nhau.
.setParallelism(2):
●	.setParallelism(): Phương thức này đặt mức độ song song khi huấn luyện các mô hình với các bộ siêu tham số khác nhau.
●	2: Nghĩa là TrainValidationSplit có thể huấn luyện và đánh giá tối đa 2 mô hình với 2 bộ siêu tham số khác nhau cùng một lúc. Điều này có thể tăng tốc quá trình tìm kiếm siêu tham số nếu có nhiều core hoặc tài nguyên sẵn có.


1.8.8.	TODO I: split the data into 80% train and 20% test
val Array(trainingData: Dataset[Row], testData: Dataset[Row]) = sentimentData.randomSplit(Array(0.8, 0.2))

1.8.9.	TODO J: Run cross-validation, and choose the best set of parameters
val model: TrainValidationSplitModel = cv.fit(trainingData)

1.8.10.	TODO K: get the parameters of the best model and print them
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

1.8.11.	TODO L: apply the model to your test set and show sample of the result
val predictions: DataFrame = model.transform(testData)
predictions.select("text", "sentiment", "label", "prediction").show()
 
1.8.12.	TODO M: evaluate the test results
val binaryClassificationEvaluator = new BinaryClassificationEvaluator()
  .setLabelCol("label")
  .setRawPredictionCol("prediction")
val accuracy: Double = binaryClassificationEvaluator.evaluate(predictions)
println(s"Accuracy of the test set is $accuracy")

🡺	Result:
 
 

🡺	(Q1) Fill the following table
The parameter values and the accuracy are based on the best model you obtained. The run time is the total run time printed by the program.
Parameter	Value
numFeatures	2048
fitIntercept	false
regParam	0.0001
maxIter	10.0000
threshold	0.2500
tol	 0.0100
Test accuracy	0.8164619490497372
Run time	123.395227 seconds

1.9.	Chế độ phân tán
1.9.1.	Cài đặt Spark
-	Truy cập vào link để tải spark https://archive.apache.org/dist/spark/spark-3.4.4/spark-3.4.4-bin-hadoop3.tgz
-	Sau khi tải thì giải nén file vào C:\spark-3.4.4
-	Thêm SPARK_HOME vào environment variables
 
-	Thêm %SPARK_HOME%\bin vào Path
 
-	Mở command line và chạy: spark-submit --version để kiểm tra cấu hình đúng
 
-	Vào thư mục conf -> copy file spark-defaults.conf.template -> dán ra và đổi tên thành spark-defaults.conf
 
-	Mở file spark-defaults.conf, edit đoạn code sau và lưu file:
-	# spark.master                     spark://class-###:7077




Tạo file jar
Vào file chọn Project Structure.
Hoặc Ctrl + Alt + Shift + S
 
Khi cửa sổ hiện lên chọn vào artifacts chọn vào dấu +
Jar -> Form modules with ….
Cửa sổ hiện lên chọn main class chọn class App
 
Nhớ chọn include in project build
 
Chọn Apply và thoát cửa sổ
 
Tiếp theo
Chọn Build -> Build artifacts ->chọn file jar cần build và chọn hành động phù hợp
File jar được tạo ra và lưu ở out/artifacts/…
Tìm file lap10.jar
out/artifacts/lap10_jar/lap10.jar
1.9.2.	Khởi chạy Spark Cluster
-	Mở 1 cửa sổ CMD/Powershell để chạy Master:
"%SPARK_HOME%\bin\spark-class.cmd" org.apache.spark.deploy.master.Master --host localhost --port 7077 --webui-port 8080
Lưu ý: Cổng 8080 sẽ tự thay đổi nếu cổng 8080 đang được sử dụng
 
-	Mở cửa sổ CMD khác để chạy Worker:
"%SPARK_HOME%\bin\spark-class.cmd" org.apache.spark.deploy.worker.Worker spark://localhost:7077
Lưu ý: Có thể chạy nhiều worker bằng cách giới hạn core của mỗi worker:
"%SPARK_HOME%\bin\spark-class.cmd"  org.apache.spark.deploy.worker.Worker --webui-port 8081 --cores 2 --memory 2G spark://localhost:7077
-	Lên trình diệt nhập URL: http://localhost:8080, Spark Master Web UI và thông tin Worker đã kết nối.
 
-	Put file sentiment.csv lên hdfs (lưu ý đường dẫn đến file sentiment.csv):
Mở cmd run as administrator 
Chạy lần lượt các lệnh
Cd C:\hadoop-3.3.0\sbin
Start-all.cmd
Cd C:\Users\ADMIN\cs167
hadoop fs -rm -r /input
hadoop fs -mkdir /input
hdfs dfs -put sentiment.csv /input
●	Lỗi khi chạy hadoop fs -rm -r /input
●	Chạy hdfs dfsadmin -safemode leave và chạy lại hadoop fs -rm -r /input
 
Check xem file đã được put chưa: http://localhost:9870/
 
-	Chạy lệnh:
C:\spark-3.4.4/bin/spark-submit.cmd --master [Spark Master] --class [Class name] --name lap10_  [url file Jar] hdfs://localhost:9000/input/sentiment.csv
[Spark Master] : spark://localhost:7077
 
[Class name] : edu.ucr.cs.cs167.nnlap10.App
 
[url file Jar] :C:\Users\ADMIN\cs167\workspace\lap10\out\artifacts\lap10_jar2\lap10.jar 
 
C:\spark-3.4.4/bin/spark-submit.cmd --master spark://localhost:7077 --class edu.ucr.cs.cs167.nnlap10.App --name lap10_ C:\Users\ADMIN\cs167\workspace\lap10\out\artifacts\lap10_jar2\lap10.jar hdfs://localhost:9000/input/sentiment.csv

 
Lưu ý: Để console chỉ hiển thị kết quả mà không cần hiển thị những log không cần thiết, hãy thực hiện các bước sau:
-	Tạo file “log4j.properties”
 
-	Thêm đoạn code sau vào file:
log4j.rootCategory=ERROR, console
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%m%n


-	Sử dụng file csv đã put lên hdfs để chạy:
C:\spark-3.4.4/bin/spark-submit.cmd --conf "spark.driver.extraJavaOptions=-Dlog4j.configuration=C:\Users\ADMIN\cs167\workspace\lap10\src\main\resources\log4j.properties"  --master spark://localhost:7077 --class edu.ucr.cs.cs167.nnlap10.App --name lap10_ C:\Users\ADMIN\cs167\workspace\lap10\out\artifacts\lap10_jar2\lap10.jar hdfs://localhost:9000/input/sentiment.csv
🡺	(Q2) Fill the following table
🡺	 
🡺	 
Parameter	Value
Number of worker nodes in your cluster	2
Total number of cores in your cluster	18
numFeatures	2048
fitIntercept	true
regParam	0.0100
maxIter	10.0000
threshold	0.2500
tol	 0.0001
Test accuracy	0.813147719924573
Run time	 174.0333499000 seconds

-	Để stop spark master và spark worker dùng lệnh: jps
 
Chúng ta sẽ thấy các PID của Master và Worker, để stop dùng lệnh taskkill sau (lưu ý kill Worker trước rồi Master sau để tránh gặp lỗi):
taskkill /PID 23316 /F
taskkill /PID 21884 /F
taskkill /PID 18680 /F
🡺	(Q3) What difference do you notice in terms of the best parameters selected, the accuracy, and the run time between running the program locally and on your Spark cluster having multiple nodes?
-	Best parameters: gần như giống nhau
-	Độ chính xác: không chênh lệch nhiều
-	Thời gian chạy: local nhanh hơn Spark cluster

