package io.react2.scalata.exporters

/**
 * @author dbalduini
 */
class ConsoleExporter extends Exporter {
  override def export(u: String): Unit = println(u)
}

