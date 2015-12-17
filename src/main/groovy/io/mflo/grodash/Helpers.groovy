package io.mflo.grodash

import groovy.transform.*

import static io.mflo.grodash.Closures.*;

/** Helper closures */

@CompileDynamic class Helpers {

  /* make a closure for accessing list values, lodash-style */
  static Closure makeAccessor = { arg ->
    if (arg == null)
      return identity
    else if (arg instanceof Closure)
      return arg
    else return { path, obj -> property(path)(obj) }.curry(arg)
  }

  /* make a closure for comparing list values, lodash-style */
  static Closure makeComparator = { arg ->
    Closure fn = Helpers.makeAccessor(arg)
    return { obj, value -> fn(obj) <=> fn(value) }
  }

  /* make a closure for matching list values, lodash-style */
  static Closure makeMatcher = { args ->
    if (args.length == 0)
      return identity
    else if ((args.length == 1) && (args[0] instanceof Closure))
      return args[0]
    else if ((args.length == 1) && (args[0] instanceof Map))
      return matches(args[0])
    else if (args.length == 1)
      return matchesProperty(args[0], true)
    else if (args.length == 2)
      return matchesProperty(args[0], args[1])
    else throw new IllegalArgumentException("Match must be Closure, Map, property name or property name + value; found ${args}")
  }

  /* make a closure for zipping and unzipping, lodash-style */
  static Closure makeZipper = { tuples, args ->
    Closure zipper = identity
    args.each { arg ->
      if (arg instanceof Closure)
        zipper = (Closure)arg
      else tuples << arg
    }
    return zipper
  }

}
