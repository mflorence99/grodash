package io.mflo.grodash

import groovy.transform.*

/**
 * Lodash Collection methods applied to Groovy Maps
 *
 * <p>Blah ... </p>
 */
@CompileDynamic class MapExtension {

  static Object property(final Map self,
                         final String path) {
    def obj = self
    path.split(/\./).every { obj = obj[it] }
    return obj
  }

}
