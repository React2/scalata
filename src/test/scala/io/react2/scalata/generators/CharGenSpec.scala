package io.react2.scalata.generators

import io.react2.scalata.utils.Charset
import org.scalatest._

/**
 * @author dbalduini
 */
class CharGenSpec extends FlatSpec with Matchers {

  val charset = Charset.LATIN_ALPHABET.map(_.charAt(0))

  implicit val gen = new CharGen(charset)

  "CharGenSpec" should "pick only available chars" in {
    val expected = charset.toSet
    val result = gen.many(100).toSet
    result foreach print
    println()
    result.size === 100
    (expected diff result) === Set()
  }

}
