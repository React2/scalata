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

//  def filter(f: U => Boolean): Generator[U] = new Generator[U] {
//    def one = {
//      val x = self.one
//      if(f(x)) x else this.one
//    }
//  }

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
      isEmpty <- Gen.booleans
      list <- if (isEmpty) Gen(Nil) else nonEmptyLists
    } yield list
  }

}
