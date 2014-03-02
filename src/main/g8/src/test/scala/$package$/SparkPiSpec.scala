package $package$

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

class SparkPiSpec extends FunSpec with BeforeAndAfter{

  val sc = new SparkContext("local", "SparkPi",
             System.getenv("SPARK_HOME"), SparkContext.jarOfClass(this.getClass))

  describe("Pi") {
    it("should be less than 4 and more than 3"){
      val sp = new SparkPi(sc, 1)
      val result = sp.exec()
      assert(result > 3 && result < 4)
    }
  }
}

// vim: ft=scala et ts=2 sw=2 tw=0
