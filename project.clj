(defproject clj-spotify "0.1.0-SNAPSHOT"
  :description "Simple spotify search and output to console."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [http-kit "2.1.18"]
                 [com.stuartsierra/component "0.3.1"]
                 [org.clojure/core.async "0.2.391"]
                 [environ "1.1.0"]
                 [http-kit.fake "0.2.1"]
                 [cheshire "5.6.3"]
                 [levand/immuconf "0.1.0"]]
  :plugins [[lein-expectations "0.0.7"]
            [environ "1.1.0"]]
  :main ^:skip-aot clj-spotify.main
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
