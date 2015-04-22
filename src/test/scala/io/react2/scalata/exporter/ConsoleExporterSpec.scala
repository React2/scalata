package io.react2.scalata.exporter

import java.util.Date

import io.react2.scalata.exporters.ConsoleExporter
import io.react2.scalata.translation._
import org.scalatest._

/**
 * @author dbalduini
 */
class ConsoleExporterSpec extends FlatSpec {

  "ConsoleExporter" should "print to console" in {
    val myObject = ObjField(Map(
      "timestamp" -> LongField(1234),
      "birthday" -> DateField(new Date()),
      "club" -> ObjField(Map(
        "name" -> StringField("XABLAU"),
        "age" -> IntField(26))
      )))

    val e = new ConsoleExporter()
    e.export(myObject)
  }
  
}
