package io.mflo.grodash

import spock.lang.*

class ClosureExtensionSpec extends Specification {

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

}
