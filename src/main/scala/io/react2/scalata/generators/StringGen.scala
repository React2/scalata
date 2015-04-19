package io.react2.scalata.generators

import io.react2.scalata.utils.Charset._

/**
 * @author dbalduini
 */
sealed abstract class StringGen(min: Int, max: Int) extends Generator[String] {
  val lengthGenerator = Generator.choose(min, max)

  def nextLen = {
    val a = lengthGenerator.one
   println(a)
    a
  }
}

class UnicodeGen(min: Int, max: Int, charset: Vector[Char]) extends StringGen(min, max) {
  def this(min: Int, max: Int) = this(min, max, BASIC_LATIN)
  val charGenerator = new CharGen(charset)
  override def one: String = new String(charGenerator.many(nextLen))
}

class AlphabeticGen(min: Int, max: Int, alphabet: Seq[String]) extends StringGen(min, max) {
  alphabet foreach println
  def this(min: Int, max: Int) = this(min, max, LATIN_ALPHABET)
  val picker = Generator.pick(alphabet:_*)
  override def one: String = (min to nextLen).foldLeft("")((t, a) => t + picker.one)
}