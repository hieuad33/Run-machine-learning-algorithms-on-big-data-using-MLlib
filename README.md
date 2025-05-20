Lab 10: Design and Implementation of Machine Learning Pipelines on Big Data with Spark Mllib
1.	Cài đặt phần mềm cần thiết:
   JDK 1.8, Hadoop 3.3.0, spark 4.4.4 ,intellij
thiết lập cấu hình Hadoop và sparrk hướng dẫn trên internet
tham khảo: Hadoop https://duythanhcse.wordpress.com/2021/01/01/cai-dat-hadoop-tren-windows/
spark https://phoenixnap.com/kb/install-spark-on-windows-10

clone dự án về máy 
chạy dự án trên intellij và tạo file jar
khởi động Hadoop
Mở CMD truy cập đến thư mục dự án chạy các lện sau để đưa file lên hadoop
hadoop fs -rm -r /input
hadoop fs -mkdir /input
hdfs dfs -put sentiment.csv /input

Khởi chạy Spark Cluster
-	Mở 1 cửa sổ CMD/Powershell để chạy Master:
"%SPARK_HOME%\bin\spark-class.cmd" org.apache.spark.deploy.master.Master --host localhost --port 7077 --webui-port 8080
-	Mở cửa sổ CMD khác để chạy Worker:
"%SPARK_HOME%\bin\spark-class.cmd" org.apache.spark.deploy.worker.Worker spark://localhost:7077
Lưu ý: Có thể chạy nhiều worker bằng cách giới hạn core của mỗi worker:
"%SPARK_HOME%\bin\spark-class.cmd"  org.apache.spark.deploy.worker.Worker --webui-port 8081 --cores 2 --memory 2G spark://localhost:7077


chạy lệnh để thực hiện chạy chương trình trên môi trường song song
spark-submit.cmd --master [Spark Master] --class [Class name] --name lap10_  [url file Jar] hdfs://localhost:9000/input/sentiment.csv
trong đó
 [Spark Master] : spark://localhost:7077
 
[Class name] : edu.ucr.cs.cs167.nnlap10.App
 
[url file Jar] :C:\Users\ADMIN\cs167\workspace\lap10\out\artifacts\lap10_jar2\lap10.jar 






