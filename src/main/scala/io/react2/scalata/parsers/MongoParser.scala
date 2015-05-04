package io.react2.scalata.parsers

import java.util

import com.mongodb.BasicDBObject
import io.react2.scalata.plugins.Plugin
import io.react2.scalata.translation._
import org.bson.types.ObjectId

import scala.collection.JavaConverters._

/**
 * @author dbalduini
 */
class MongoParser(val args: Plugin.Args) extends Parser {

  override def parse(root: Field): Any = toBSON(root)

  def toBSON(f: Field): Any = f match {
    case ObjField(bindings) =>
      val doc = new BasicDBObject()
      bindings foreach {
        case (key, value) => doc.append(key, toBSON(value))
      }
      doc
    case SeqField(elems) =>
      val arr = elems map toBSON
      arr.asJava
    case StringField(v) => v
    case IntField(v) => v
    case LongField(v) => v
    case BooleanField(v) => v
    case FloatField(v) => v
    case DoubleField(v) => v
    case DateField(v) => v
    case PlaceHolder("<_id>") => ObjectId.get
    case PlaceHolder("<_empty-array>") => new util.ArrayList[Nothing]()
    case NullField => null
    case _ => throw new IllegalArgumentException
  }

}
