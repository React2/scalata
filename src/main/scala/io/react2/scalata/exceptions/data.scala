package io.react2.scalata.exceptions

/**
 * @author dbalduini
 */
case class MissingPropertyException(name: String) extends Exception(s"The required property $name is missing")

case class InvalidFieldType(name: String) extends Exception(s"Invalid field type $name")

