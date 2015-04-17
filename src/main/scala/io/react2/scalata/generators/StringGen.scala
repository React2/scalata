package io.react2.scalata.generators

import io.react2.scalata.data.StringField
import io.react2.scalata.utils.Random

/**
 * @author dbalduini
 */
class StringGen(name: String, minLen: Int, maxLen: Int)
  extends Generator[StringField, String] {

  private lazy val strategy = name match {
    case "{{random}}" => Random.randString(minLen, maxLen)
  }

  override def one: String = Random.randString(minLen, maxLen)

  override def many(n: Int): List[String] = stream.take(n).toList

  override def stream: Stream[String] = one #:: stream

}
