package io.mflo.grodash

import spock.lang.*

class MapExtensionSpec extends Specification {

  @Shared def barney = [ 'user': 'barney', 'age': 36, 'spouse': [ 'name': 'betty' ] ]
  @Shared def fred = ['name': 'fred', 'age': 35, 'active': false, 'spouse': ['name': 'wilma']]
  @Shared def pebbles = ['name': 'pebbles', 'age': 2, 'active': true]

  def 'at() extracts the given keys from the map'() {
    expect:
      barney.at('age', 'spouse.name') == [36, 'betty']
      pebbles.at('age', 'spouse.name') == [2]
  }

  def 'property() gets a named property value by its path'() {
    expect:
      [a: [b: 'c']].property('a.b') == 'c'
      barney.property('spouse.name') == 'betty'
  }

}
