package io.react2.scalata

import io.react2.scalata.exceptions.MissingPropertyException
import io.react2.scalata.translation.DescriptionFormat.Reader
import org.tsers.zeison.Zeison.JValue

/**
 * @author dbalduini
 */
package object translation {

  implicit def enrichJValue(j: JValue) = new RichJValue(j)


  class RichJValue(j: JValue) {
    def get[T](key: String)(implicit reader: Reader[T]): T = {
      j.apply(key).toOption match {
        case Some(_) => reader.read(key)(j)
        case None => throw MissingPropertyException(key)
      }
    }

    def getOrElse[T](key: String, that: T)(implicit reader: Reader[T]): T = {
      j.apply(key).toOption match {
        case Some(_) => reader.read(key)(j)
        case None => that
      }
    }
  }


}
