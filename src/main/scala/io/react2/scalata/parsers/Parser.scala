package io.react2.scalata
package parsers

import io.react2.scalata.translation.Field

/**
 * @author dbalduini
 */
trait Parser[-A, +B] {
  def parse(a: A): B
}

trait FieldParser[+U] extends Parser[Field, U]
