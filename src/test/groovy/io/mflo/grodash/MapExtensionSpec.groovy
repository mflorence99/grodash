package io.mflo.grodash

import spock.lang.*

class MapExtensionSpec extends Specification {

  def 'property() gets a named property value by its path'() {
    def obj = [ 'user': 'barney', 'age': 36, 'spouse': [ 'name': 'betty' ] ]
    expect:
      [a: [b: 'c']].property('a.b') == 'c'
      obj.property('spouse.name') == 'betty'
  }

}
