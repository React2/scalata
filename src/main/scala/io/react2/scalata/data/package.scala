package io.react2.scalata

import io.react2.scalata.data.FieldFormat.FieldReader
import io.react2.scalata.exceptions.MissingPropertyException
import org.tsers.zeison.Zeison.JValue

/**
 * @author dbalduini
 */
package object data {


  implicit def toRichJValue(j: JValue) = new RichJValue(j)

  class RichJValue(j: JValue) {

    def get[T](key: String)(implicit reader: FieldReader[T]): T = {
      j.apply(key).toOption match {
        case Some(_) => reader.read(key)(j)
        case None => throw MissingPropertyException(key)
      }
    }



  }

}
