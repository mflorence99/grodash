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

  /** Calls the suppled closure asynchronously. */
  static def defer(final Closure self,
                   final Object... args) {
    Thread.startDaemon {
      self(*args)
    }
  }

  /** Calls the supplied closure after N milliseconds. */
  static def delay(final Closure self,
                   final long wait,
                   final Object... args) {
    Thread.startDaemon {
      Thread.sleep(wait)
      self(*args)
    }
  }

  /** Creates a closure that negates the return of the supplied closure. */
  static Closure negate(final Closure self) {
    return { Object... args -> !self(*args) }
  }

  /** Creates a closure that calls the supplied closure only once. */
  static Closure once(final Closure self) {
    def result = null
    return { Object... args -> result ?: (result = self(*args)) }
  }

  /** Calls the supplied closure N times. */
  static def times(final Closure self,
                   final long times,
                   final Object... args) {
    (0..times-1).inject([]) { result, n -> result << self(args) }
  }

}
