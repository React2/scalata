package io.react2.scalata.data

import org.tsers.zeison.Zeison
import org.tsers.zeison.Zeison.JValue

/**
 * @author dbalduini
 */
class DataSpecification(val json: JValue) {

  lazy val root = {
    val struct = json("data_structure")
    val repeat = struct.repeat.toInt
    val fields = struct.fields.toList
    Root(repeat, Field(fields))
  }



}

object DataSpecification {

  def apply(json: String) = new DataSpecification(Zeison.parse(json))

}
