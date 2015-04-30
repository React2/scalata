package io.react2.scalata.generators

import io.react2.scalata.translation.DateField
import io.react2.scalata.utils.Format

/**
 * @author dbalduini
 */
sealed abstract class DateGen extends Generator[DateField]

class ISODateGen(lo: Int, hi: Int) extends DateGen {

  private val random = new RandomDateGen(lo, hi)
  private val sdf = Format.ISO_DATE_FORMAT

  private def toISO8601(date: DateField) = sdf.parse(sdf.format(date.value))

  override def one = random.map(toISO8601).one

}

class RandomDateGen(lo: Int, hi: Int) extends DateGen {

  import java.util.{Calendar, GregorianCalendar}

  private val gc = new GregorianCalendar()
  private val yearGen = Gen.choose(lo, hi)
  private val dayGen = Gen.choose(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR))

  private lazy val dateGen: Generator[DateField] = for {
    year <- yearGen
    day <- dayGen
  } yield {
    gc.set(Calendar.YEAR, year)
    gc.set(Calendar.DAY_OF_YEAR, day)
    gc.getTime
  }

  override def one = dateGen.one

}

object DateGen {
  def default = new RandomDateGen(2000, 2015)
}

