package io.react2.scalata.generators

import scala.util.Random

/**
 * @author dbalduini
 */
trait Generator[+T] {

  protected[generators] val rand = Random

  def one: T

  def many(n: Int): List[T]
}
