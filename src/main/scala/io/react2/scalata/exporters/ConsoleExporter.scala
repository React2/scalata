package io.react2.scalata.exporters

import io.react2.scalata.plugins.Plugin

/**
 * @author dbalduini
 */
class ConsoleExporter(val args: Plugin.Args) extends Exporter {
  override def export(u: Any): Unit = println(u)
}

