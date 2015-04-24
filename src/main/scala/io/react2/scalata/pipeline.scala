package io.react2.scalata

import io.react2.scalata.exporters.Exporter
import io.react2.scalata.parsers.Parser
import io.react2.scalata.translation.Field

import scalaz.concurrent.Task
import scalaz.stream.Process

/**
 * @author dbalduini
 */
object pipeline {

  implicit class PipeOp[A](a: A) {
    def |>[B](f: A => B): B = f(a)
  }

  implicit class Pipeline2[O](p: Process[Task, Field]) {
    def |>>(parser: Parser) = p.map(f => parser.parse(f))
  }

  implicit class Pipeline3[O](p: Process[Task, Any]) {
    def |>>(exporter: Exporter) = exporter.sink(p).run
  }

}
