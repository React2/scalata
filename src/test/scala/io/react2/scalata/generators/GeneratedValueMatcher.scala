package io.react2.scalata.generators

import org.scalatest.matchers.{MatchResult, Matcher}

/**
 * @author dbalduini
 */
trait GeneratedValueMatcher {

  class ExpectValue[T](expected: T, until: Int)(implicit val gen: Generator[T]) extends Matcher[T] {

    def apply(left: T) = {

      def _loop[T](expect: T, until: Int = 100): Boolean = {
        if(until <= 0) false
        else if(gen.one == expect) true
        else _loop(expect, until - 1)
      }

      MatchResult(
        _loop(expected, until),
        s"""Value $expected was not generated"""",
        s"""Value generated as expected""""
      )
    }
  }

  def expectValue[T](expected: T, until: Int = 100)(implicit gen: Generator[T]) = new ExpectValue(expected, until)

}
