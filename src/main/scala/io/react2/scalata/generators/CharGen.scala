package io.react2.scalata.generators

/**
 * @author dbalduini
 */
class CharGen(charset: Vector[Char]) extends Generator[Char] {
  override def one: Char = Gen.pick(charset:_*).one
}
