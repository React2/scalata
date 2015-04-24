package io.react2.scalata.exporters

import io.react2.scalata.plugins.Plugin
import scalaz.concurrent.Task
import scalaz.stream._

/**
 * @author dbalduini
 */
trait Exporter extends Plugin {

  def export(f: Any): Unit
  def close(): Unit = {}
//  def before(): Unit = {}
//  def after(): Unit = {}

  protected def intersperse(p: => Process[Task, Any]): Process[Task, Any] = p

  // Export stream
  private def closeResources = Process.eval_(Task.delay(this.close()))
  private def output = Exporter.exporterSink[Any](this)(_ export _).onComplete(closeResources)
  def sink(p: Process[Task, Any]): Process[Task, Unit] = intersperse(p).to(output)

}

object Exporter {

  import scalaz.concurrent.Task
  import scalaz.stream._

  def exporterSink[T](out: Exporter)(fun: (Exporter, T) => Unit): Sink[Task, T] = {
    val task: T => Task[Unit] = f => Task.delay(fun(out, f))
    channel.lift(task)
  }

}