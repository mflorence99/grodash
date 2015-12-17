package io.mflo.grodash

import spock.lang.*

import static io.mflo.grodash.Closures.*;

class ClosuresSpec extends Specification {

  def 'constant() creates a closure that returns a constant'() {
    expect:
      constant(42)() == 42
      [1, 2, 3].map(constant(5)) == [5, 5, 5]
  }

  def 'identity() returns the first argument passed to it'() {
    expect:
      identity(42) == 42
      identity(1, 2, 3) == 1
      identity([x: 'y']) == [x: 'y']
  }

  def 'property() creates a closure that returns a value at a path'() {
    expect:
      property('a.b')([a: [b: 'c']]) == 'c'
  }

  def 'propertyOf() creates a closure that returns a value at a path'() {
    expect:
      propertyOf([a: [b: 'c']])('a.b') == 'c'
  }

}
