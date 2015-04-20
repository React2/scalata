package io.react2.scalata.generators

import java.text.SimpleDateFormat
import java.util.{Date, TimeZone}

/**
 * @author dbalduini
 */
sealed abstract class DateGen extends Generator[Date]

class ISODateGen extends DateGen {

  private val random = new RandomDateGen

  private val sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz") {
    this.setTimeZone(TimeZone.getTimeZone("UTC"))
  }

  def toISO8601(date: Date) = sdf.parse(sdf.format(date))

  override def one = random.map(toISO8601).one

}

//TODO MELHORAR ESSE GERADOR
class RandomDateGen extends DateGen {

  private val gen = for {
    timestamp <- Gen.longs
  } yield new Date(timestamp)

  override def one = gen.one
}
