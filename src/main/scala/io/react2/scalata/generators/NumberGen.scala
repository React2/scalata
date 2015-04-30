package io.react2.scalata.generators

/**
 * @author dbalduini
 */
sealed abstract class NumberGen[+T] extends Generator[T]

class IntGen(lo: Int, hi: Int) extends NumberGen[Int] {
  private val picker = new DoubleGen(lo, hi).map(_.toInt)
  override def one: Int = picker.one
}

class LongGen(lo: Long, hi: Long) extends NumberGen[Long] {
  private val picker = new DoubleGen(lo, hi).map(_.toLong)
  override def one: Long = picker.one
}

class FloatGen(lo: Long, hi: Long) extends NumberGen[Float] {
  private val picker = new DoubleGen(lo, hi).map(_.toFloat)
  override def one: Float = picker.one
}

class DoubleGen(lo: Long, hi: Long) extends NumberGen[Double] {
  override def one: Double = Math.floor(Math.random * (hi - lo + 1) + lo)
}
