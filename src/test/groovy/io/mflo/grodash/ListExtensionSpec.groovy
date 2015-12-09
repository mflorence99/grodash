package io.mflo.grodash

import spock.lang.*

class ListExtensionSpec extends Specification {

  @Shared def flintstones = [
    ['name': 'barney',  'active': false, 'spouse': ['name': 'betty']],
    ['name': 'fred',    'active': false, 'spouse': ['name': 'wilma']],
    ['name': 'pebbles', 'active': true]
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
    expect:
      [1, 2, 3].difference([4, 2]) == [1, 3]
  }

  def 'dropRightWhile() creates a slice of the list, dropping items from the end'() {
    expect:
      [1, 3, 2].dropRightWhile{ it < 3 } == [1, 3]
  }

  def 'dropRightWhile() also emulates extended lodash semantics and syntax'() {
    expect:
      flintstones.dropRightWhile(['name': 'pebbles', 'active': true]).pluck('name') == ['barney', 'fred']
  }

  def 'dropWhile() creates a slice of the list, dropping items from the beginning'() {
    expect:
      [1, 3, 2].dropWhile{ it < 3 } == [3, 2]
  }

  def 'dropWhile() also emulates extended lodash semantics and syntax'() {
    expect:
      flintstones.dropWhile('spouse.name').pluck('name') == ['pebbles']
      flintstones.dropWhile(['active': false]).pluck('name') == ['pebbles']
  }

