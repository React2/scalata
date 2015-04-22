package io.react2.scalata.translation

import io.react2.scalata.exceptions.InvalidGenType
import io.react2.scalata.generators._
import org.tsers.zeison.Zeison
import org.tsers.zeison.Zeison.JValue

import scalaz.stream._
import scalaz.concurrent.Task

/**
 * @author dbalduini
 */
class Translator(val json: JValue) {

  type Data = Map[String, Any]


  def buildDataStructure = {

    def dataStructure(j: JValue): DataStructure = {

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
            case "{{string}}" => new UnicodeGen(4,15)
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

    val root = json("data_structure")
    dataStructure(root)
  }


  def buildParser = {

  }

  def buildExporter = {

  }

  def buildGenerator: Process[Task, Data] = {
    ???
  }

}

object Translator {

  def apply(json: String) = new Translator(Zeison.parse(json))

}
