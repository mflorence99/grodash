package io.mflo.grodash

import groovy.transform.*

/** Lodash methods applied to Groovy Lists */

@CompileDynamic class Helpers {

  /* useful identity closure */
  static Closure identity = { it }

  /* make a closure for accessing list values, lodash-style */
  static Closure makeAccessor = { arg ->
    if (arg == null)
      return Helpers.identity
    else if (arg instanceof Closure)
      return arg
    else return { path, obj -> obj[path] }.curry(arg)
  }

  /* make a closure for comparing list values, lodash-style */
  static Closure makeComparator = { arg ->
    Closure fn = Helpers.makeAccessor(arg)
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
    if (args.length == 0)
      return Helpers.identity
    else if ((args.length == 1) && (args[0] instanceof Closure))
      return args[0]
    else if ((args.length == 1) && (args[0] instanceof Map))
      return matcher.curry(args[0])
    else if (args.length == 1)
      return matcher.curry([ (args[0]): undefined ])
    else if (args.length == 2)
      return matcher.curry([ (args[0]): args[1] ])
    else throw new IllegalArgumentException("Match must be Closure, Map, property name or property name + value; found ${args}")
  }

  /* make a closure for zipping and unzipping, lodash-style */
  static Closure makeZipper = { tuples, args ->
    Closure zipper = Helpers.identity
    args.each { arg ->
      if (arg instanceof Closure)
        zipper = (Closure)arg
      else tuples << arg
    }
    return zipper
  }

}
