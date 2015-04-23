package io.react2.scalata.translation

import io.react2.scalata.exceptions.InvalidGenType
import io.react2.scalata.exporters.FileExporter
import io.react2.scalata.generators._
import io.react2.scalata.parsers.MongoParser
import org.tsers.zeison.Zeison
import org.tsers.zeison.Zeison.JValue

/**
 * @author dbalduini
 */
class Translator(val json: JValue) {

  def buildRoot = {
    def getRoot(j: JValue): Root = {
      val repeat = j.repeat.toInt
      val fields = j.fields.toList
      def parseFields(fields: List[JValue]): List[FieldGen] = {
        def parse(field: JValue): FieldGen = {
          val name = field.get[String]("name")
          val `type` = field.get[String]("type")
          // Get the generator
          val generator: Generator[Field] = `type` match {
            case "{{object}}" =>
              val repeat = field.getOrElse[Int]("repeat", 1)
              val fields = field.fields.toOption.map(_.toList).getOrElse(Nil)
              val root = Root(name, repeat, parseFields(fields))
              new ObjFieldGen(root)
            case "{{string}}" => new UnicodeGen(4, 15)
            case "{{date}}" => new RandomDateGen(1900, 2015)
            case "{{int-32}}" => Gen.integers
            case "{{int-64}}" => Gen.longs
            case "{{float-32}}" => ???
            case "{{float-64}}" => ???
            case "{{boolean}}" => Gen.booleans
            case "{{null}}" => Gen(null)
            case other => throw new InvalidGenType(other)
          }
          FieldGen(name, generator)
        }
        fields map parse
      }
      Root("root", repeat, parseFields(fields))
    }
    getRoot(json("data_structure"))
  }


  def buildParser = new MongoParser

  def buildExporter = new FileExporter("output.txt")

  def translate = (buildRoot, buildParser, buildExporter)


}


object Translator {
  def apply(json: String) = new Translator(Zeison.parse(json))
}
