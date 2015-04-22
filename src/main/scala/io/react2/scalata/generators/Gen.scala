package io.react2.scalata.generators

import io.react2.scalata.translation.{ObjField, Root}

/**
 * @author dbalduini
 */
object Gen {

  def apply[T](x: T): Generator[T] = new Generator[T] {
    def one = x
  }

  val integers = new Generator[Int] {
    val rand = new java.util.Random
    def one = rand.nextInt()
  }

  val longs = new Generator[Long] {
    val rand = new java.util.Random
    def one = rand.nextLong
  }

  val booleans: Generator[Boolean] = for (x <- integers) yield x > 0

  /**
   * Choose one value in between the given range.
   * @param lo the min value
   * @param hi the max value
   * @return the chosen value in the range
   */
  def choose(lo: Int, hi: Int): Generator[Int] = new Generator[Int] {
    def one = Math.floor(Math.random * (hi - lo + 1) + lo).toInt
  }

  /**
   * Pick one value of the given elements
   * @param xs the values to pick from
   * @return the chosen value
   */
  def pick[T](xs: T*): Generator[T] = for (i <- choose(0, xs.length - 1)) yield xs(i)

  def root(r: Root): Generator[ObjField] = new Generator[ObjField] {
    override def one: ObjField = {
      val bindings = r.fields.map(_.generate)
      ObjField(bindings.toMap)
    }
  }

}
