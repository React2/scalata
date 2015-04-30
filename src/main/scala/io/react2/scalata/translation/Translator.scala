package io.react2.scalata.translation

import io.react2.scalata.Context
import io.react2.scalata.exporters._
import io.react2.scalata.generators._
import io.react2.scalata.parsers._
import io.react2.scalata.plugins.Plugin
import org.tsers.zeison.Zeison
import org.tsers.zeison.Zeison.JValue

/**
 * @author dbalduini
 */
class Translator(val json: JValue) {

  def translate = {
    val root = buildRoot

    // Set context variables
    Context.NUMBER_OF_LINES = Some(root.repeat)

    // Translate pipeline members
    val parser = buildParser
    val exporter = buildExporter
    (root, parser, exporter)
  }

  lazy val buildRoot = {
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
            case "{{unicode}}" =>
              val escape: Set[Char] = field("escape").toSet[JValue].map(_.toStr.head)
              new UnicodeGen(4, 15, escape)
            case "{{alphabetic}}" =>
              val min = field.getOrElse[Int]("min", 0)
              val max = field.getOrElse[Int]("max", 10)
              new AlphabeticGen(min, max)
            case "{{int-32}}" =>
              val min = field.getOrElse[Int]("min", Int.MinValue)
              val max = field.getOrElse[Int]("max", Int.MaxValue)
              field.maybe[Int]("value") match {
                case Some(value) => Gen(IntField(value))
                case None => new IntGen(min, max)
              }
            case "{{int-64}}" =>
              val min = field.getOrElse[Long]("min", Long.MinValue)
              val max = field.getOrElse[Long]("max", Long.MaxValue)
              field.maybe[Long]("value") match {
                case Some(value) =>
                  println("Aqui " + value)
                  Gen(LongField(value))
                case None => new LongGen(min, max)
              }
            case "{{float-32}}" =>
              val min = field.getOrElse[Long]("min", Long.MinValue)
              val max = field.getOrElse[Long]("max", Long.MaxValue)
              new FloatGen(min, max)
            case "{{float-64}}" =>
              val min = field.getOrElse[Long]("min", Long.MinValue)
              val max = field.getOrElse[Long]("max", Long.MaxValue)
              field.maybe[Double]("value") match {
                case Some(value) => Gen(DoubleField(value))
                case None => new DoubleGen(min, max)
              }
            case "{{boolean}}" =>
              Gen.booleans
            case "{{date}}" =>
              new RandomDateGen(2000, 2015)
            case "{{date-iso}}" =>
              new ISODateGen(2000, 2015)
            case "{{inc}}" =>
              new IncrementalGen(1, 30)
            case "{{null}}" =>
              Gen(null)
            case key@placeholder() =>
              Gen.placeholder(key)
            case other =>
              Gen(other)
          }
          FieldGen(name, generator)
        }
        fields map parse
      }
      Root("root", repeat, parseFields(fields))
    }
    getRoot(json("data_structure"))
  }


  lazy val buildParser: Parser = {
    val parser = json.parser
    val args = parser.toMap
    val name = parser.get[String]("name")
    Plugin.of[Parser](name, args)
  }

  lazy val buildExporter: Exporter = {
    val exporter = json.exporter
    val args = exporter.toMap
    val name = args("name").as[String]
    Plugin.of[Exporter](name, args)
  }

  private object placeholder {
    val pattern = """^<(.*)>$""".r

    def unapply(s: String): Boolean = s match {
      case pattern(key) => true
      case _ => false
    }
  }

}


object Translator {
  def apply(json: String) = new Translator(Zeison.parse(json))
}
