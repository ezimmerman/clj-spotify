(ns clj-spotify.components.json-to-map-converter
  (:require [cheshire.core :as cheshire]
            [clojure.core.async :as async]
            [com.stuartsierra.component :as component]))

(defn convert [status msg-request-chan msg-response-chan]
  (async/go (while (= @status :running)
              (let [msg (async/<! msg-request-chan)
                    converted (cheshire/parse-string (:body msg) true)]
                (async/>! msg-response-chan converted)))
            (async/close! msg-request-chan))
  )

(defrecord Json-map-converter [status msg-request-chan msg-response-chan]
  component/Lifecycle
  (start [component]
    (reset! (:status component) :running)
    (convert status msg-request-chan msg-response-chan))
  (stop [component]
    (reset! (:status component) :stopped)
    component))

(defn new-json-map-converter [request-ch response-ch]
  (->Json-map-converter (atom :init) request-ch response-ch))