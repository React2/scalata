package io.react2.scalata.generators

/**
 * @author dbalduini
 */
sealed abstract class NumberGen[+T] extends Generator[T]

class IntGen(lo: Int, hi: Int) extends NumberGen[Int] {
  private val picker = new LongGen(lo, hi).map(_.toInt)
  override def one: Int = picker.one
}

class LongGen(lo: Long, hi: Long) extends NumberGen[Long] {
  override def one: Long = Math.floor(Math.random * (hi - lo + 1) + lo).toLong
}
