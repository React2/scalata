package io.react2.scalata

import io.react2.scalata.translation.{DataStructure, Translator}
import scodec.bits.ByteVector

import scalaz.stream._
import scalaz.concurrent.Task

/**
 * @author dbalduini
 */
object Main extends App {



//  def toByteVector: ProcessAnyPO, ByteVector] = ???

//  def export: Sink[Task, ByteVector] = io.fileChunkW("data/output.txt"un

  def main() = {

    val home = Option(System.getenv("SCALATA_HOME")) orElse Some("data/template.json")
    require(home.isDefined)

    val file = new java.io.File("")
    require(file.exists)

    val json = scala.io.Source.fromFile(file).mkString
    val trans = Translator(json)

    // Generate Pipelinere
    val generate = trans.buildGenerator
    // Parse Pipeline
    val parse = trans.buildParser
    // Export Pipeline
    val export = trans.buildExporter

    //TODO ASSIM FICA MELHOR EU ACHO
//    trans.generator.run


//    val generator: Task[Unit] =
//      generate
//        .pipe(parse)
//        .to(export)
//        .runs)

    // at the end of the universe...
//    val u: Unit = generator.run
  }


  // Run the program
  main()

}
