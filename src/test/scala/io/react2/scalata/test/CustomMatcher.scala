package io.react2.scalata.test

import io.react2.scalata.generators.Generator
import org.scalatest.matchers.{MatchResult, Matcher}

/**
 * @author dbalduini
 */
trait CustomMatcher {

  type Test[T] = T => Boolean

  class ExpectValue[T](test: Test[T], until: Int)(implicit val gen: Generator[T]) extends Matcher[T] {

    def apply(left: T) = {

      def _loop(until: Int = 100): Boolean = {
        if(until <= 0) false
        else if(test(gen.one)) true
        else _loop(until - 1)
      }

      MatchResult(
        _loop(until),
        s"""Value was not generated"""",
        s"""Value generated as expected""""
      )
    }
  }

  def expectValue[T](test: Test[T], until: Int = 100)(implicit gen: Generator[T]) = new ExpectValue(test, until)

}
