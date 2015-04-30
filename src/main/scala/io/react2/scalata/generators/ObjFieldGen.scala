package io.react2.scalata.generators

import io.react2.scalata.translation.{SeqField, Field, Root, ObjField}

/**
 * @author dbalduini
 */
class ObjFieldGen(r: Root) extends Generator[Field] {

  private val picker = new Generator[ObjField] {
    override def one: ObjField = {
      val bindings = r.fields.map(_.generate)
      ObjField(bindings.toMap)
    }
  }

  override def one: Field = {
    if(r.repeat > 1) {
      val xs = picker.many(r.repeat)
      SeqField(xs)
    } else
      picker.one
  }

}
