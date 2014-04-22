package $package$

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import scopt.OptionParser
import scala.math.random
import scala.collection.mutable._

case class WordCountConfig(master: String = "",
                           input: String = "",
                           output: String = "",
                           userName: String = "spark",
                           minSplits: Int = 1)

object WordCount {

  def main(args: Array[String]): Unit = {

    val parser = new OptionParser[WordCountConfig]("WordCount") {
      opt[String]('u', "userName") valueName("userName") action {
        (x, c) => c.copy(userName = x)
      }

      arg[String]("master") valueName("master") action {
        (x, c) => c.copy(master = x)
      }

      arg[String]("input") valueName("input") action {
        (x, c) => c.copy(input = x)
      }

      arg[String]("output") valueName("output") action {
        (x, c) => c.copy(output = x)
      }

      opt[Int]('s', "minSplits") valueName("minSplists") action {
        (x, c) => c.copy(minSplits = x)
      }

    }

    parser.parse(args, WordCountConfig()) map { config =>
      val sparkConf = new SparkConf()
                            .setMaster(config.master)
                            .setAppName("WordCount")
                            .setJars(SparkContext.jarOfClass(this.getClass))
                            .setSparkHome(System.getenv("SPARK_HOME"))

      System.setProperty("user.name", config.userName)

      val sc = new SparkContext(sparkConf)

      val wordCount = new WordCount(sc, config.input, config.output, config.minSplits)
      val numWords = wordCount.getNumWords()

      println("The number of kinds of words: " + numWords)

      sc.stop()
      System.exit(0)
    } getOrElse {
      System.exit(1)
    }

  } 
}

@SerialVersionUID(1L)
class WordCount(sc: SparkContext, input: String,  output: String, minSplits: Int)
                                                 extends Serializable {

  val file = sc.textFile(input)

  // We first pick up value of key-value, and split sentences by space.
  // Next, words are counted up.
  val counts = file.map(line => line.split("\t")(1))
                    .flatMap(line => line.split(" "))
                    .map(word => (word, 1))
                    .reduceByKey(_ + _)
                    .cache()
  counts.saveAsTextFile(output)

  def getNumWords() = {
    counts.count()
  }
}

// vim: ft=scala tw=0 sw=2 ts=2 et
