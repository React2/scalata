package io.react2.scalata

import io.react2.scalata.translation.Translator

/**
 * @author dbalduini
 */
object Main extends App {

  def main() = {

    val home = Option(System.getenv("SCALATA_HOME")) orElse Some("data/template.json")
    require(home.isDefined)

    val file = new java.io.File("")
    require(file.exists)

    val json = scala.io.Source.fromFile(file).mkString
    val trans = Translator(json)

    // at the end of the universe...
    trans.converter.run
  }

  // Run the program
  main()

}
