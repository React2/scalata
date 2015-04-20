package io.react2.scalata.translation

import io.react2.scalata.exceptions._
import io.react2.scalata.generators._
import org.tsers.zeison.Zeison.JValue

import scalaz.Alpha

/**
 * @author dbalduini
 */
object Field {
  def apply(fields: List[JValue]) = parseFields(fields)

  def parseFields(fields: List[JValue]): List[Field] = {
    def parse(j: JValue): Field = {
      val name = j.get[String]("name")
      val `type` = j.get[String]("type")
      val fields = j.fields.toOption.map(_.toList).getOrElse(Nil)
      `type` match {
        case "{{object}}" => ObjField(name, parseFields(fields))
        case "{{string}}" =>
          val gen = j.getOrElse[StringGen]("generator", StringGen.alphabetic)
          StringField(name, gen)
        case "{{date}}" => DateField(name)
        case "{{number}}" => NumberField(name)
        case "{{boolean}}" => BooleanField(name)
        case "{{id}}" => IdField(name)
        case "{{null}}" => NullField
        case other => throw new InvalidFieldType(other)
      }
    }
    fields map parse
  }

}

sealed abstract class Field {
  def name: String
}

trait HasGen[+T] {
  def gen: Generator[T]
}

case class ObjField(name: String, elems: List[Field]) extends Field
case class StringField(name: String, gen: Generator[String]) extends Field with HasGen[String]
case class DateField(name: String) extends Field
case class NumberField(name: String) extends Field
case class BooleanField(name: String) extends Field
case class IdField[T](name: String) extends Field

case object NullField extends Field {
  override val name: String = throw new NoSuchElementException("name of null field")
}

