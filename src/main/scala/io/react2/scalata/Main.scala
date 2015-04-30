package io.react2.scalata

import io.react2.scalata.generators.Gen
import io.react2.scalata.translation._
import io.react2.scalata.utils.Args
import scalaz.concurrent.Task
import scalaz.stream._

/**
 * @author dbalduini
 */
object Main extends App {

  import pipeline._

  def generateData(json: String) = {

    lazy val (root, parser, exporter) = Translator(json).translate
    println(root)

    def generator(root: Root): Process[Task, Field] = {
      val gen = Gen.fieldGen(root)
      Process.range(0, root.repeat).flatMap { _ =>
        Process.emitO(gen.one)
      }.toSource.stripW
    }

    // Data Generation Pipeline
    def pipeline: Task[Unit] = root |> generator |>> parser |>> exporter

    // at the end of the universe...
    pipeline.run
  }

  val options = Args.options(this.args)

  val config = options.get('config) orElse Some("data/template.json") map(_.toString)

  val file = config.map(x => new java.io.File(x)).get

  val json = scala.io.Source.fromFile(file).mkString

  val start = System.currentTimeMillis()

  // Run the program
  generateData(json)

  val end = (System.currentTimeMillis() - start) / 1000

  println("\n=====================")
  println(s"Done in $end seconds")


}
