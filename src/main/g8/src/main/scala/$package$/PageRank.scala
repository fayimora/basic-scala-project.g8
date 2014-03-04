package $package$

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object PageRank {

  def main(args: Array[String]): Unit = {
    if (args.length < 4) {
      System.err.println("Usage: PageRank <master> <input file> <output dir> <num of iteration>")
      System.exit(1)
    }

    val sc = new SparkContext(args(0), "PageRank",
      System.getenv("SPARK_HOME"), SparkContext.jarOfClass(this.getClass))

    val myPageRank = new PageRank(sc, args(1), args(2), args(3).toInt)
    myPageRank.writeOutput()

    sc.stop()
  }
}

class PageRank(sc: SparkContext, iFile: String, oDir: String,  iters: Int) {
  val lines = sc.textFile(iFile, 1)

  val links = lines.map{ s =>
    val parts = s.split("\\s+")
    (parts(0), parts(1))
  }.distinct().groupByKey().cache()
  var ranks = links.mapValues(v => 1.0)

  for (i <- 1 to iters) {
    val contribs = links.join(ranks).values.flatMap{ case (urls, rank) =>
      val size = urls.size
      urls.map(url => (url, rank / size))
    }
    ranks = contribs.reduceByKey(_ + _).mapValues(0.15 + 0.85 * _)
  }

  val formedRank = ranks.map(_.swap).sortByKey()
  
  def writeOutput() = {
    formedRank.saveAsTextFile(oDir)
  }
}

// vim: ft=scala tw=0 sw=2 ts=2 et
