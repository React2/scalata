package io.react2.scalata.parsers

import io.react2.scalata.translation._
import io.react2.scalata.plugins.Plugin
import io.react2.scalata.utils.Format

/**
 * @author dbalduini
 */
class MongoParser(val args: Plugin.Args) extends Parser {

  private val sdf = Format.ISO_DATE_FORMAT



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
    case BooleanField(v) => v.toString
    case FloatField(v) => v.toString
    case DoubleField(v) => v.toString
    case DateField(v) =>
      val date = "\"" + sdf.format(v) + "\""
      s"ISODate($date)"
    case PlaceHolder("<_id>") => "ObjectId()"
    case NullField => "null"
    case _ => "<ERROR>"
  }

}
