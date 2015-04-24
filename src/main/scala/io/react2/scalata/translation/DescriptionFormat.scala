package io.react2.scalata.translation

import org.tsers.zeison.Zeison.JValue

/**
 * @author dbalduini
 */
object DescriptionFormat {

  import scala.annotation.implicitNotFound

  @implicitNotFound("No Reader in scope for ${T}")
  trait Reader[T] {
    def read(key: String): JValue => T = json => parse(json.apply(key))
    def parse(j: JValue): T
  }

  object Reader {
    implicit object StringReader extends Reader[String] {
      override def parse(j: JValue): String = j.toStr
    }
    implicit object IntReader extends Reader[Int] {
      override def parse(j: JValue): Int = j.toInt
    }
  }

}
