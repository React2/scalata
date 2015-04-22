package io.react2.scalata.generators

/**
 * @author dbalduini
 */
class CharGen(charset: Vector[Char]) extends Generator[Char] {
  private val picker = Gen.pick(charset:_*)
  override def one: Char = picker.one
}
