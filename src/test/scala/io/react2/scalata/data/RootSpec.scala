package io.react2.scalata.data

import org.scalatest._

/**
 * @author dbalduini
 */
class RootSpec extends FlatSpec with Matchers {

  val json = scala.io.Source.fromURL(getClass.getResource("/template.json")).mkString

  var root: Root = null

  "RootSpec" should "not be null" in {
    val spec = DataSpecification(json)
    root = spec.root
    root should not be null
  }

  it should "have 4 fields" in {
    root.fields.size shouldBe 4
    root.repeat shouldBe 100
  }

}


