package io.react2.scalata.generators

/**
 * @author dbalduini
 */

class IncrementalGen(start: Int, stop: Int) extends Generator[Int] {
  var step = start
  override def one: Int = {
    val x = step
    if(x == stop)
      step = start
    else
      step = step + 1
    x
  }
}
