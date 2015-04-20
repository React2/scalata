package io.react2.scalata.translation

import org.tsers.zeison.Zeison
import org.tsers.zeison.Zeison.JValue

/**
 * @author dbalduini
 */
class Translator(val json: JValue) {

  def buildParser = {

  }

  def buildExporter = {

  }

  def buildDataStructure = {
    val ds = json("data_structure")
    val repeat = ds.repeat.toInt
    val fields = ds.fields.toList
    DataStructure(repeat, Field(fields))
  }

}

object Translator {

  def apply(json: String) = new Translator(Zeison.parse(json))

}
