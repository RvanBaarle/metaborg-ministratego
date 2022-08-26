# metaborg-ministratego

This repository contains an implementation of core subset of [Stratego](https://www.spoofax.dev/references/stratego/) transformation language. The implementation is written using version 3 of the [Spoofax language workbench](https://spoofax.dev/spoofax-pie/develop/). This project was written as part as a case-study for [Dynamix: A Domain-Specific Language for Dynamic Semantics](http://resolver.tudelft.nl/uuid:8653ab24-a782-41f0-aefc-6b1c8d9a37d5).

You can import this repository in your Spoofax 3 eclipse instance by following the steps [here](https://www.spoofax.dev/spoofax-pie/develop/guide/eclipse_lwb/import/).

## Supported Constructs

The following constructs are supported by this version of miniStratego:

Terms:
- Integers
- Strings
- Lists

Strategies:
- fail
- id
- Match (`?<pattern>`)
- Build (`!<pattern>`)
- Seq (`s1; s2`)
- Guarded choice (`s1 < s2 + s3`)
- some, one, all
- let bindings (`let <decl*> in s end`)

These features should support complete enough to express all Stratego programs. You will likely need to desugar various things though. For example:

```
foo: a -> b
  where c := d
```

will need to be desugared to

```
foo(|) = ?tmp0; !d; ?c; !tmp0; ?a; !b
```

miniStratego is fully compliant with core Stratego, except for one crucial difference: variables are deterministically bound. This means that variables bound in an invocation to a strategy will not persist on the next invocation to that strategy, nor can they escape "outside" of the strategy. To illustrate:

```
where(s|) = ?x; s; !x

// in Stratego, this works fine and prints "foo"
test(|) = !"foo"; where(?y|); !y; debug

// in miniStratego, this is an error
// the binding of `y` cannot escape the `y`
test(|) = !"foo"; where(?y|); !y; debug 
//     Undefined variable 'y' ^^
```

miniStratego ships with various "prim"s: strategies implemented directly in Tim source. Their implementations can be found in `ministratego/src/dynamics/native.dx`. See `ministratego/test.mstr` for an example on how to declare and access them.

## Contents

This language project contains the following parts:
- `ministratego/src/syntax`: The SDF3 grammar definition for miniStratego, based on the [core Stratego syntax](https://github.com/metaborg/stratego/tree/master/stratego.lang/syntax/core) from the Stratego repository.
- `chocopy/src/statics`: A simple Statix specification for miniStratego, that additionally keeps track of whether a pattern will bind or match on a value.
- `chocopy/src/dynamics`: The Dynamix specification for miniStratego.

## License

The files in this repository are licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
You may use the files in this repository in compliance with the license.