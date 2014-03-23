**********************
Spark sample project
**********************
This is a sample project of Spark.
You can run the most simple example (SparkPi) without Spark cluster,
and also can run it on Spark standalone cluster.

If you have YARN cluster, the test-data generator (RandomTextWriter) and
the wordcount application (WordCount) is available.
These appliation use HDFS for input and output.

.. contents::
.. sectnum::

Feature
========
* Use ScalaTest
* Include sbteclipse-plugin config in plugins.sbt
* Use Hadoop 2.2 (CDH5b2)
* Sample souce code

  + SparkPi
  + WordCount
 
How to run SparkPi on local mode with sbt command
=================================================

Requirement
-----------
* sbt is installed

Procedure
---------
You can run SparkPi on local mode::

 $ sbt "run local 1"
 ...
 ..
 .
 14/03/02 11:34:49 INFO SparkContext: Job finished: reduce at SparkPi.scala:37, took 0.610860249 s
 pi: 3.11
 14/03/02 11:34:50 INFO MapOutputTrackerMasterActor: MapOutputTrackerActor stopped!
 14/03/02 11:34:50 INFO ConnectionManager: Selector thread was interrupted!
 14/03/02 11:34:50 INFO ConnectionManager: ConnectionManager stopped
 14/03/02 11:34:50 INFO MemoryStore: MemoryStore cleared
 14/03/02 11:34:50 INFO BlockManager: BlockManager stopped
 14/03/02 11:34:50 INFO BlockManagerMasterActor: Stopping BlockManagerMaster
 14/03/02 11:34:50 INFO BlockManagerMaster: BlockManagerMaster stopped
 14/03/02 11:34:50 INFO SparkContext: Successfully stopped SparkContext
 14/03/02 11:34:50 INFO RemoteActorRefProvider$RemotingTerminator: Shutting down remote daemon.
 [success] Total time: 7 s, completed 2014/03/02 11:34:50
 14/03/02 11:34:50 INFO RemoteActorRefProvider$RemotingTerminator: Remote daemon shut down; proceeding with flushing remote transports.

How to run SparkPi on spark shell
=================================

Requirement
-----------
* sbt is installed
* Spark is installed on your computer.
  If you use CDH5, you have spark-shell command
  in your PATH.

Procedure
---------
First, you need compile source codes
and make JAR.::

 $ sbt compile
 $ sbt package

Then, you get JAR as target/scala-2.10/basic-spark.jar.

Second, you can run SparkPi with spark-shell.::

 $ MASTER=local ADD_JARS=target/scala-2.10/basic-spark.jar SPARK_CLASSPATH=$SPARK_CLASSPATH:target/scala-2.10/basic-spark.jar spark-shell

Now, you see spark's console::

 scala>

You need to import the library and run SparkPi.::

 scala> import com.example.SparkPi._
 scala> val sp = new SparkPi(sc, 2)
 scala> sp.exec()
 ...
 ..
 .
 res0: Double = 3.1376

How to run SparkPi on local mode
================================
You can run SparkPi with spark-class command.

Requirement
-----------
* sbt is installed
* Spark is installed on your computer.
  If you use CDH5, you have spark-class command
  in /usr/lib/spark/bin/spark-class.

Procedure
---------
First, you need compile source codes
and make JAR in the same way of running with spark-shell.
Then, we suppose that you have JAR as <your source root directory>/target/scala-2.10/basic-spark.jar.

Next, you can run SparkPi with spark-class command.::

 $ SPARK_CLASSPATH=$SPARK_CLASSPATH:target/scala-2.10/basic-spark.jar /usr/lib/spark/bin/spark-class com.example.SparkPi local
 ...
 ..
 .
 14/03/02 11:51:01 INFO SparkContext: Job finished: reduce at SparkPi.scala:37, took 0.703761825 s
 pi: 3.1192
 14/03/02 11:51:02 INFO MapOutputTrackerMasterActor: MapOutputTrackerActor stopped!
 14/03/02 11:51:02 INFO ConnectionManager: Selector thread was interrupted!
 14/03/02 11:51:02 INFO ConnectionManager: ConnectionManager stopped
 14/03/02 11:51:02 INFO MemoryStore: MemoryStore cleared
 14/03/02 11:51:02 INFO BlockManager: BlockManager stopped
 14/03/02 11:51:02 INFO BlockManagerMasterActor: Stopping BlockManagerMaster
 14/03/02 11:51:02 INFO BlockManagerMaster: BlockManagerMaster stopped
 14/03/02 11:51:02 INFO RemoteActorRefProvider$RemotingTerminator: Shutting down remote daemon.
 14/03/02 11:51:02 INFO SparkContext: Successfully stopped SparkContext
 14/03/02 11:51:02 INFO RemoteActorRefProvider$RemotingTerminator: Remote daemon shut down; proceeding with flushing remote transports.

