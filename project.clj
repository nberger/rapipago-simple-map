(defproject rapipago_simple_map "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.async "0.1.303.0-886421-alpha"]
                 [org.clojure/clojurescript "0.0-2261"]]

  :plugins [[lein-cljsbuild "1.0.3"]]

  :source-paths ["src"]

  :cljsbuild { 
    :builds [{:id "rapipago_simple_map"
              :source-paths ["src"]
              :compiler {
                :output-to "rapipago_simple_map.js"
                :output-dir "out"
                :optimizations :none
                :source-map true}}]})
