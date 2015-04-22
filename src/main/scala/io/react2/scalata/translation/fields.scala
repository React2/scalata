package io.react2.scalata.translation

import scala.annotation.tailrec

/**
 * @author dbalduini
 */
sealed abstract class Field

// Containers
case class ObjField(bindings: Map[String, Field]) extends Field {
  def +(b: (String, Field)) = ObjField(bindings + b)

  def -(k: String) = ObjField(bindings - k)
}

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

  def parse(root: DataStructure): ObjField = {
    @tailrec
    def recursiveBuild(fields: List[FieldGen], acc: Map[String,Field]): Map[String, Field] = fields match {
      case head :: tail => recursiveBuild(tail, acc + head.generate)
      case Nil => acc
    }
    root match {
      case Root(name, repeat, gens) => ObjField(recursiveBuild(gens, Map()))
      case FieldGen(name, gen) => ObjField(Map(name -> gen.one))
    }
  }

}