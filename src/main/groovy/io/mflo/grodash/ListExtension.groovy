package io.mflo.grodash

import groovy.transform.*

/**
 * Lodash Array methods applied to Groovy Lists
 *
 * <p>Blah ... </p>
 */
@CompileDynamic class ListExtension {

  /** Splits a list into a groups the length of size. If the list can’t be split evenly, the final chunk will be the remaining elements. */
  static List chunk(final List self,
                    final int size = 1) {
    List result = []
    if ((size >= 1) && self.size()) {
      def limit = self.size() - 1
      for (int i = 0; i <= limit; i += size) {
        def group = self.getAt(i..Math.min(limit, i + size - 1))
        if (group)
          result << group
        else break
      }
    }
    return result
  }

  /** Creates a new list with all falsey values removed, according to Groovy truth. */
  static List compact(final List self) {
    self.findAll()
  }

  /** Creates a new list from the unique values not included in the other provided lists. */
  static List difference(final List self,
                         final List... excludes) {
    Set result = self as Set
    excludes.each { exclude -> result = result - exclude }
    return result as List
  }

}
