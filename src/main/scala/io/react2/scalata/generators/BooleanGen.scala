package io.react2.scalata.generators

/**
 * @author dbalduini
 */
object BooleanGen extends Generator[Boolean] {

  override def one = rand.nextBoolean()

  override def many(n: Int) = (1 to n).map(_ => rand.nextBoolean()).toList

}
