package io.react2.scalata.data

import io.react2.scalata.translation.Translator
import org.scalatest._
import org.tsers.zeison.Zeison.JValue

/**
 * @author dbalduini
 */
class TranslatorSpec extends FlatSpec with Matchers {

  val json = scala.io.Source.fromURL(getClass.getResource("/template.json")).mkString

  "TranslatorSpec" should "parse the spec template file" in {
    val jValue = Translator(json).json
    jValue shouldBe an[JValue]
    jValue.parser.toStr shouldBe "MongoParser"
  }

}
