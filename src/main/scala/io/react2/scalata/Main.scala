package io.react2.scalata

import scodec.bits.ByteVector

import scalaz.stream._
import scalaz.concurrent.Task

/**
 * @author dbalduini
 */
object Main extends App {

  val definition = "data/template.json"

  type MEUTIPO = String

  def generate: Process[Task, MEUTIPO] = ???

  def parse(line: MEUTIPO): Process1[MEUTIPO, ByteVector] = ???

  def toByteVector: Process1[MEUTIPO, ByteVector] = ???

  def export: Sink[Task, ByteVector] = io.fileChunkW("data/output.txt")

  val generator: Task[Unit] =
    generate
      .pipe(toByteVector)
      .to(export)
      .run

  // at the end of the universe...
  val u: Unit = generator.run

}
