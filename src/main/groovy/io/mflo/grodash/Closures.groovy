package io.mflo.grodash

import groovy.transform.*

/** Lodash methods cast as Groovy Closures */

@CompileDynamic class Closures {

  /* Creates a closure that returns a constant. */
  static Closure constant = { value -> return { value } }

  /* Returns the first argument passed to it. */
  static def identity = { Object... args -> args? args[0] : null }

  /** Creates a closure that returns the property value at a path on a given object. */
  static Closure property(final String path) {
    def properties = path.split(/\./)
    return { obj ->
      properties.every { obj = obj[it] }
      return obj
    }
  }

  /** Creates a closure that returns the property value at a path on a given object. */
  static Closure propertyOf(final def arg) {
    def obj = arg
    return { path ->
      path.split(/\./).every { obj = obj[it] }
      return obj
    }
  }

}
