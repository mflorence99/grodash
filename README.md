# A Groovy Port of [lodash](https://lodash.com/)

* [How to Install](#install)
* [How to Build](#build)
* [Groovy Methods](#index)
* [Open Issues](#todo)

This is a spare-time project, and our objective is to port all of the [lodash v3.x](https://lodash.com/) functions and methods to Groovy that are not already part of Groovy itself, like `each`, for example.

We attempt to combine idiomatic Groovy syntax with lodash semantics. So, for example, `_.chunk([1, 2, 3], 2)` in lodash becomes `[1, 2, 3].chunk(2)` in Groovy and the result is `[[1, 2], [3]]` in both.

Each ported function and method is backed by a thorough [Spock](http://spockframework.github.io/spock/docs/1.0/index.html) unit test to validate the equivalence. We will make no attempt to replicate the lodash documentation in Groovydoc format, although we plan to host a very basic Groovydoc site.

We will die happy if we can make our code as performant as lodash! In the meantime, our only objective is to make it correct. A later release will focus on optimizations.

## <a name='install'>How to Install

> TBD publish JAR on Maven / JCenter etc

## <a name='build'>How to Build from Source and Run Unit Tests

```sh
mkdir -p ~/whatever/grodash
cd ~/whatever/grodash
git clone git@github.com:mflorence99/grodash.git
gradle test
```

## <a name='index'>Groovy Methods and lodash Equivalents

> The unit tests are a great repository of more complete examples.

| Groovy | lodash | Expect |
| --- | --- | --- |
| `after` | [`_.after`](https://lodash.com/docs#after) | `(0..9).inject('', { p, q -> p += q }.after(6)) == '56789'` |
| `all` | [`_.all`](https://lodash.com/docs#all) | `[true, 1, null, 'yes'].all() == false` |
| [`any`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#any%28groovy.lang.Closure%29)<sup>[1]</sup> | [`_.any`](https://lodash.com/docs#any) | `[true, 1, null, 'yes'].any() == true` |
| `ary` | [`_.ary`](https://lodash.com/docs#ary) | `['6', '8', '10'].map(Integer.&parseInt.ary(1)) == [6, 8, 10]` |
| `at` | [`_.at`](https://lodash.com/docs#at) | `['a', 'b', 'c'].at(0, 2) == ['a', 'c']` |
| `before` | [`_.before`](https://lodash.com/docs#before) | `(0..9).inject('', { p, q -> p += q }.before(6)) == '01234'` |
| `chunk` | [`_.chunk`](https://lodash.com/docs#chunk) | `[1, 2, 3].chunk(2) == [[1, 2], [3]]` |
| [`collect`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#collect%28groovy.lang.Closure%29)<sup>[1,2]</sup> | [`_.collect`](https://lodash.com/docs#collect) | `[1, 2, 3].collect{ it * 3 } == [3, 6, 9]` |
| `compact` | [`_.compact`](https://lodash.com/docs#compact) | `[0, 1, false, 2, '', 3].compact() == [1, 2, 3]` |
| `constant` | [`_.constant`](https://lodash.com/docs#constant) | `[1, 2, 3].map(constant(5)) == [5, 5, 5]` |
| [`contains`](http://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#contains-java.lang.Object-)<sup>[1,2]</sup> | [`_.contains`](https://lodash.com/docs#contains) | `[1, 2, 3].contains(1) == true` |
| [`countBy`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#countBy%28groovy.lang.Closure%29)<sup>[1,6]</sup> | [`_.countBy`](https://lodash.com/docs#countBy) | `[4.3, 6.1, 6.4].countBy { Math.floor(it) as String } == ['4.0': 1, '6.0': 2]` |
| `defer` | [`_.defer`](https://lodash.com/docs#defer) | `({ println it }).defer('Hello, World!') // prints Hello, World asynchronously` |
| `delay` | [`_.delay`](https://lodash.com/docs#delay) | `({ println it }).delay(1000, 'Hello, World!') // prints Hello, World after 1 sec` |
| `difference` | [`_.difference`](https://lodash.com/docs#difference) | `[1, 2, 3].difference([4, 2]) == [1, 3]` |
| [`drop`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#drop%28int%29)<sup>[1]</sup> | [`_.drop`](https://lodash.com/docs#drop) | `[1, 2, 3].drop(2) == [3]` |
| [`dropRight`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#dropRight%28int%29)<sup>[1]</sup> | [`_.dropRight`](https://lodash.com/docs#dropRight) | `[1, 2, 3].dropRight(2) == [1]` |
| `dropRightWhile`<sup>[2]</sup> | [`_.dropRightWhile`](https://lodash.com/docs#dropRightWhile) | `[1, 3, 2].dropRightWhile{ it < 3 } == [1, 3]` |
| [`dropWhile`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#dropWhile%28groovy.lang.Closure%29)<sup>[1, 2]</sup> | [`_.dropWhile`](https://lodash.com/docs#dropWhile) | `[1, 3, 2].dropWhile{ it < 3 } == [3, 2]` |
| [`every`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#every%28groovy.lang.Closure%29)<sup>[1]</sup> | [`_.every`](https://lodash.com/docs#every) | `[true, 1, null, 'yes'].every() == false` |
| `findIndex` | [`_.findIndex`](https://lodash.com/docs#findIndex) | `[3, 2, 1].findIndex{ it == 3 } == 0` |
| `findLastIndex` | [`_.findLastIndex`](https://lodash.com/docs#findLastIndex) | `[3, 2, 1].findLastIndex{ it == 1 } == 2` |
| [`first`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#first%28%29)<sup>[1]</sup> | [`_.first`](https://lodash.com/docs#first) | `[1, 2, 3].first() == 1` |
| [`flatten`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#flatten%28%29)<sup>[1, 3]</sup> | [`_.flatten`](https://lodash.com/docs#flatten) | `[1, [2, 3, [4]]].flatten() == [1, 2, 3, 4]` |
| `flattenDeep` | [`_.flattenDeep`](https://lodash.com/docs#flattenDeep) | `[1, [2, 3, [4]]].flattenDeep() == [1, 2, 3, 4]` |
| [`head`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#head%28%29)<sup>[1]</sup> | [`_.head`](https://lodash.com/docs#head) | `[1, 2, 3].head() == 1` |
| `includes` | [`_.includes`](https://lodash.com/docs#includes) | `[1, 2, 3].includes(1, 2) == false` |
| `indexOf` | [`_.indexOf`](https://lodash.com/docs#indexOf) | `[1, 2, 1, 2].indexOf(2) == 1` |
| `initial` | [`_.initial`](https://lodash.com/docs#initial) | `[1, 2, 3].initial() == [1, 2]` |
| `intersection` | [`_.intersection`](https://lodash.com/docs#intersection) | `[1, 2].intersection([4, 2], [2, 1]) == [2]` |
| [`last`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#last%28%29)<sup>[1]</sup> | [`_.last`](https://lodash.com/docs#last) | `[1, 2, 3].last() == 3` |
| `lastIndexOf` | [`_.lastIndexOf`](https://lodash.com/docs#lastIndexOf) | `[1, 2, 1, 2].lastIndexOf(3) == 1` |
| `object` | [`_.object`](https://lodash.com/docs#object) | `[['fred', 30], ['barney', 40]].object() == [ 'fred': 30, 'barney': 40 ]` |
| `map` | [`_.map`](https://lodash.com/docs#map) | `[1, 2, 3].map{ it * 3 } == [3, 6, 9]` |
| `memoize` | [`_.memoize`](https://lodash.com/docs#memoize) | `def fn = identity.memoize(constant('a')); fn(42) == fn(21)` |
| `negate` | [`_.negate`](https://lodash.com/docs#negate) | `[1, 2, 3].map({ n -> n % 2 == 0 }.negate()) == [true, false, true]` |
| `object` | [`_.object`](https://lodash.com/docs#object) | `[['fred', 30], ['barney', 40]].object() == ['fred': 30, 'barney': 40]` |
| `once` | [`_.once`](https://lodash.com/docs#once) | `(0..9).inject('', { p, q -> p += q }.once()) == '0'` |
| `pluck` | [`_.pluck`](https://lodash.com/docs#pluck) | `[[a: 1], [a: 2]].pluck('a') == [1, 2]` |
| `property` | [`_.property`](https://lodash.com/docs#property)  | `property('a.b')([a: [b: 'c']]) == 'c'` |
| `propertyOf` | [`_.propertyOf`](https://lodash.com/docs#propertyOf)  | `propertyOf([a: [b: 'c']])('a.b') == 'c'` |
| `pull` | [`_.pull`](https://lodash.com/docs#pull) | `[1, 2, 3, 1, 2, 3].pull(2, 3) == [1, 1]` |
| `pullAt` | [`_.pullAt`](https://lodash.com/docs#pullAt) | `[5, 10, 15, 20].pullAt(1, 3) == [5, 15]` |
| `removeElements`<sup>[5]</sup> | [`_.remove`](https://lodash.com/docs#remove) | `[1, 2, 3, 4].removeElements{ it % 2 } == [1, 3]` |
| `rest` | [`_.rest`](https://lodash.com/docs#rest) | `[1, 2, 3].rest() == [2, 3]` |
| `slice` | [`_.slice`](https://lodash.com/docs#slice) | `[1, 2, 3, 4, 5].slice(2, 4) == [3, 4]` |
| `some` | [`_.some`](https://lodash.com/docs#some) | `[true, 1, null, 'yes'].any() == true` |
| `sortedIndex` | [`_.sortedIndex`](https://lodash.com/docs#sortedIndex) | `[['x': 30], ['x': 50]].sortedIndex(['x': 40], 'x') == 1` |
| `sortedLastIndex` | [`_.sortedLastIndex`](https://lodash.com/docs#sortedLastIndex) | `[4, 4, 5, 5].sortedLastIndex(5) == 4` |
| [`tail`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#tail%28%29)<sup>[1]</sup> | [`_.tail`](https://lodash.com/docs#tail) | `[1, 2, 3].tail() == [2, 3]` |
| [`take`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#take%28int%29)<sup>[1]</sup> | [`_.take`](https://lodash.com/docs#take) | `[1, 2, 3].take(2) == [1, 2]` |
| [`takeRight`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#takeRight%28int%29)<sup>[1]</sup> | [`_.takeRight`](https://lodash.com/docs#takeRight) | `[1, 2, 3].takeRight(2) == [2, 3]` |
| `takeRightWhile`<sup>[2]</sup> | [`_.takeRightWhile`](https://lodash.com/docs#takeRightWhile) | `[1, 2, 3].takeRightWhile{ it > 1 } == [2, 3]` |
| [`takeWhile`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#takeWhile%28groovy.lang.Closure%29)<sup>[1, 2]</sup> | [`_.takeWhile`](https://lodash.com/docs#takeWhile) | `[1, 2, 3].takeWhile{ it < 3 } == [1, 2]` |
| `tap` | [`_.tap`](https://lodash.com/docs#tap) | `[1, 2, 3].map({ n -> n % 2 == 0 }.tap(identity)) == [false, true, false]` |
| `thru` | [`_.thru`](https://lodash.com/docs#thru) | `[1, 2, 3].map({ n -> n % 2 == 0 }.thru(constant(42))) == [42, 42, 42]` |
| `union` | [`_.union`](https://lodash.com/docs#union) | `[1, 2].union([4, 2], [2, 1]) == [1, 2, 4]` |
| `times` | [`_.times`](https://lodash.com/docs#times) | `identity.times(5, 42) == [42, 42, 42, 42, 42]` |
| `uniq` | [`_.uniq`](https://lodash.com/docs#uniq) | `[1, 2.5, 1.5, 2].uniq{ Math.floor(it) } == [1, 2.5]` |
| [`unique`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#unique%28%29)<sup>[1,2]</sup> | [`_.unique`](https://lodash.com/docs#unique) | `[2, 1, 2].unique() == [2, 1]` |
| `unzip` | [`_.unzip`](https://lodash.com/docs#unzip) | `[['fred', 30, true], ['barney', 40, false]].unzip() == [['fred', 'barney'], [30, 40], [true, false]]` |
| `unzipWith` | [`_.unzipWith`](https://lodash.com/docs#unzipWith) | `[[2, 10, 200], [1, 20, 100]].unzipWith({ it.min() }) == [1, 10, 100]` |
| `without` | [`_.without`](https://lodash.com/docs#without) | `[1, 2, 3, 1, 2, 3].without(2, 3) == [1, 1]` |
| `xor` | [`_.xor`](https://lodash.com/docs#xor) | `[1, 2].xor([4, 2]) == [1, 4]` |
| `zip` | [`_.zip`](https://lodash.com/docs#zip) | `[['fred', 30, true], ['barney', 40, false]].zip() == ['fred', 'barney'].zip([30, 40], [true, false]) == [['fred', 30, true], ['barney', 40, false]]` |
| `zipObject` | [`_.zipObject`](https://lodash.com/docs#zipObject) | `[['fred', 30], ['barney', 40]].zipObject() == ['fred': 30, 'barney': 40]` |
| `zipWith` | [`_.zipWith`](https://lodash.com/docs#zipWith) | `[1, 2].zipWith([10, 20], [100, 200], { it.max() }) == [100, 200]` |

> <sup>[1]</sup> Already implemented in Groovy
>
> <sup>[2]</sup> Semantics and syntax expanded to match lodash
>
> <sup>[3]</sup> Only 'deep' semantics implemented
>
> <sup>[5]</sup> Renamed from lodash because of collision with existing Groovy method
>
> <sup>[6]</sup> Syntax and semantics adapted to Groovy

## <a name='todo'>Open Issues

* application to `Collection`, `Iterable`, `Set`, `SortedSet` etc
* lazy evaluation
