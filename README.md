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

| Groovy | lodash | Expect |
| --- | --- | --- |
| `chunk` | [`_.chunk`](https://lodash.com/docs#chunk) | `[1, 2, 3].chunk(2) == [[1, 2], [3]]` |
| `compact` | [`_.compact`](https://lodash.com/docs#compact) | `[0, 1, false, 2, '', 3].compact() == [1, 2, 3]` |
| `difference` | [`_.difference`](https://lodash.com/docs#difference) | `[1, 2, 3].difference([4, 2]) == [1, 3]` |
| [`drop`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#drop%28int%29)<sup>[1]</sup> | [`_.drop`](https://lodash.com/docs#drop) | `[1, 2, 3].drop(2) == [3]` |
| [`dropRight`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#dropRight%28int%29)<sup>[1]</sup> | [`_.dropRight`](https://lodash.com/docs#dropRight) | `[1, 2, 3].dropRight(2) == [1]` |
| `dropRightWhile`<sup>[2]</sup> | [`_.dropRightWhile`](https://lodash.com/docs#dropRightWhile) | `[1, 3, 2].dropRightWhile{ it < 3 } == [1, 3]` |
| [`dropWhile`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#dropWhile%28groovy.lang.Closure%29)<sup>[1, 2]</sup> | [`_.dropWhile`](https://lodash.com/docs#dropWhile) | `[1, 3, 2].dropWhile{ it < 3 } == [3, 2]` |
| `findIndex` | [`_.findIndex`](https://lodash.com/docs#findIndex) | `[3, 2, 1].findIndex{ it == 3 } == 0` |
| `findLastIndex` | [`_.findLastIndex`](https://lodash.com/docs#findLastIndex) | `[3, 2, 1].findLastIndex{ it == 1 } == 2` |
| [`first`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#first%28%29)<sup>[1]</sup> | [`_.first`](https://lodash.com/docs#first) | `[1, 2, 3].first() == 1` |
| [`flatten`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#flatten%28%29)<sup>[1, 3]</sup> | [`_.flatten`](https://lodash.com/docs#flatten) | `[1, [2, 3, [4]]].flatten() == [1, 2, 3, 4]` |
| `flattenDeep` | [`_.flattenDeep`](https://lodash.com/docs#flattenDeep) | `[1, [2, 3, [4]]].flattenDeep() == [1, 2, 3, 4]` |
| [`head`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#head%28%29)<sup>[1]</sup> | [`_.head`](https://lodash.com/docs#head) | `[1, 2, 3].head() == 1` |
| `indexOf` | [`_.indexOf`](https://lodash.com/docs#indexOf) | `[1, 2, 1, 2].indexOf(2) == 1` |
| `initial` | [`_.initial`](https://lodash.com/docs#initial) | `[1, 2, 3].initial() == [1, 2]` |
| `intersection` | [`_.intersection`](https://lodash.com/docs#intersection) | `[1, 2].intersection([4, 2], [2, 1]) == [2]` |
| [`last`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#last%28%29)<sup>[1]</sup> | [`_.last`](https://lodash.com/docs#last) | `[1, 2, 3].last() == 3` |
| `lastIndexOf` | [`_.lastIndexOf`](https://lodash.com/docs#lastIndexOf) | `[1, 2, 1, 2].lastIndexOf(3) == 1` |
| `object` | [`_.object`](https://lodash.com/docs#object) | `[['fred', 30], ['barney', 40]].object() == [ 'fred': 30, 'barney': 40 ]` |
| `pluck` | [`_.pluck`](https://lodash.com/docs#pluck) | `[[a: 1], [a: 2]].pluck('a') == [1, 2]` |
| `property`<sup>[4]</sup> |  | `[a: [b: 'c']].property('a.b') == 'c'` |
| `pull` | [`_.pull`](https://lodash.com/docs#pull) | `[1, 2, 3, 1, 2, 3].pull(2, 3) == [1, 1]` |
| `pullAt` | [`_.pullAt`](https://lodash.com/docs#pullAt) | `[5, 10, 15, 20].pullAt(1, 3) == [5, 15]` |
| `removeElements`<sup>[5]</sup> | [`_.remove`](https://lodash.com/docs#remove) | `[1, 2, 3, 4].removeElements{ it % 2 } == [1, 3]` |
| `rest` | [`_.rest`](https://lodash.com/docs#rest) | `[1, 2, 3].rest() == [2, 3]` |
| [`tail`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#tail%28%29)<sup>[1]</sup> | [`_.tail`](https://lodash.com/docs#tail) | `[1, 2, 3].tail() == [2, 3]` |
| `zipObject` | [`_.zipObject`](https://lodash.com/docs#zipObject) | `[['fred', 30], ['barney', 40]].zipObject() == [ 'fred': 30, 'barney': 40 ]` |

> <sup>[1]</sup> Already implemented in Groovy
>
> <sup>[2]</sup> Semantics and syntax expanded to match lodash
>
> <sup>[3]</sup> Only 'deep' semantics implemented
>
> <sup>[4]</sup> New in this Groovy library
>
> <sup>[5]</sup> Renamed from lodash because of collision with existing Groovy method

## <a name='todo'>Open Issues

* application to `Collection`, `Iterable`, `Set`, `SortedSet` etc
* lazy evaluation
