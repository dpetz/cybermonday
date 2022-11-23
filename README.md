# cybermonday

> What if markdown was _just data_

![GitHub Workflow Status](https://img.shields.io/github/workflow/status/kiranshila/cybermonday/Main)
[![codecov](https://codecov.io/gh/kiranshila/cybermonday/branch/master/graph/badge.svg?token=U0130A9B8Y)](https://codecov.io/gh/kiranshila/cybermonday)
[![Clojars Project](https://img.shields.io/clojars/v/com.kiranshila/cybermonday.svg)](https://clojars.org/com.kiranshila/cybermonday)
[![cljdoc badge](https://cljdoc.org/badge/com.kiranshila/cybermonday)](https://cljdoc.org/d/com.kiranshila/cybermonday/CURRENT)

Cybermonday provides a Clojure(Script) interface to working with markdown as a hiccup AST. Beyond CommonMark, GFM tables, tasklists and a few other features are added. A default implementation is provided to transform this markdown IR to HTML hiccup for rendering.

Try it out here on the [test app](https://kiranshila.github.io/cybermonday-test-app/)!

## Usage

Simply add this library to your Clojure(Script) project, grab some markdown as a string, and run

```clojure
(cybermonday.core/parse-md my-markdown-str)
```

to get an HTML hiccup representation of your markdown.

If you want the IR directly:

```clojure
(cybermonday.ir/md-to-ir my-markdown-str)
```

Extending the lowering step is easy, with an options map provided to set default attributes for the HTML tags and custom lowering fns.
For example, say we want to use a react component from Reagent to render math and make all paragraph tags use my custom class. This is as simple as

```clojure
(ns main.core
  (:require
   [cybermonday.core :as cm]
   ["@matejmazur/react-katex" :as TeX]))

(defn lower-inline-math [[_ _ math]]
  [:> TeX {:math math}])

(cm/parse-md markdown {:default-attrs {:p {:class ["my-class"]}}
                       :lower-fns {:markdown/inline-math lower-inline-math}}
```

## Motivation

I've been frustrated with the space of Markdown manipulation in Clojure. Most
libraries provide parsing to raw html, which is fine if you have a
straightforward way to include that in whatever you are targeting. If however,
you would want to manipulate the AST of the markdown directly, or convert it
into a format that frontend frameworks (like Reagent) can consume, you would
have to convert the HTML to clojure data (like Hiccup). There are a few html to
hiccup parsers, but as anyone who has tried to parse html will tell you, there
are edge cases that can break the whole thing. It also seems backwards to go
from markdown to html to hiccup to html when you consider the entire rendering
pipeline.

To overcome this, I wrote `cybermonday`! It was originally going to be a parser
for blackfriday markdown, but as I realized the markdown spec is insane, it made
more sense to wrap the excellent Flexmark (java) and Remark (js) markdown parsers. At the
most basic level, `cybermonday` provides a top level function `parse-md`
that gives you a nice, reagent-renderable representation of your source. This
includes all of the CommonMark spec as well as the best features of popular extensions such as tables,
strikethroughs, footnotes, math, and more! This
even supports inline html and html around markdown-formated text.

However, `cybermonday` also provides access to a hiccup representation of the
Flexmark (and remark) AST and the methods to provide the final pass transformation from
this intermediate representation to HTML. This allows the user to customize how the raw markdown AST
gets transformed into html, allowing for easy extension and customization.

I'm using this library on my blog at kiranshila.com - please let me know if you
run into any issues.

For more details, check out the docs!

## Major Caveats

The inline html parser is really rudimentary. Please be gentle. They pretty much must follow the `<tag foo="bar"> Content </tag>` syntax to be properly rendered.

Another breaking example is if the attributes of a tag contain `=` in the value of the attribute. I'm splitting the attributes up by the `=`, so any random `=` that doesn't separate the key and the value will break the parser.

## JVM Compat
Flexmark now has a hard dependency on Java 11 - for which is carried over here. If you need Java 8, pin the flexmark deps to 0.62.2

## License

Copyright © 2022 Kiran Shila

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
