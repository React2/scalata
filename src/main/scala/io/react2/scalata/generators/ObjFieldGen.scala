package io.react2.scalata.generators

import io.react2.scalata.translation.{Root, ObjField}

/**
 * @author dbalduini
 */
class ObjFieldGen(r: Root) extends Generator[ObjField]{
  override def one: ObjField = {
    val bindings = r.fields.map(_.generate)
    ObjField(bindings.toMap)
  }
}
