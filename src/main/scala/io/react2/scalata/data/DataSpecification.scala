package io.react2.scalata.data

import org.tsers.zeison.Zeison
import org.tsers.zeison.Zeison.JValue

/**
 * @author dbalduini
 */
class DataSpecification(val json: JValue) {

  def root: Root = ???

}

object DataSpecification {

  def apply(json: String) = new DataSpecification(Zeison.parse(json))

}
