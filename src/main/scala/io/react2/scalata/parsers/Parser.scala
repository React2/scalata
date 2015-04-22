package io.react2.scalata
package parsers

import io.react2.scalata.translation._

/**
 * @author dbalduini
 */
trait Parser {

  def parse(data: Map[Key, Field]): Unit

}

object Parser {

}
