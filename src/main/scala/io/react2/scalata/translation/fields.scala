package io.react2.scalata.translation

/**
 * @author dbalduini
 */
sealed abstract class Field

// Containers
case class ObjField(bindings: Map[String, Field]) extends Field
case class SeqField(elems: List[ObjField]) extends Field

// Values
case class StringField(value: String) extends Field
case class DateField(value: java.util.Date) extends Field
case class BooleanField(value: Boolean) extends Field
case object NullField extends Field

// Numeric Fields
case class IntField(value: Int) extends Field
case class LongField(value: Long) extends Field
case class DoubleField(value: Double) extends Field
case class FloatField(value: Float) extends Field


object Field {
  def prettyPrint(field: Field): String = field match {
    case SeqField(elems) => "[" + (elems map prettyPrint mkString ", ") + "]"
    case ObjField(bindings) =>
      val assocs = bindings map {
        case (key, value) => "\"" + key + "\": " + prettyPrint(value)
      }
      "{" + (assocs mkString ", ") + "}"
    case StringField(v) => "\"" + v + "\""
    case LongField(v) => v.toString
    case IntField(v) => v.toString
    case DateField(v) => v.toString
    case NullField => "null"
  }
}