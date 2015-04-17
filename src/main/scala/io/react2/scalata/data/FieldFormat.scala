package io.react2.scalata.data

import org.tsers.zeison.Zeison.JValue

/**
 * @author dbalduini
 */
object FieldFormat {

  import annotation.implicitNotFound
  @implicitNotFound("No FieldReader in scope for ${T}")
  trait FieldReader[T] {
    def read(key: String): JValue => T
  }

  object FieldReader {
    implicit object StringFieldReader extends FieldReader[String] {
      override def read(key: String) = _.apply(key).toStr
    }
  }

}
