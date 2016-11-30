(ns clj-spotify.main
  (:require [immuconf.config :as ic]
            [com.stuartsierra.component :as component]
            [clojure.core.async :as async]
            [clj-spotify.components.filter :as filter]
            [clj-spotify.components.json-to-map-converter :as converter]
            [clj-spotify.components.sort :as sort]
            [clj-spotify.components.feed :as feed]
            [clj-spotify.components.console-output :as out])
  (:gen-class))


(defn system [{:keys (feed) :as config}]
  (let [feed-response-chan (async/chan 100)
        filter-response-chan (async/chan 100)
        converter-response-chan (async/chan 100)
        sort-response-chan (async/chan 100)]
    (component/system-map
      :feed (feed/new-feed feed feed-response-chan)
      :converter (converter/new-json-map-converter feed-response-chan converter-response-chan)
      :filter (filter/new-filter converter-response-chan filter-response-chan)
      :sort (sort/new-sort filter-response-chan sort-response-chan)
      :output (out/new-console-output sort-response-chan))))

(defn start-app []
  (let [conf (ic/load "resources/config.edn")]
    (component/start-system (system conf))))

(defn -main
  "App entry point"
  []
  (start-app ))