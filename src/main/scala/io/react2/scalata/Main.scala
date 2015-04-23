package io.react2.scalata

import io.react2.scalata.generators.Gen
import io.react2.scalata.translation._
import scalaz.concurrent.Task
import scalaz.stream._

/**
 * @author dbalduini
 */
object Main extends App {

  import pipeline._

  def main(json: String) = {

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

//  val home = Option(System.getenv("SCALATA_HOME")) orElse Some("data/template.json")
//  require(home.isDefined)
//
//  val file = new java.io.File("")
//  require(file.exists)
//
//  val json = scala.io.Source.fromFile(file).mkString

  val json = """
      {
          "parser": "MongoParser",
          "exporter": {
              "name": "FileExporter",
              "output": "club-data.js"
          },
          "data_structure": {
              "repeat": 3001,
              "fields": [
                  {
                      "type": "{{int-64}}",
                      "name": "timestamp"
                  },
                  {
                      "type": "{{date}}",
                      "name": "birthday"
                  },
                  {
                      "type": "{{object}}",
                      "name": "club",
                      "fields": [
                          {
                              "type": "{{string}}",
                              "name": "name"
                          },
                          {
                              "type": "{{int-32}}",
                              "name": "age"
                          }
                      ]
                  }
              ]
          }
      }
      """

  // Run the program
  main(json)

}
