package io.react2.scalata.generators

import io.react2.scalata.translation.StringField
import io.react2.scalata.utils.Charset._

/**
 * @author dbalduini
 */
sealed abstract class StringGen(min: Int, max: Int) extends Generator[StringField] {
  private [this] val lengthGenerator = Gen.choose(min, max)
  def nextLen = lengthGenerator.one
}

class UnicodeGen(min: Int, max: Int, charset: Vector[Char], escape: Set[Char]) extends StringGen(min, max) {
  private val escaped = charset.filter(_ != '"')
  def this(min: Int, max: Int, escape: Set[Char] = Set.empty) = this(min, max, BASIC_LATIN, escape)
  val charGenerator = new CharGen(escaped.filterNot(c => escape.contains(c)))
  override def one: StringField = new String(charGenerator.many(nextLen))
}

class AlphabeticGen(min: Int, max: Int, alphabet: Seq[String]) extends StringGen(min, max) {
  def this(min: Int, max: Int) = this(min, max, LATIN_ALPHABET)
  private val picker = Gen.pick(alphabet:_*)
  override def one: StringField = (min to nextLen).foldLeft("")((t, a) => t + picker.one)
}

object StringGen {
  def default = new AlphabeticGen(1, 10)
}
