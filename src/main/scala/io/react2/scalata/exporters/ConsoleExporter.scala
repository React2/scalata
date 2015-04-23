package io.react2.scalata.exporters

/**
 * @author dbalduini
 */
class ConsoleExporter extends Exporter {
  override def export(u: Any): Unit = println(u)
}

