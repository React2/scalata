package io.react2.scalata.exporters

import java.io.{RandomAccessFile, File}
import java.nio.ByteBuffer
import java.nio.charset.Charset
import io.react2.scalata.plugins.Plugin

/**
 * @author dbalduini
 */
class FileExporter(val args: Plugin.Args) extends Exporter {

  val fileName = args("output", "data/data.out")
  val file = new File(fileName)
  require(!file.exists, fileName + " already exists")
  require(file.createNewFile, "Impossible to create file: " + fileName)

  @transient var counter = 0
  val lineSeparator = System.getProperty("line.separator")

  val raf = new RandomAccessFile(file, "rw")
  raf.seek(raf.length)

  val channel = raf.getChannel

  override def export(a: Any): Unit = {
    val line = a.asInstanceOf[String]
    // Allocate
    val bytes = line.getBytes(Charset.forName("UTF-8"))
    val buffer = ByteBuffer.wrap(bytes)
    // Write
    buffer.put(bytes)
    buffer.rewind()
    if(channel.isOpen)
      channel.write(buffer)
    // Flush
    buffer.clear()
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

}
