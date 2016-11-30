(ns clj-spotify.components.feed
  (:require [org.httpkit.client :as http]
            [com.stuartsierra.component :as component]
            [clojure.core.async :as async]))

(defn handle-responses [status response-chan]
  )

(defn get-messages[uri options]
  @(http/get uri options))

(defn process-messages [uri options response-chan]
  (async/go (async/>! response-chan
              (get-messages uri options))))

(defrecord Feed [config status response-chan]
  component/Lifecycle
  (start [component]
    (reset! (:status component) :running)
    (process-messages (:uri config) (:options config) response-chan)
    (handle-responses status response-chan)
    component)
  (stop [component]
    (reset! (:status component) :stopped)
    component))

(defn new-feed [config response-chan]
  (->Feed config (atom :init) response-chan))


