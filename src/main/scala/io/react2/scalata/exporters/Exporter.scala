package io.react2.scalata.exporters

/**
 * @author dbalduini
 */
trait Exporter {
  def export(f: String): Unit
  def close(): Unit = {}
}

object Exporter {

  import scalaz.concurrent.Task
  import scalaz.stream._

  def exporterSink[T](out: Exporter)(fun: (Exporter, T) => Unit): Sink[Task, T] = {
    val task: T => Task[Unit] = f => Task.delay(fun(out, f))
    channel.lift(task)
  }

  def export(out: Exporter) = exporterSink[String](out)(_ export _)

}