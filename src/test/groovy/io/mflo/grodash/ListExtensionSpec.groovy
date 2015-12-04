package io.mflo.grodash

import spock.lang.Specification

class ListExtensionSpec extends Specification {

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

}
