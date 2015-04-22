package io.react2.scalata.parsers

import java.util.Date

import io.react2.scalata.translation._
import org.scalatest._

/**
 * @author dbalduini
 */
class ParserSpec extends FlatSpec with Matchers {

  val definition = scala.io.Source.fromURL(getClass.getResource("/sample1.json")).mkString

  "ParserSpec" should "parse a DataStructure to a map of fields" in {

    val exptected = ObjField(Map(
      "timestamp" -> LongField(1234),
      "birthday" -> DateField(new Date()),
      "club" -> ObjField(Map(
        "name" -> StringField("XABLAU"),
        "age" -> IntField(26))
      )))


    val t = Translator(definition)
    val ds = t.buildDataStructure
    val obj = Field.parse(ds)
    Field.prettyPrint(obj)
    obj shouldBe an [ObjField]
  }

}
