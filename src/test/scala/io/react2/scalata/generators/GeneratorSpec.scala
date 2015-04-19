package io.react2.scalata.generators

import io.react2.scalata.test.CustomMatcher
import org.scalatest._

/**
 * @author dbalduini
 */
class GeneratorSpec extends FlatSpec with Matchers with CustomMatcher {

  "GeneratorSpec" should "choose only numbers in the given range" in {
    val expected = Set(5,6,7,8,9,10)
    implicit val gen = Generator.choose(5, 10)
    val result = gen.many(100).toSet
    (result diff expected) === Set()
  }

  it should "pick only expected values" in {
    val expected = List(1, 4, 6, 8)
    implicit val gen = Generator.pick(expected: _*)
    expectValue[Int](x => expected.contains(x), 100)
  }

}
