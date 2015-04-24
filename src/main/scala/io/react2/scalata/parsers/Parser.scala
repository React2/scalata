package io.react2.scalata.parsers

import io.react2.scalata.plugins.Plugin
import io.react2.scalata.translation.Field

/**
 * @author dbalduini
 */
trait Parser extends Plugin {
  def parse(f: Field): Any
}
