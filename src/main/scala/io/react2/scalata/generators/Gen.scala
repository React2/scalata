package io.react2.scalata.generators

import io.react2.scalata.translation._

import scala.annotation.tailrec

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
  def choose(lo: Int, hi: Int): Generator[Int] = new IntGen(lo, hi)

  /**
   * Pick one value of the given elements
   * @param xs the values to pick from
   * @return the chosen value
   */
  def pick[T](xs: T*): Generator[T] = for (i <- choose(0, xs.length - 1)) yield xs(i)

  /**
   * Field generator
   * @param root The root data structure
   * @return the field generator
   */
  def fieldGen(root: DataStructure): Generator[ObjField] = {
    @tailrec
    def recursiveBuild(fields: List[FieldGen], acc: Map[String,Field]): Map[String, Field] = fields match {
      case head :: tail => recursiveBuild(tail, acc + head.generate)
      case Nil => acc
    }
    new Generator[ObjField] {
      override def one: ObjField = root match {
        case Root(name, repeat, gens) => ObjField(recursiveBuild(gens, Map()))
        case FieldGen(name, gen) => ObjField(Map(name -> gen.one))
      }
    }
  }

  def placeholder(k: String) = Gen(PlaceHolder(k))

}
