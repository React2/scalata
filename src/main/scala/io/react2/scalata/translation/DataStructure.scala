package io.react2.scalata.translation

import io.react2.scalata.generators.Generator

/**
 * @author dbalduini
 */
sealed abstract class DataStructure {
  def name: String
}

case class Root(name: String, repeat: Int, fields: List[FieldGen]) extends DataStructure

case class FieldGen(name: String, gen: Generator[Field]) extends DataStructure {
  def generate: (String, Field) = name -> gen.one
}


