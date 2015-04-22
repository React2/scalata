package io.react2.scalata.translation

import io.react2.scalata.generators._
import org.tsers.zeison.Zeison.JValue

import scalaz._
import std.option._

/**
 * @author dbalduini
 */
object DescriptionFormat {

  import scala.annotation.implicitNotFound

  @implicitNotFound("No Reader in scope for ${T}")
  trait Reader[T] {
    def read(key: String): JValue => T
  }

  object Reader {

//    def opt[T](j: JValue)(implicit r: Reader[T]) = j.toOption.map(x => r.read(x))

    implicit object StringReader extends Reader[String] {
      override def read(key: String) = _.apply(key).toStr
    }

    implicit object IntReader extends Reader[Int] {
      override def read(key: String) = _.apply(key).toInt
    }
  }

}
