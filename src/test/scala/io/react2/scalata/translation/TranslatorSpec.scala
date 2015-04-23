package io.react2.scalata.translation

import org.scalatest._
import org.tsers.zeison.Zeison.JValue

/**
 * @author dbalduini
 */
class TranslatorSpec extends FlatSpec with Matchers {
  val definition = scala.io.Source.fromURL(getClass.getResource("/sample1.json")).mkString

  "TranslatorSpec" should "parse the spec template file" in {
    val jValue = Translator(definition).json
    jValue shouldBe an[JValue]
    jValue.parser.toStr shouldBe "MongoParser"
  }

  it should "build the data structure" in {
    val t = Translator(definition)
    val ds = t.buildRoot
    println(ds)
    ds.name === "root"
  }

}
