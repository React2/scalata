package io.react2.scalata.exporters

import java.io.{RandomAccessFile, File}
import java.nio.ByteBuffer
import java.nio.charset.Charset
import io.react2.scalata.plugins.Plugin

import scalaz.concurrent.Task
import scalaz.stream.Process

/**
 * @author dbalduini
 */
class FileExporter(val args: Plugin.Args) extends Exporter {

  val fileName = args("output", "data/data.out")
  val buffSize = args("buffSize", 2048)

  val file = new File(fileName)
  require(!file.exists, fileName + " already exists")
  require(file.createNewFile, "Impossible to create file: " + fileName)

  var counter = 0
  val lineSeparator = System.getProperty("line.separator")

  val raf = new RandomAccessFile(file, "rw")
  raf.seek(raf.length())

  val channel = raf.getChannel
  val buffer = ByteBuffer.allocate(buffSize)

  override def export(a: Any): Unit = {
    val line = a.asInstanceOf[String]
    buffer.clear()
    val bytes = line.getBytes(Charset.forName("UTF-8"))
    buffer.put(bytes)
    buffer.flip()

    // Write
    while(buffer.hasRemaining)
      channel.write(buffer)

    counter += 1
    if(counter % 1000 == 0) {
      println("Flushing...")
      channel.force(true)
    }
  }

  override def close(): Unit = {
    println("Closing File Channel...")
    channel.close()
  }


  override protected def intersperse(p: => Process[Task, Any]) = p.intersperse(",\n")

}
