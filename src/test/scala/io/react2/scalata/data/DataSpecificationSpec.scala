package io.react2.scalata.data

import org.scalatest._
import org.tsers.zeison.Zeison.JValue

/**
 * @author dbalduini
 */
class DataSpecificationSpec extends FlatSpec with Matchers {

  val json = scala.io.Source.fromURL(getClass.getResource("/template.json")).mkString

  "DataSpecificationSpec" should "parse the spec template file" in {
    val jValue = DataSpecification(json).json
    jValue shouldBe an[JValue]
    jValue.parser.toStr shouldBe "MongoParser"
  }

}
