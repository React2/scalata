package io.react2.scalata.generators

import io.react2.scalata.data.Field
import org.tsers.zeison.Zeison.JValue

import scala.util.Random

/**
 * @author dbalduini
 */
trait Generator[T <: Field, +U] {
  def one: U
  def stream: Stream[U]
  def many(n: Int): List[U]
}

object Generator {

  def apply(fieldType: String, json: JValue) = {

  }

}