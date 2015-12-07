package io.mflo.grodash

import groovy.transform.*

/**
 * Lodash Array methods applied to Groovy Lists
 *
 * <p>Blah ... </p>
 */
@CompileDynamic class ListExtension {

  /* useful identity closure */
  static Closure identity = { it }

  /* make a closure for comparing list values, lodash-style */
  static Closure makeComparator = { arg ->
    Closure fn = null
    if (arg == null)
      fn = identity
    else if (arg instanceof Closure)
      fn = arg
    else fn = { path, obj -> obj[path] }.curry(arg)
    return { obj, value -> fn.call(obj) <=> fn.call(value) }
  }

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

  /** Splits a list into a groups the length of size. If the list can’t be split evenly, the final chunk will be the remaining items. */
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

  /** Creates a slice of the list excluding items dropped from the end. Items are dropped until the closure returns falsey. */
  static List dropRightWhile(final List self,
                             final Object... args) {
    Closure fn = makeMatcher(args)
    def result = self.collect()
    while (fn.call(result[result.size() - 1]))
      result.removeAt(result.size() - 1)
    return result
  }

  /** Creates a slice of the list excluding items dropped from the beginning. Items are dropped until the closure returns falsey. */
  static List dropWhile(final List self,
                        final Object... args) {
    Closure fn = makeMatcher(args)
    def result = self.collect()
    while (fn.call(result[0]))
      result.removeAt(0)
    return result
  }

  /**
   * Fills items of the list with value from start up to, but not including, end.
   *
   * <p><b>NOTE</b> the list is mutated.</p>
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

  /** See zipObject */
  static Map object(final List self,
                    final List values = null) {
    self.zipObject(values)
  }

  /** Gets the property value of path from all items in the list. */
  static List pluck(final List self,
                    final def path) {
    self.inject([]) { result, obj ->
      def value = obj.property(path)
      if (value)
        result << value
      return result
    }
  }

  /**
   * Removes all provided values from the list.
   *
   * <p><b>NOTE</b> the list is mutated.</p>
   */
  static List pull(final List self,
                   final Object... args) {
    def values = args as Set
    for (int index = 0; index < self.size(); ) {
      if (values.contains(self.getAt(index)))
        self.removeAt(index)
      else index++
    }
    return self
  }

  /**
   * Removes all items at the provided indexes from the list.
   *
   * <p><b>NOTE</b> the list is mutated.</p>
   */
  static List pullAt(final List self,
                     final Object... args) {
    def indexes = args.flatten() as SortedSet
    indexes.eachWithIndex { index, offset ->
      self.removeAt(index - offset)
    }
    return self
  }

  /** Removes and returns items from the list that closure returms truthy for. */
  static List removeElements(final List self,
                             final Object... args) {
    Closure fn = makeMatcher(args)
    self.inject([]) { result, item ->
      if (fn.call(item))
        result << item
      return result
    }
  }

  /** Gets all but the first element of the list. */
  static List rest(final List self) {
    self.tail()
  }

  /** Creates a slice of the list from start up to, but not including, end. */
  static List slice(final List self,
                    final int start = 0,
                    final int end = Integer.MAX_VALUE) {
    int stop = (end == Integer.MAX_VALUE)? self.size() : end
    return self.subList(start, stop).collect()
  }

  /** Uses a binary search to determine the lowest index at which value should be inserted into the list in order to maintain its sort order. */
  static int sortedIndex(final List self,
                         final Object value,
                         final Object arg = null) {
    int index = Collections.binarySearch(self, value, makeComparator(arg))
    // NOTE: if value not found, Java says return is (-(insertion point) - 1)
    return (index >= 0)? index : ((index + 1) * -1)
  }

  /** Uses a binary search to determine the highest index at which value should be inserted into the list in order to maintain its sort order. */
  static int sortedLastIndex(final List self,
                             final Object value,
                             final Object arg = null) {
    Closure fn = makeComparator(arg)
    int index = Collections.binarySearch(self, value, fn)
    // NOTE: if value not found, Java says return is (-(insertion point) - 1)
    if (index < 0)
      return (index + 1) * -1
    else {
      while ((++index < self.size()) && (fn.call(self.getAt(index), value) == 0)) { }
      return index
    }
  }

  /** Creates a slice of the list with items taken from the end. Items are taken until the closure returns falsey. */
  static List takeRightWhile(final List self,
                             final Object... args) {
    Closure fn = makeMatcher(args)
    def result = []
    for (int index = self.size() - 1; (index >= 0) && fn.call(self[index]); index--)
      result << self.getAt(index)
    return result.reverse()
  }

  /** Creates a slice of the list with items taken from the beginning. Items are taken until the closure returns falsey. */
  static List takeWhile(final List self,
                        final Object... args) {
    Closure fn = makeMatcher(args)
    def result = []
    for (int index = 0; (index < self.size()) && fn.call(self[index]); index++)
      result << self.getAt(index)
    return result
  }

  /**
   * Returns an object composed from a list of names and values
   *
   * <p><b>NOTE</b> Normally, the list is assumed to be a list of pairs of names and
   * values. However, if a values parameter is supplied, the list is of names, and
   * the values parameter is the list of corresponding values.</p>
   */
  static Map zipObject(final List self,
                       final List values = null) {
    def result = [:]
    self.eachWithIndex { item, index ->
      if (values == null)
        result[item[0]] = item[1]
      else result[item] = values[index]
    }
    return result
  }

}
