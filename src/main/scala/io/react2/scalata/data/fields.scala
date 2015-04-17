package io.react2.scalata.data

import io.react2.scalata.generators.Generator

/**
 * @author dbalduini
 */

sealed abstract class Field {
  def `type`: String
  def fields: List[Field]
  def generator: Generator[Any] = ???
}

case class Root(repeat: Int, fields: List[Field])

case class ObjectField(fields: List[Field]) extends Field {
  override def `type` = "{{object}}"
}

case class StringField(fields: List[Field]) extends Field {
  override def `type` = "{{string}}"
}

case class DateField(fields: List[Field]) extends Field {
  override def `type` = "{{date}}"
}

case class NumberField(fields: List[Field]) extends Field {
  override def `type` = "{{number}}"
}

case class BooleanField(fields: List[Field]) extends Field {
  override def `type` = "{{boolean}}"
}

case class IdField(fields: List[Field]) extends Field {
  override def `type` = "{{id}}"
}
