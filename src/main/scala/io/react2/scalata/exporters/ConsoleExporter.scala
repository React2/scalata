package io.react2.scalata.exporters

import io.react2.scalata.translation.Field

/**
 * @author dbalduini
 */
class ConsoleExporter extends Exporter[Field] {
  override def export(u: Field): Unit = println(Field.prettyPrint(u))
}

