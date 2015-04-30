package io.react2.scalata.exporters

import com.mongodb.{BasicDBObject, MongoClient}
import io.react2.scalata.plugins.Plugin

import scala.util.control.NonFatal

/**
 * @author dbalduini
 */
class MongoExporter(val args: Plugin.Args) extends Exporter {

  val verbose = args("verbose", false)
  val database = args("database", "test")
  val collection = args("collection", "scalata")
  val host = args("host", "localhost")
  val port = args("port", 27017)

  val mongo = new MongoClient(host, port)
  val db = mongo.getDB(database)
  val coll = db.getCollection(collection)
  val bulk = coll.initializeUnorderedBulkOperation()

  override def export(f: Any): Unit = {
    val doc = f.asInstanceOf[BasicDBObject]
    try {
      if(verbose)
        println(doc.toString)
      bulk.insert(doc)
    } catch {
      case NonFatal(t) =>
        t.printStackTrace()
    }
  }

  override def close(): Unit = {
    println("Executing - start")
    bulk.execute()
    println("Executing - done")
  }
}
