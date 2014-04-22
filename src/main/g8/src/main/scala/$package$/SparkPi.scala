package $package$

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object SparkPi {

  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      System.err.println("Usage: SparkPi <master> [<slices>]")
      System.exit(1)
    }

    val sc = new SparkContext(args(0), "SparkPi",
      System.getenv("SPARK_HOME"), SparkContext.jarOfClass(this.getClass))

    val slices = if (args.length > 1) args(1).toInt else 2

    val sp = new SparkPi(sc, slices)
    val pi = sp.exec()
    println("pi: " + pi)

    sc.stop()
  }
}

class SparkPi(sc: SparkContext, slices: Int) {

  val NUM_SAMPLES = 10000
  val n = NUM_SAMPLES / slices

  def exec(): Double = {
    val count = sc.parallelize(1 to n, slices).map{i =>
      val x = Math.random()
      val y = Math.random()
      if (x*x + y*y < 1) 1 else 0
    }.reduce(_ + _)
    val pi = 4.0 * count / n
    pi
  }
}

// vim: ft=scala tw=0 sw=2 ts=2 et
