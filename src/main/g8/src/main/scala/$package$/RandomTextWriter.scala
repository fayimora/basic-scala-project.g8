package $package$


import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import scopt.OptionParser
import scala.math.random
import scala.collection.mutable._

case class RandomTextWriterConfig(master: String = "",
                                  output: String = "",
                                  minKey: Int = 5, maxKey: Int = 10,
                                  minValue: Int = 10, maxValue: Int = 100,
                                  mapNum: Int = 1,
                                  userName: String = "spark",
                                  megaBytesPerMap: Int = 1)

object RandomTextWriter {

  def main(args: Array[String]): Unit = {

    val parser = new OptionParser[RandomTextWriterConfig]("RandomTextWriter") {
      opt[Int]('k', "minKey") valueName("minKey") action {
        (x, c) => c.copy(minKey = x)
      }

      opt[Int]('K', "maxKey") valueName("maxKey") action {
        (x, c) => c.copy(maxKey = x)
      }
      
      opt[Int]('v', "minValue") valueName("minValue") action {
        (x, c) => c.copy(minValue = x)
      }

      opt[Int]('V', "maxValue") valueName("maxValue") action {
        (x, c) => c.copy(maxValue = x)
      }

      opt[Int]('b', "megaBytesPerMap [MB]") valueName("megaBytesPerMap") action {
        (x, c) => c.copy(megaBytesPerMap = x)
      }

      opt[Int]('n', "mapNum") valueName("mapNum") action {
        (x, c) => c.copy(mapNum = x)
      }

      opt[String]('u', "userName") valueName("userName") action {
        (x, c) => c.copy(userName = x)
      }

      arg[String]("master") valueName("master") action {
        (x, c) => c.copy(master = x)
      }

      arg[String]("output") valueName("output") action {
        (x, c) => c.copy(output = x)
      }
    }

    parser.parse(args, RandomTextWriterConfig()) map { config =>
      val wordsInKeyRange = config.maxKey - config.minKey
      val wordsInValueRange = config.maxValue - config.minValue

      val sparkConf = new SparkConf()
                      .setMaster(config.master)
                      .setAppName("RandomTextWriter")
                      .setJars(SparkContext.jarOfClass(this.getClass))
                      .setSparkHome(System.getenv("SPARK_HOME"))
      //                .set("user.name", config.userName)

      System.setProperty("user.name", config.userName)

      //val sc = new SparkContext(config.master, "RandomTextWriter",
      //  System.getenv("SPARK_HOME"), SparkContext.jarOfClass(this.getClass))
      val sc = new SparkContext(sparkConf)

      val randomTextWriter2 = new RandomTextWriter(sc, config.minKey, wordsInKeyRange,
        config.minValue, wordsInValueRange, config.mapNum, config.megaBytesPerMap, config.output)

      println("Total size: " + randomTextWriter2.totalSize.value + " [byte]")

      sc.stop()

    } getOrElse {
      System.exit(1)
    }
  
  }
}

@SerialVersionUID(1L)
class RandomTextWriter(sc: SparkContext, minKey: Int, wordsInKeyRange: Int,
          minValue: Int, wordsInValueRange: Int, mapNum: Int, megaBytesPerMap: Int, output: String) 
          extends Serializable {

  val totalSize = sc.accumulator(0: Long)

  sc.parallelize(1 to mapNum, mapNum).flatMap { id =>
    val keyValues = ArrayBuffer.empty[(String, String)]
    var bytes: Long = megaBytesPerMap * 1024 * 1024

    while (bytes > 0) {
      val noWordsKey = if (wordsInKeyRange != 0) (minKey + random * wordsInKeyRange).toInt else 0
      val noWordsValue = if (wordsInValueRange != 0) (minValue + random * wordsInValueRange).toInt else 0

      val keyWords = generateSentence(noWordsKey)
      val valueWords = generateSentence(noWordsValue)

      keyValues += ((keyWords, valueWords))

      val size = keyWords.getLength + valueWords.getLength
      bytes -= size
      totalSize += size
    }
    keyValues

  }.map { t =>
    t._1 + "\t" + t._2
  }.saveAsTextFile(output)

  def generateSentence(noWords: Int) = {
    val sentence = new StringBuilder
    val space = " "
  
    for (i <- 0 to noWords -1) {
      sentence ++= Words.words((random * Words.words.length).toInt)
      sentence ++= space
    }
    sentence.toString
  }


}

// vim: ft=scala tw=0 sw=2 ts=2 et
