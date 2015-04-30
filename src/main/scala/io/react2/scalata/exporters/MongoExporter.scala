package io.react2.scalata.exporters

import com.mongodb.{WriteConcern, BulkWriteOperation, BasicDBObject, MongoClient}
import io.react2.scalata.Context
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
  var maybeBulk: Option[BulkWriteOperation] = None

  @transient var counter = 0
  val total = Context.NUMBER_OF_LINES.get

  override def export(f: Any): Unit = {
    val doc = f.asInstanceOf[BasicDBObject]
    try {
      if(verbose)
        println(doc.toString)

      val bulk = maybeBulk match {
        case None =>
          maybeBulk = Some(coll.initializeUnorderedBulkOperation)
          maybeBulk.get
        case Some(b) => b
      }

      bulk.insert(doc)
      counter = counter + 1
      flush()

    } catch {
      case NonFatal(t) =>
        t.printStackTrace()
    }
  }

  private def flush(): Unit = {
    val percentage = (counter.toFloat * 100) / total
    if (percentage != 0 && percentage % 10 == 0) {
      println(s"Current percentage processed: $counter / $total / $percentage%")
      maybeBulk match {
        case Some(bulk) =>
          val wc = new WriteConcern(0)
          bulk.execute(wc)
          maybeBulk = None
        case None =>
      }
    }
  }

}
