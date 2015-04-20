package io.react2.scalata

import io.react2.scalata.translation.Translator
import scodec.bits.ByteVector

import scalaz.stream._
import scalaz.concurrent.Task

/**
 * @author dbalduini
 */
object Main extends App {

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

  def main() = {

    val home = Option(System.getenv("SCALATA_HOME")) orElse Some("data/template.json")
    require(home.isDefined)

    val file = new java.io.File("")
    require(file.exists)

    val json = scala.io.Source.fromFile(file).mkString
    val definition = Translator(json)


    // at the end of the universe...
    val u: Unit = generator.run
  }



}
