package io.react2.scalata.data

import io.react2.scalata.translation._
import org.scalatest._
import org.tsers.zeison.Zeison

/**
 * @author dbalduini
 */
class FieldSpec extends FlatSpec with Matchers{

  val json = scala.io.Source.fromURL(getClass.getResource("/root.json")).mkString

  var fields: List[Field] = Nil

  "FieldSpec" should "parse a list of json values" in {
    val jv = Zeison.parse(json)
    fields = Field(jv.fields.toList)
    fields.isEmpty shouldBe false
  }

  it should "have 3 fields" in {
    fields.size shouldBe 3
  }

  it should "return a list of fields in the correct order" in {
    val expected = List(
      StringField("name"),
      NumberField("age"),
      ObjField("address",
        List(StringField("street"), NumberField("number")))
    )
    fields === expected
  }

}


