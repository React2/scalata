package io.react2.scalata.generators

/**
 * @author dbalduini
 */
trait Generator[+U] {
  self =>

  def one: U

  def map[S](f: U => S): Generator[S] = new Generator[S] {
    def one = f(self.one)
  }

  def flatMap[S](f: U => Generator[S]): Generator[S] = new Generator[S] {
    def one = f(self.one).one
  }

  def stream: Stream[U] = one #:: stream

  def many(n: Int): List[U] = stream.take(n).toList

  /**
   * Transform this generator to a generator of List[U].
   * @return the List[U] generator
   */
  def lists: Generator[List[U]] = {
    def nonEmptyLists = for {
      head <- self
      tail <- lists
    } yield head :: tail

    for {
      isEmpty <- Generator.booleans
      list <- if (isEmpty) Generator(Nil) else nonEmptyLists
    } yield list
  }

}

object Generator {

  def apply[T](x: T): Generator[T] = new Generator[T] {
    def one = x
  }

  /**
   * An integer generator
   */
  val integers = new Generator[Int] {
    val rand = new java.util.Random
    def one = rand.nextInt()
  }

  /**
   * A boolean generator
   */
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

}