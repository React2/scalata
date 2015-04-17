package io.react2.scalata.generators

import io.react2.scalata.data.DataSpecification
import org.scalatest._

/**
 * @author dbalduini
 */
class BooleanGenSpec extends FlatSpec with Matchers with GeneratedValueMatcher {

  val json = scala.io.Source.fromURL(getClass.getResource("/template.json")).mkString

  implicit val gen = BooleanGen

  "BooleanGenSpec" should "be loaded from spec" in {
    val spec = DataSpecification(json)
    val field = spec.root.fields(1)
//    field.generator shouldBe an [BooleanGen.type]
  }

  it should "random generate true values" in {
    expectValue(true, 10)
  }

  it should "random generate false values" in {
    expectValue(false, 10)
  }

  it should "generate 100 size data" in {
    val data = gen.many(100)
    data.size shouldBe 100
  }

}
