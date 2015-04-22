package io.react2.scalata.parsers

import io.react2.scalata.translation._
import org.scalatest._

/**
 * @author dbalduini
 */
class MongoParserSpec extends FlatSpec with Matchers {

  val parser = new MongoParser()

  "MongoParserSpec" should "successfully parse the object" in {
    val root = ObjField(Map(
      "timestamp" -> LongField(1234),
      "club" -> ObjField(Map(
        "name" -> StringField("XABLAU"),
        "age" -> IntField(26))
      )))
    val expected = """{"timestamp": NumberLong(1234), "club": {"name": "XABLAU", "age": NumberInt(26)}}"""
    val line = parser.parse(root)
    line shouldBe expected
  }

}
