package io.mflo.grodash

import groovy.transform.*

/** Lodash Collection methods applied to Groovy Closures */

@CompileDynamic class ClosureExtension {

  /** Creates a closure that calls the supplied closure after it has been invoked N times. */
  static Closure after(final Closure self,
                       final int times) {
    int n = times
    return { Object... args -> (--n > 0)? args[0] : self(*args) }
  }

  /** Creates a closure that calls the supplied closure until it has been invoked N times. */
  static Closure before(final Closure self,
                       final int times) {
    int n = times
    def result = null
    return { Object... args -> (--n > 0)? (result = self(*args)) : result }
  }

}