How to run SparkPi on the Spark standalone cluster
==================================================
You can run SparkPi on the Spark standalone cluster with spark-class command.

Requirement
-----------
* sbt is installed
* Spark is installed on your computer.
  If you use CDH5, you have spark-class command
  in /usr/lib/spark/bin/spark-class.
* The standalone cluster of Spark is available from your computer.
  We assume that the url for the master is "spark://spark-01:7077".

Procedure
---------
First, you need to copy JAR to every server in the cluster.
In this tutorial, we assume that basic-spark.jar is located on /tmp/basic-spark.jar in every server,
and is readable for spark user.

Next, you can run SparkPi with spark-class command.::

 $ /usr/lib/spark/bin/spark-class org.apache.spark.deploy.Client launch spark://spark-01:7077 file:///tmp/basic-spark.jar com.example.SparkPi spark://spark-01:7077 10
 Sending launch command to spark://spark-01:7077
 Driver successfully submitted as driver-20140302163431-0000
 ... waiting before polling master for driver state
 ... polling master for driver state
 State of driver-20140302163431-0000 is RUNNING
 Driver running on spark-04:7078 (worker-20140228225630-spark-04-7078)

The launched driver program and application is found on Spark master's web frontend.
(ex. http://spark-01:8080)
The detail information for driver program is obtained from "Completed Drivers".
In the woker's frontend, you get the stdout and stderr of the driver program.

How to run RandomTextWriter on the YARN cluster with yarn-client mode
=====================================================================
You can run RandomTextWriter, which is used to generate test data, on **YARN cluster** .

Requirement
-----------
* sbt is installed
* Spark-0.9.0-incubating with compiled against CDH5b2.
  Here, we assume that you have cloned the Spark repository in ~/Sources/spark-0.9.0-incubating
  and the compiled JAR path is ~/Sources/spark-0.9.0-incubating/assembly/target/scala-2.10/spark-assembly-0.9.0-incubating-hadoop2.2.0-cdh5.0.0-beta-2.jar.
  The detail of compilling sources of Spark is available on `Spark public website <http://spark.apache.org/docs/latest/running-on-yarn.html>`_ .
* The CDH5b2 YARN cluster is available from your client computer.
* The CDH5b2 HDFS cluster is available from your client computer.
  We assume that the url of HDFS is hdfs://hdfs-namenode:8020/
* Hadoop configuration file is located on /etc/hadoop/conf.
* You have the spark-env.sh in ~/Sources/spark-0.9.0-incubating/conf/spark-env.sh.
  The following is the content.::

   export SPARK_USER=${USER}
   export HADOOP_CONF_DIR=/etc/hadoop/conf
   export SPARK_JAR=./assembly/target/scala-2.10/spark-assembly-0.9.0-incubating-hadoop2.2.0-cdh5.0.0-beta-2.jar

* The application JAR compiled by "sbt assembly" is located on target/scala-2.10/basic-spark.jar
  
Procedure
---------
RandomTextWriter generates test-data, which is consists of key-value recode delited by *tab* .
The key and value is the sequence of some words which is randomly selected from the list of 1000 words.

Example::

 scapuloradial circumzenithal corbel eer hemimelus divinator <<tab>> nativeness reconciliable pneumonalgia Joachimite Dadaism

You can run RandomTextWriter by the following command::

 $ SPARK_CLASSPATH=$CLASSPATH:../basic-spark/target/scala-2.10/basic-spark.jar SPARK_YARN_APP_JAR=../basic-spark/target/scala-2.10/basic-spark.jar ./bin/spark-class com.example.RandomTextWriter yarn-client hdfs://cdh5-pseudo:8020/user/vagrant/sampledata-hoge -b 10 -n 2



.. vim: ft=rst tw=0
