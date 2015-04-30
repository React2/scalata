package io.react2.scalata.utils

import java.text.SimpleDateFormat
import java.util.TimeZone

/**
 * @author dbalduini
 */
object Format {

  final val ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ") {
    this.setTimeZone(TimeZone.getTimeZone("UTC"))
  }

}
