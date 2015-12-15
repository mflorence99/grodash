package io.mflo.grodash

import groovy.transform.*

/** Lodash Collection methods applied to Groovy Closures */

@CompileDynamic class ClosureExtension {

  /** Creates a closure that calls the supplied closure after it has been invoked N times. */
  static Closure after(final Closure self,
                       final long times) {
    long n = times
    return { Object... args -> (--n > 0)? args[0] : self(*args) }
  }

  /** Creates a closure that calls the supplied closure with a maximum of N arguments. */
  static Closure ary(final Closure self,
                     final int n) {
    return { Object... args -> self(*(args.take(n))) }
  }

  /** Creates a closure that calls the supplied closure until it has been invoked N times. */
  static Closure before(final Closure self,
                        final long times) {
    long n = times
    def result = null
    return { Object... args -> (--n > 0)? (result = self(*args)) : result }
  }

  static def delay(final Closure self,
                   final long wait,
                   final Object... args) {
    Thread.startDaemon {
      Thread.sleep(wait)
      self(*args)
    }
  }

}
