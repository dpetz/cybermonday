(defproject cybermonday "0.1-fork"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 ; cybermonday dependencies (checkout) 
                 [clj-commons/clj-yaml "1.0.26"]
                 [com.vladsch.flexmark/flexmark "0.64.8"]
                 [com.vladsch.flexmark/flexmark-ext-tables "0.64.8"]
                 [com.vladsch.flexmark/flexmark-ext-footnotes "0.64.8"]
                 [com.vladsch.flexmark/flexmark-ext-gfm-strikethrough "0.64.8"]
                 [com.vladsch.flexmark/flexmark-ext-gfm-tasklist "0.64.8"]
                 [com.vladsch.flexmark/flexmark-test-util "0.64.8"] ; :exclusions [junit/junit]
                 [com.vladsch.flexmark/flexmark-ext-gitlab "0.64.8"]
                 [com.vladsch.flexmark/flexmark-ext-toc "0.64.8"]
                 [org.clj-commons/hickory "0.7.3"]
                 [cljs-bean/cljs-bean "1.9.0"]
                 [com.vladsch.flexmark/flexmark-ext-definition "0.64.8"] ; DEPTZ
                 ]
  :main ^:skip-aot cybermonday.core
  :target-path "target/%s"
  :profiles {:dev {}
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
