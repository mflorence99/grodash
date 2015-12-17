package io.mflo.grodash

import groovy.transform.*

/** Lodash methods cast as Groovy Closures */

@CompileDynamic class Closures {

  /* Creates a closure that returns a constant. */
  static Closure constant = { value -> return { value } }

  /* Returns the first argument passed to it. */
  static def identity = { Object... args -> args? args[0] : null }

  /** Creates a closure that performs a deep comparison between a given object and a template, returning true if the given object has equivalent property values, else false. */
  static Closure matches = { template ->
    return { obj ->
      template.flattenDeep().every { path, value -> property(path)(obj) == value }
    }
  }

  /** Creates a closure that compares the property value of path on a given object to value. */
  static Closure matchesProperty = { path, value ->
    return { obj ->
      (value == true)? !!property(path)(obj) : (property(path)(obj) == value)
    }
  }

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
