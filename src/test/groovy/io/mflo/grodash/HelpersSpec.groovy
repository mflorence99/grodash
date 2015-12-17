package io.mflo.grodash

import spock.lang.*

import static io.mflo.grodash.Helpers.*;

class HelpersSpec extends Specification {

  def 'makeAccessor() accesses items in a list, lodash-style'() {
    expect:
      makeAccessor(null)(42) == 42
      makeAccessor({ it * 2 })(42) == 84
      makeAccessor('x')([x: 21, y: 42]) == 21
  }

  def 'makeComparator() compares list values, lodash-style'() {
    when:
      def comparator = makeComparator('x')
    then:
      comparator([x: 21, y: 42], [x: 20]) == 1
  }

  def 'makeMatcher() matches items in a list, lodash-style'() {
    expect:
      makeMatcher([{ it == 21 }] as Object[])(21) == true
      makeMatcher([[x: 10, z: 30]] as Object[])([x: 10, y: 20, z: 30]) == true
      makeMatcher(['y', 20] as Object[])([x: 10, y: 20, z: 30]) == true
      makeMatcher(['x'] as Object[])([x: 10, y: 20, z: 30]) == true
  }

  def 'makeMatcher() is serious about what it is passed'() {
    when:
      makeMatcher([1, 2, 3] as Object[])
    then:
      thrown(IllegalArgumentException)
  }

  def 'makeZipper() zips list values, lodash-style'() {
    when:
      def tuples = []
      def zipper = makeZipper(tuples, [[1, 2, 3], [4, 5, 6], { it * 2 }])
    then:
      zipper(21) == 42
      tuples == [[1, 2, 3], [4, 5, 6]]
  }

}
