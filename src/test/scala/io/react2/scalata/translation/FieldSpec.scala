package io.react2.scalata.translation

import java.util.Date

import org.scalatest._

/**
 * @author dbalduini
 */
class FieldSpec extends FlatSpec with Matchers {

  val json = scala.io.Source.fromURL(getClass.getResource("/sample1.json")).mkString

  "FieldSpec" should "pretty print the fields" in {
    val myObject = ObjField(Map(
      "timestamp" -> LongField(1234),
      "birthday" -> DateField(new Date()),
      "club" -> ObjField(Map(
        "name" -> StringField("XABLAU"),
        "age" -> IntField(26))
      )))

    val expected = """{"timestamp": 1234, "birthday": Wed Apr 22 16:14:30 BRT 2015, "club": {"name": "XABLAU", "age": 26}}"""
    val result = Field.prettyPrint(myObject)
    expected === result
  }

  it should "successfully parse data structure to an ObjField" in {
    val expected = ObjField(Map(
      "timestamp" -> LongField(1234),
      "birthday" -> DateField(new Date()),
      "club" -> ObjField(Map(
        "name" -> StringField("XABLAU"),
        "age" -> IntField(26))
      )))
    val t = Translator(json)
    val root = t.buildDataStructure
    val result = Field.parse(root)
    result === expected
  }


}


