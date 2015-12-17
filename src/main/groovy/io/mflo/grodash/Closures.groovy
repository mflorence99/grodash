package io.mflo.grodash

import groovy.transform.*

/** Lodash methods cast as Groovy Closures */

@CompileDynamic class Closures {

  /* Creates a closure that returns a constant. */
  static Closure constant = { value -> return { value } }

  /* Returns the first argument passed to it. */
  static def identity = { Object... args -> args? args[0] : null }

}
