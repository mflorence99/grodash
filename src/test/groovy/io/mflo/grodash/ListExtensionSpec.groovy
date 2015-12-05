package io.mflo.grodash

import spock.lang.*

class ListExtensionSpec extends Specification {

  @Shared def flintstones = [
    [ 'name': 'barney',  'active': false, 'spouse': [ 'name': 'betty' ] ],
    [ 'name': 'fred',    'active': false, 'spouse': [ 'name': 'wilma' ] ],
    [ 'name': 'pebbles', 'active': true ]
  ]

  def 'chunk() splits a List into a List of Lists'() {
    def empty = []
    def list = [1, 2, 3, 4, 5]
    expect:
      empty.chunk(999) == []
      list.chunk(0) == []
      list.chunk() == [[1], [2], [3], [4], [5]]
      list.chunk(1) == [[1], [2], [3], [4], [5]]
      list.chunk(2) == [[1, 2], [3, 4], [5]]
      list.chunk(3) == [[1, 2, 3], [4, 5]]
      list.chunk(4) == [[1, 2, 3, 4], [5]]
      list.chunk(5) == [[1, 2, 3, 4, 5]]
      list.chunk(6) == [[1, 2, 3, 4, 5]]
  }

  def 'compact() creates a new list with all falsey values removed'() {
    def list = [0, 1, false, 2, '', 3];
    expect:
      list.compact() == [1, 2, 3]
      list.compact() == list.findAll()
  }

  def 'difference() creates a new list from unique values not in provided lists'() {
    def list = [1, 2, 3]
    expect:
      list.difference([4, 2]) == [1, 3]
  }

  def 'dropRightWhile() creates a slice of the list, dropping elements from the end'() {
    def list = [1, 3, 2]
    expect:
      list.dropRightWhile{ it < 3 } == [1, 3]
  }

  def 'dropRightWhile() also emulates extended lodash semantics and syntax'() {
    expect:
      flintstones.dropRightWhile([ 'name': 'pebbles', 'active': true ]).pluck('name') == ['barney', 'fred']
  }

  def 'dropWhile() creates a slice of the list, dropping elements from the beginning'() {
    def list = [1, 3, 2]
    expect:
      list.dropWhile{ it < 3 } == [3, 2]
  }

  def 'dropWhile() also emulates extended lodash semantics and syntax'() {
    expect:
      flintstones.dropWhile('spouse.name').pluck('name') == ['pebbles']
      flintstones.dropWhile([ 'active': false ]).pluck('name') == ['pebbles']
  }

  def 'fill() fills elements of a list with a value'() {
    expect:
      [1, 2, 3].fill(0) == [0, 0, 0]
      [1, 2, 3].fill(0, 1) == [1, 0, 0]
      [].fill(1, 0, 5) == [1, 1, 1, 1, 1]
  }

  def 'findIndex() returns the index of the first element the predicate returns truthy for'() {
    expect:
      [3, 2, 1].findIndex{ it == 3 } == 0
      flintstones.findIndex('active') == 2
      flintstones.findIndex('active', false) == 0
      flintstones.findIndex(['name': 'fred', 'active': false]) == 1
      flintstones.findIndex(['name': 'bambam']) == -1
  }

  def 'findIndex() returns the index of the last element the predicate returns truthy for'() {
    expect:
      [3, 2, 1].findLastIndex{ it == 1 } == 2
      flintstones.findLastIndex('active') == 2
      flintstones.findLastIndex('active', false) == 1
      flintstones.findLastIndex(['name': 'fred', 'active': false]) == 1
      flintstones.findLastIndex(['name': 'bambam']) == -1
  }

  def 'pluck() gets a named property value from all elements'() {
    expect:
      [[a: 1], [a: 2]].pluck('a') == [1, 2]
      flintstones.pluck('name') == ['barney', 'fred', 'pebbles']
      flintstones.pluck('spouse.name') == ['betty', 'wilma']
  }

}
