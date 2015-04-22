package io.react2.scalata.generators

import io.react2.scalata.test.CustomMatcher
import org.scalatest._

/**
 * @author dbalduini
 */
class DateGenSpec extends FlatSpec with Matchers with CustomMatcher {

  val randGen = new RandomDateGen(1900, 2015)
  val isoGen = new ISODateGen(1900, 2015)

  "DateGenSpec" should "gen a random date" in {
    val xs = randGen.many(100)
    val ys = xs.map(_.getYear + 1900).filterNot(x => x >= 1900 && x <= 2015)
    ys shouldBe empty
  }

}
