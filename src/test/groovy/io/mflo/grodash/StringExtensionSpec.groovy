package io.mflo.grodash

import spock.lang.*

class StringExtensionSpec extends Specification {

  def 'contains() checks if a substring is contained in a string'() {
    expect:
      'pebbles'.contains('eb') == true
      'pebbles'.contains('eb', 2) == false
  }

  def 'includes() is a synonym for contains()'() {
    expect:
      'pebbles'.includes('eb') == 'pebbles'.contains('eb')
      'pebbles'.includes('eb', 2) == 'pebbles'.contains('eb', 2)
  }

}
