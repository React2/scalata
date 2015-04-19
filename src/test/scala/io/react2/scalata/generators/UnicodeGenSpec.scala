package io.react2.scalata.generators

import io.react2.scalata.test.CustomMatcher
import org.scalatest._

/**
 * @author dbalduini
 */
class UnicodeGenSpec extends FlatSpec with Matchers with CustomMatcher {

  implicit val gen = new UnicodeGen(4, 15)

  "UnicodeGenSpec" should "generate 100 size data" in {
    val data = gen.many(100)
    data.size shouldBe 100
  }

  it should "random generate strings that starts with LowerCase A" in {
    expectValue[String](_.startsWith("A"), 1000)
  }

  it should "random generate strings that starts with UpperCase A" in {
    expectValue[String](_.startsWith("B"), 1000)
  }

}
