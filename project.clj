(defproject rapipago_simple_map "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/core.async "0.2.374"]
                 [org.clojure/clojurescript "1.7.228"]]

  :plugins [[lein-cljsbuild "1.1.2"]]

  :source-paths ["src"]

  :cljsbuild {
    :builds [{:id "rapipago_simple_map"
              :source-paths ["src"]
              :compiler {
                :output-to "rapipago_simple_map.js"
                :output-dir "out"
                :optimizations :simple}}]})
