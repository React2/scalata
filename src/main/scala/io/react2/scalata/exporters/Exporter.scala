package io.react2.scalata.exporters

import scalaz.concurrent.Task
import scalaz.stream._

/**
 * @author dbalduini
 */
trait Exporter {

  def export(f: Any): Unit
  def close(): Unit = {}
  def before(): Unit = {}
  def after(): Unit = {}

  // Export stream
  private def closeResources = Process.eval_(Task.delay(this.close()))
  private def output = Exporter.exporterSink[Any](this)(_ export _).onComplete(closeResources)
  def sink(p: Process[Task, Any]): Process[Task, Unit] = p.intersperse(",\n").to(output)

}

object Exporter {

  import scalaz.concurrent.Task
  import scalaz.stream._

  def exporterSink[T](out: Exporter)(fun: (Exporter, T) => Unit): Sink[Task, T] = {
    val task: T => Task[Unit] = f => Task.delay(fun(out, f))
    channel.lift(task)
  }

}