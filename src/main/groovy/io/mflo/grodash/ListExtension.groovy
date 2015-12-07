package io.mflo.grodash

import groovy.transform.*

/**
 * Lodash Array methods applied to Groovy Lists
 *
 * <p>Blah ... </p>
 */
@CompileDynamic class ListExtension {

  /* make a closure for matching list values, lodash-style */
  static Closure makeMatcher = { args ->
    def undefined = Math.random()
    def matcher = { template, obj ->
      return template.every { path, value ->
        // NOTE: special case when value is undefined, just looking for Groovy truth
        (value == undefined)? obj.property(path) : (obj.property(path) == value)
      }
    }
    if ((args.length == 1) && (args[0] instanceof Closure))
      return args[0]
    else if ((args.length == 1) && (args[0] instanceof Map))
      return matcher.curry(args[0])
    else if (args.length == 1)
      return matcher.curry([ (args[0]): undefined ])
    else if (args.length == 2)
      return matcher.curry([ (args[0]): args[1] ])
    else throw IllegalArgumentException("Match must be Closure, Map, property name or property name + value; found ${args}")
  }

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

  /** Creates a slice of the list excluding elements dropped from the end. Elements are dropped until the closure returns falsey. */
  static List dropRightWhile(final List self,
                             final Object... args) {
    Closure fn = makeMatcher(args)
    def result = self.collect()
    while (fn.call(result[result.size() - 1]))
      result.removeAt(result.size() - 1)
    return result
  }

  /** Creates a slice of the list excluding elements dropped from the beginning. Elements are dropped until the closure returns falsey. */
  static List dropWhile(final List self,
                        final Object... args) {
    Closure fn = makeMatcher(args)
    def result = self.collect()
    while (fn.call(result[0]))
      result.removeAt(0)
    return result
  }

  /**
   * Fills elements of the list with value from start up to, but not including, end.
   *
   * <p><b>NOTE</b> the list is mutated</p>
   */
  static List fill(final List self,
                   final Object value,
                   final int start = 0,
                   final int end = Integer.MAX_VALUE) {
    int stop = (end == Integer.MAX_VALUE)? self.size() : end
    for (int index = start; index < stop; index++)
      self[index] = value
    return self
  }

  /** Returns the index of the first element the predicate returns truthy for, starting at the beginning. */
  static int findIndex(final List self,
                       final Object... args) {
    Closure fn = makeMatcher(args)
    int index = 0
    for (; (index < self.size()) && !fn.call(self[index]); index++) { }
    return (index < self.size())? index : -1
  }

  /** Returns the index of the first element the predicate returns truthy for, starting at the end. */
  static int findLastIndex(final List self,
                           final Object... args) {
    Closure fn = makeMatcher(args)
    int index = self.size() - 1
    for (; (index >= 0) && !fn.call(self[index]); index--) { }
    return index
  }

  /** Recursively flattens a nested list. */
  static List flattenDeep(final List self) {
    self.flatten()
  }

  /**
   * Gets the index of the first occurrence of value in the list, starting from the beginning.
   *
   * <p><b>NOTE</b> if fromIndex is negative, it's used as an offset from the end.</p>
   */
  static int indexOf(final List self,
                     final Object value,
                     final int fromIndex = 0) {
    int index = (fromIndex >= 0)? fromIndex : Math.max(0, self.size() + fromIndex)
    int stop = self.size()
    for (; (index < stop) && (self[index] != value); index++) { }
    return (index < self.size())? index : -1
  }

  /** Gets all but the last element of the list. */
  static List initial(final List self) {
    self.init()
  }

  /** Creates a list of unique values that are included in all of the provided lists. */
  static List intersection(final List self,
                           final List... others) {
    others.inject(self.collect()) { result, other -> result.intersect(other) }
  }

  /**
   * Gets the index of the first occurrence of value in the list, starting from the end.
   *
   * <p><b>NOTE</b> if fromIndex is negative, it's used as an offset from the end.</p>
   */
  static int lastIndexOf(final List self,
                         final Object value,
                         final int fromIndex = -1) {
    int index = (fromIndex >= 0)? fromIndex : Math.max(0, self.size() + fromIndex)
    int stop = self.size()
    for (; (index >= 0) && (self[index] != value); index--) { }
    return (index >= 0)? index : -1
  }

  /** Gets the property value of path from all elements in the list. */
  static List pluck(final List self,
                    final def path) {
    self.inject([]) { result, obj ->
      def value = obj.property(path)
      if (value)
        result << value
      return result
    }
  }

}
