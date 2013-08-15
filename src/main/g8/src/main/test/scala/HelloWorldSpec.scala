package $organization$.$name;format="lower,word"$

import org.scalatest.FunSpec

class HelloWorldSpec extends FunSpec {
  describe("1 + 1") {
    it("should equals 2") {
      val res = 1+1
      assert(res == 2)
    }
  }
}

