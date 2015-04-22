package io.react2.scalata.exporters

/**
 * @author dbalduini
 */
trait Exporter[-U] {
  def export(u: U): Unit
}
