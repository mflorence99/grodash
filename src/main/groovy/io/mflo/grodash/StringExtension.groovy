package io.mflo.grodash

import groovy.transform.*

/** Lodash Collection methods applied to Groovy Strings */

@CompileDynamic class StringExtension {

  /** Checks if a substring is contained within a string, starting at a specified index. */
  static boolean contains(final String self,
                          final String substring,
                          final int fromIndex = 0) {
    self.indexOf(substring, fromIndex) != -1
  }

  /** includes() is a synonym for contains() */
  static boolean includes(final String self,
                          final String substring,
                          final int fromIndex = 0) {
    self.contains(substring, fromIndex)
  }

}
