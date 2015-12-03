package io.mflo.groovy_lodash

import groovy.transform.*

/**
 * Lodash Array methods applied to Groovy Lists
 *
 * <p>Blah ... </p>
 */
@CompileDynamic class ListExtension {

  /**
   * Splits a list into a groups the length of size.
   * If the list can’t be split evenly, the final chunk will be the remaining elements.
   */
  static List chunk(final List self,
                    final int size = 1) {
    List<List> result = []
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

}
