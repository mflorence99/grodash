package io.mflo.grodash

import groovy.transform.*

import static io.mflo.grodash.Closures.*

/** Lodash Collection methods applied to Groovy Maps */

@CompileDynamic class MapExtension {

  /** Creates a list of items corresponding to the given keys of the map. */
  static List at(final Map self,
                 final String... paths) {
    paths.flatten().inject([]) { result, path ->
      def value = property(path)(self)
      if (value != null)
        result << value
      return result
    }
  }

  /** Checks if a value is contained in the map. */
  static boolean contains(final Map self,
                          final Object value) {
    self.values().contains(value)
  }

  /** Creates a new map with all the keys of inner maps flattened. */
  static Map flattenDeep(final Map self,
                         final Map result = [:],
                         final String key = null) {
    self.inject(result) { obj, name, value ->
      String k = key? "${key}.${name}" : "${name}"
      if (value instanceof Map)
        value.flattenDeep(obj, k)
      else obj[k] = value
      return obj
    }
  }

  /** includes() is a synonym for contains(). */
  static boolean includes(final Map self,
                          final Object value) {
    self.contains(value)
  }

  /** Creates a map of unique key/values that are included in all of the provided maps. */
  static Map intersection(final Map self,
                          final Map... others) {
    others.inject(self) { result, other -> result.intersect(other) }
  }

}
