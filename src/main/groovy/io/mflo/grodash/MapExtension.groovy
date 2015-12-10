package io.mflo.grodash

import groovy.transform.*

/**
 * Lodash Collection methods applied to Groovy Maps
 *
 * <p>Blah ... </p>
 */
@CompileDynamic class MapExtension {

  /** Creates a list of items corresponding to the given keys of the map. */
  static List at(final Map self,
                 final String... paths) {
    paths.flatten().inject([]) { result, path ->
      def value = self.property(path)
      if (value != null)
        result << value
      return result
    }
  }

  /** Deep access object by property as in a.b.c */
  static Object property(final Map self,
                         final String path) {
    def obj = self
    path.split(/\./).every { obj = obj[it] }
    return obj
  }

}
