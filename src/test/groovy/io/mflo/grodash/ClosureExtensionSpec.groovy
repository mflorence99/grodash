package io.mflo.grodash

import spock.lang.*

import static io.mflo.grodash.Closures.*

class ClosureExtensionSpec extends Specification {

  def setupSpec() {
    ({ println it }).defer('Goodbye, cruel world!')
    ({ println it }).delay(1, 'Hello, world!')
  }

  def 'after() calls the supplied closure after it has been invoked N times (README test)'() {
    expect:
      (0..9).inject('', { p, q -> p += q }.after(6)) == '56789'
  }

  def 'after() calls the supplied closure after it has been invoked N times'() {
    given:
      def times = 0
      def saves = ['profile', 'settings']
      def saver = { save, index -> times++ }.after(saves.size())
    when:
      saves.eachWithIndex { save, index -> saver(save, index) }
    then:
      times == 1
  }

  def 'ary() calls the supplied closure with a maximum of N arguments'() {
    expect:
      ['6', '8', '10'].map(Integer.&parseInt.ary(1)) == [6, 8, 10]
  }

  def 'before() calls the supplied closure until it has been invoked N times (README test)'() {
    expect:
      (0..9).inject('', { p, q -> p += q }.before(6)) == '01234'
  }

  def 'before() calls the supplied closure until it has been invoked N times'() {
    given:
      def flintstones = [
        ['name': 'barney'],
        ['name': 'fred'],
        ['name': 'pebbles'],
        ['name': 'bambam']
      ]
      def indexer = { item, index -> item.index = index }.before(4)
    when:
      flintstones.eachWithIndex { item, index -> indexer(item, index) }
    then:
      flintstones[2].index == 2
      flintstones[3].index == null
  }

  def 'memoize() caches the result of a closure'() {
    when:
      def fn = identity.memoize(constant('a'))
    then:
      fn(42) == fn(21)
  }

  def 'negate() negates the return of the supplied closure'() {
    expect:
      [1, 2, 3].map({ n -> n % 2 == 0 }.negate()) == [true, false, true]
  }

  def 'once() calls the supplied closure only once'() {
    expect:
      (0..9).inject('', { p, q -> p += q }.once()) == '0'
  }

  def 'tap() is a K-combinator'() {
    expect:
      [1, 2, 3].map({ n -> n % 2 == 0 }.tap(identity)) == [false, true, false]
  }

  def 'thru() is like tap, except it returns the return of the interceptor'() {
    expect:
      [1, 2, 3].map({ n -> n % 2 == 0 }.thru(constant(42))) == [42, 42, 42]    
  }

  def 'times() calls the supplied closure N times'() {
    expect:
      constant(42).times(5) == [42, 42, 42, 42, 42]
      identity.times(5, 42) == [42, 42, 42, 42, 42]
  }

}
