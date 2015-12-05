# A Groovy Port of [lodash](https://lodash.com/)

This is a spare-time project, and my objective is to port all of the [lodash v3.x](https://lodash.com/) functions and methods to Groovy that are not already part of Groovy itself, like `each`, for example.

I will attempt to combine idiomatic Groovy syntax with lodash semantics. So, for example, `_.chunk([1, 2, 3], 2)` in lodash becomes `[1, 2, 3].chunk(2)` in Groovy and the result is `[[1, 2], [3]]` in both.

Each ported function and method will be backed by a thorough [Spock]() unit test to validate the equivalence. I will make no attempt to replicate the lodash documentation in Groovydoc format, although I plan to host a very basic Groovydoc site.

I will die a happy man if I can make my code as performant as lodash! In the meantime, my only objective is to make it correct. A later release will focus on optimizations.

## How to install

## How to Build from Source and Run Unit Tests

## Groovy List Methods

| Groovy | lodash | Expect |
| --- | --- | --- |
| `chunk` | [`_.chunk`](https://lodash.com/docs#chunk) | `[1, 2, 3].chunk(2) == [[1, 2], [3]]` |
| `compact` | [`_.compact`](https://lodash.com/docs#compact) | `[0, 1, false, 2, '', 3].compact() == [1, 2, 3]` |
| `difference` | [`_.difference`](https://lodash.com/docs#difference) | `[1, 2, 3].difference([4, 2]) == [1, 3]` |
| [`drop`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#drop%28int%29)<sup>[1]</sup> | [`_.drop`](https://lodash.com/docs#drop) | `[1, 2, 3].drop(2) == [3]` |
| [`dropRight`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#dropRight%28int%29)<sup>[1]</sup> | [`_.dropRight`](https://lodash.com/docs#dropRight) | `[1, 2, 3].dropRight(2) == [1]` |
| `dropRightWhile`<sup>[2]</sup> | [`_.dropRightWhile`](https://lodash.com/docs#dropRightWhile) | `[1, 3, 2].dropRightWhile{ it < 3 } == [1, 3]` |
| [`dropWhile`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/List.html#dropWhile%28groovy.lang.Closure%29)<sup>[1, 2]</sup> | [`_.dropWhile`](https://lodash.com/docs#dropWhile) | `[1, 3, 2].dropWhile{ it < 3 } == [3, 2]` |
| `pluck` | [`_.pluck`](https://lodash.com/docs#pluck) | `[[a: 1], [a: 2]].pluck('a') == [1, 2]` |

> <sup>[1]</sup> Already implemented in Groovy
>
> <sup>[2]</sup> Semantics and syntax expanded to match lodash

## Groovy Map Methods

| Groovy | lodash | Expect |
| --- | --- | --- |
| `property` |  | `[a: [b: 'c']].property('a.b') == 'c'` |
