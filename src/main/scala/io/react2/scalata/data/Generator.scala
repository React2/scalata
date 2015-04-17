package io.react2.scalata.data

/**
 * @author dbalduini
 */
sealed abstract class StringGenerator

case object Default extends StringGenerator
case object Alphanumeric extends StringGenerator
case object Random extends StringGenerator