  def 'fill() fills items of a list with a value'() {
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

  def 'first() gets the first element of the list'() {
    expect:
      [1, 2, 3].first() == 1
  }

  def 'flattendDeep() recursively flattens a nested list'() {
    expect:
      [1, [2, 3, [4]]].flattenDeep() == [1, 2, 3, 4]
  }

  def 'indexOf() gets the index of the first occurence of value, starting from beginning'() {
    expect:
      [1, 2, 1, 2].indexOf(2) == 1
      [1, 2, 1, 2].indexOf(2, 2) == 3
      [1, 2, 1, 2].indexOf(2, -3) == 1
      [1, 2, 1, 2].indexOf(3) == -1
  }

  def 'initial() gets all but the last element of the list'() {
    expect:
      [1, 2, 3].initial() == [1, 2]
  }

  def 'intersection() creates a list of unique values'() {
    expect:
      [1, 2, 3, 4, 5].intersection([4, 5, 6, 7, 8]) == [4, 5]
      [1, 2].intersection([4, 2], [2, 1]) == [2]
  }

  def 'last() gets the last element of the list'() {
    expect:
      [1, 2, 3].last() == 3
  }

  def 'lastIndexOf() gets the index of the first occurence of value, starting from end'() {
    expect:
      [1, 2, 1, 2].lastIndexOf(2) == 3
      [1, 2, 1, 2].lastIndexOf(2, 2) == 1
      [1, 2, 1, 2].lastIndexOf(2, -3) == 1
      [1, 2, 1, 2].lastIndexOf(3) == -1
  }

  def 'object() is a synonym for zipObject'() {
    expect:
      [['fred', 30], ['barney', 40]].object() == [['fred', 30], ['barney', 40]].zipObject()
      ['fred', 'barney'].object([30, 40])  == ['fred', 'barney'].zipObject([30, 40])
  }

  def 'pluck() gets a named property value from all items'() {
    expect:
      [[a: 1], [a: 2]].pluck('a') == [1, 2]
      flintstones.pluck('name') == ['barney', 'fred', 'pebbles']
      flintstones.pluck('spouse.name') == ['betty', 'wilma']
  }

  def 'pull() removes all provided values from the list'() {
    expect:
      [1, 2, 3, 1, 2, 3].pull(2, 3) == [1, 1]
  }

  def 'pullAt() removes items at the provided indexes from the list'() {
    expect:
      [5, 10, 15, 20].pullAt(1, 3) == [5, 15]
      [5, 10, 15, 20].pullAt([1, 3], [3, 1]) == [5, 15]
  }

  def 'removeElements() removes and returns matching items from the list'() {
    expect:
      [1, 2, 3, 4].removeElements{ it % 2 } == [2, 4]
  }

  def 'rest() gets all but the first element of the list'() {
    expect:
      [1, 2, 3].rest() == [2, 3]
  }

  def 'tail() and rest() are synonyms'() {
    expect:
      [1, 2, 3].tail() == [1, 2, 3].rest()
  }

  def 'slice() creates a slice of the list from start to end'() {
    expect:
      [1, 2, 3, 4, 5].slice() == [1, 2, 3, 4, 5]
      [1, 2, 3, 4, 5].slice(2) == [3, 4, 5]
      [1, 2, 3, 4, 5].slice(2, 4) == [3, 4]
  }

  def 'sortedIndex() finds lowest insertion point for value in sorted list'() {
    def dict = ['data': ['thirty': 30, 'forty': 40, 'fifty': 50]]
    expect:
      [1, 2, 3, 4, 5].sortedIndex(0) == 0
      [1, 2, 3, 4, 5].sortedIndex(3) == 2
      [1, 2, 3, 4, 5].sortedIndex(6) == 5
      [['x': 30], ['x': 50]].sortedIndex(['x': 40], 'x') == 1
      ['thirty', 'fifty'].sortedIndex('forty') { dict.data[it] } == 1
  }

  def 'sortedLastIndex() finds highest insertion point for value in sorted list'() {
    expect:
      [4, 4, 5, 5].sortedLastIndex(5) == 4
  }

  def 'take() creates a slice of a list with n elements taken from the beginning'() {
    expect:
     [1, 2, 3].take(2) == [1, 2]
  }

  def 'takeRight() creates a slice of a list with n elements taken from the end'() {
    expect:
     [1, 2, 3].takeRight(2) == [2, 3]
  }

  def 'takeRightWhile() creates a slice of the list, taking items from the end'() {
    expect:
      [1, 2, 3].takeRightWhile{ it > 1 } == [2, 3]
  }

  def 'takeRightWhile() also emulates extended lodash semantics and syntax'() {
    expect:
      flintstones.takeRightWhile(['name': 'pebbles', 'active': true]).pluck('name') == ['pebbles']
  }

  def 'takeWhile() creates a slice of the list, takeing items from the beginning'() {
    expect:
      [1, 2, 3].takeWhile{ it < 3 } == [1, 2]
  }

  def 'takeWhile() also emulates extended lodash semantics and syntax'() {
    expect:
      flintstones.takeWhile('spouse.name').pluck('spouse.name') == ['betty', 'wilma']
      flintstones.takeWhile(['active': false]).pluck('name') == ['barney', 'fred']
  }

  def 'union() creates a list of unique values, in order, from all of the provided lists'() {
    expect:
      [1, 2].union([4, 2], [2, 1]) == [1, 2, 4]
  }

  def 'uniq() a duplicate-free version of a list'() {
    expect:
      [2, 1, 2].uniq() == [2, 1]
      [1, 2.5, 1.5, 2].uniq{ Math.floor(it) } == [1, 2.5]
      [['x': 1], ['x': 2], ['x': 1]].uniq('x') == [['x': 1], ['x': 2]]
  }

  def 'unique() is a synonym for uniq()'() {
    expect:
      [2, 1, 2].unique() == [2, 1, 2].uniq()
      [1, 2.5, 1.5, 2].unique{ Math.floor(it) } == [1, 2.5, 1.5, 2].uniq{ Math.floor(it) }
      [['x': 1], ['x': 2], ['x': 1]].unique('x') == [['x': 1], ['x': 2], ['x': 1]].uniq('x')
  }

  def 'unzip() ungroups a list of grouped elements'() {
    expect:
      [['fred', 30, true], ['barney', 40, false]].unzip() == [['fred', 'barney'], [30, 40], [true, false]]
  }

  def 'unzipWith() ungroups a list of grouped elements'() {
    expect:
      [[2, 10, 200], [1, 20, 100]].unzipWith({ it.min() }) == [1, 10, 100]
  }

  def 'without() removes all provided values from the list'() {
    expect:
      [1, 2, 3, 1, 2, 3].without(2, 3) == [1, 1]
  }

  def 'xor() returns the symmetric difference'() {
    expect:
      [1, 2].xor([4, 2]) == [1, 4]
  }

  def 'zip() creates a list of grouped elements'() {
    expect:
      ['fred', 'barney'].zip([30, 40], [true, false]) == [['fred', 30, true], ['barney', 40, false]]
  }

  def 'zipObject() returns an object composed from a list of names and values'() {
    expect:
      [['fred', 30], ['barney', 40]].zipObject() == ['fred': 30, 'barney': 40]
      ['fred', 'barney'].zipObject([30, 40])  == ['fred': 30, 'barney': 40]
  }

  def 'zipWith() creates a list of grouped elements'() {
    expect:
      [1, 2].zipWith([10, 20], [100, 200], { it.max() }) == [100, 200]
      ['fred', 'barney'].zipWith([30, 40], [true, false]) == [['fred', 30, true], ['barney', 40, false]]
  }

}
