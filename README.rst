This is a `Spark <https://spark.incubator.apache.org/>`_ template for generating a new Spark application project.
It comes bundled with:

* *main* and *test* source directories
* `ScalaTest <http://www.scalatest.org/>`_
* Scalacheck
* SBT configuration for *0.13.0* , *Scala 2.10.3*, and *ScalaTest 2.0* dependencies
* project *name* , *package* and *version* customizable as variables

How to use
==========
First, you need to install `conscript <https://github.com/n8han/conscript>`_ .::

 $ curl https://raw.github.com/n8han/conscript/master/setup.sh | sh

Conscript command is installed into ~/bin/cs.
If ~/bin is included in PATH, you can install `giter8 <https://github.com/n8han/giter8>`_ ::

 $ cs n8han/giter8

Next, the following command generates the first set of Spark application.::

 $ g8 nttdata-oss/basic-spark-project

After the short question, you have the project directory (default: basic-spark)
In the project directory, README.rst is found, which tells us how to execute sample application.

You can read README.rst of the sample project from `this link <https://github.com/nttdata-oss/basic-spark-project.g8/blob/master/src/main/g8/README.rst>`_


CHANGELOG
=========

0.1.1 (Change CDH5b2 to CDH5 GA.)
------------------------
* Scalatest 2.0 for testing
* Sbt 0.12.4
* Scala 2.10.3
* Spark 0.9.0
* Hadoop 2.3 (CDH5)
* SparkPi

0.1.0 (Initial release!)
------------------------
* Scalatest 2.0 for testing
* Sbt 0.12.4
* Scala 2.10.3
* Spark 0.9.0
* Hadoop 2.2 (CDH5b2)
* SparkPi

.. vim: ft=rst tw=0 ts=2 sw=2 et
