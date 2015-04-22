package io.react2.scalata.parsers

import io.react2.scalata.translation._

/**
 * @author dbalduini
 */
class MongoParser extends FieldParser[String] {

  override def parse(a: Field): String = a match {
    case SeqField(elems) => "[" + (elems map parse mkString ", ") + "]"
    case ObjField(bindings) =>
      val assocs = bindings map {
        case (key, value) => "\"" + key + "\": " + parse(value)
      }
      "{" + (assocs mkString ", ") + "}"
    case StringField(v) => "\"" + v + "\""
    case LongField(v) => s"NumberLong($v)"
    case IntField(v) => s"NumberInt($v)"
    case FloatField(v) => v.toString
    case DoubleField(v) => v.toString
    case DateField(v) => v.toString
    case NullField => "null"
  }

}
