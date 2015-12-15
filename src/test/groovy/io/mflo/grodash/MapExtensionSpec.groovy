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

  def 'contains() checks if a value is contained in the map'() {
    expect:
      barney.contains(36) == true
      fred.contains('fred') == true
  }

  def 'includes() is a synonym for contains()'() {
    expect:
      barney.includes(36) == barney.contains(36)
      fred.includes('fred') == fred.contains('fred')
  }

  def 'intersection() creates a map of unique key/value pairs'() {
    expect:
      [1: 1, 2: 2, 3: 3, 4: 4, 5: 5].intersection([4: 4, 5: 5, 6: 6, 7: 7, 8: 8]) == [4: 4, 5: 5]
      [1: 1, 2: 2, 3: 3, 4: 4].intersection( [1: 1.0, 2: 2, 5: 5] ) == [1: 1, 2: 2]
  }

  def 'property() gets a named property value by its path'() {
    expect:
      [a: [b: 'c']].property('a.b') == 'c'
      barney.property('spouse.name') == 'betty'
  }

}
