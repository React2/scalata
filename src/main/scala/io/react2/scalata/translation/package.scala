package io.react2.scalata

import io.react2.scalata.exceptions.MissingPropertyException
import io.react2.scalata.translation.DescriptionFormat.Reader
import org.tsers.zeison.Zeison.JValue

/**
 * @author dbalduini
 */
package object translation {


  implicit class RichJValue(j: JValue) {

    def get[T](key: String)(implicit reader: Reader[T]): T =
      maybe(key).getOrElse(throw MissingPropertyException(key))

    def getOrElse[T](key: String, that: T)(implicit reader: Reader[T]): T =
      maybe(key) getOrElse that

    def maybe[T](key: String)(implicit reader: Reader[T]): Option[T] =
      j.apply(key).toOption.map(_ => reader.read(key)(j))

    def as[T](implicit reader: Reader[T]): T = reader.parse(j)

  }


}
