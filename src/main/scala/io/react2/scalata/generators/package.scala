package io.react2.scalata

import java.util.Date

import io.react2.scalata.translation._

/**
 * @author dbalduini
 */
package object generators {

  // Implicit field converters
  implicit def stringField(s: String): StringField = StringField(s)
  implicit def longField(d: Long): LongField = LongField(d)
  implicit def intField(d: Int): IntField = IntField(d)
  implicit def toDateField(d: Date): DateField = DateField(d)
  implicit def fromDateField(d: DateField): Date = d.value

  // Implicit generators converters
  implicit def intFieldGen(gen: Generator[Int]) = for(i <- gen) yield IntField(i)
  implicit def longFieldGen(gen: Generator[Long]) = for(i <- gen) yield LongField(i)
  implicit def boolFieldGen(gen: Generator[Boolean]) = for(i <- gen) yield BooleanField(i)


}
