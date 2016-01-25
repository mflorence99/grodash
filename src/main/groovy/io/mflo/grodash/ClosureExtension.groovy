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
  static void defer(final Closure self,
                    final Object... args) {
    new Timer().schedule({
      self(*args)
    } as TimerTask, 1)
  }

  /** Calls the supplied closure after N milliseconds. */
  static void delay(final Closure self,
                    final long wait,
                    final Object... args) {
    new Timer().schedule({
      self(*args)
    } as TimerTask, wait)
  }

  /** Creates a closure that memoizes the result of calling the given closure. If resolver is provided it determines the cache key for storing the result based on the arguments provided to the memoized function. By default, the stringified args is used. */
  static Closure memoize(final Closure self,
                         final Closure arg = null) {
    Closure resolver = arg ?: { Object... args -> args as String }
    Map cache = new WeakHashMap()
    return { Object... args ->
      def key = resolver(args)
      def result = cache.get(key)
      if (result == null) {
        result = self(*args)
        cache.put(key, result)
      }
      return result
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

  /** Creates a closure that taps into the return of the supplied closure. */
  static def tap(final Closure self,
                 final Closure interceptor) {
    return { Object... args ->
      interceptor(*args);
      return self(*args)
    }
  }

  /** Creates a closure that taps into the return of the supplied closure. */
  static def thru(final Closure self,
                  final Closure interceptor) {
    return { Object... args -> interceptor(*args) }
  }

  /** Calls the supplied closure N times. */
  static def times(final Closure self,
                   final long times,
                   final Object... args) {
    (0..times-1).inject([]) { result, n -> result << self(args) }
  }

}
