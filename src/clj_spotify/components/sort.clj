(ns clj-spotify.components.sort
  (:require [com.stuartsierra.component :as component]
            [clojure.core.async :as async]))



(defn sort-map [msg]
  (sort msg))

(defn process-msg [status msg-chan response-chan]
  (async/go (while (= @status :running)
              (let [msg (async/<! msg-chan)]
                (async/>! response-chan (sort-map msg))))
            (async/close! msg-chan)))

(defrecord Sort [status msg-chan response-chan]
  component/Lifecycle
  (start [component]
    (reset! (:status component) :running)
    (process-msg status msg-chan response-chan))
  (stop [component]
    (reset! (:status component) :stopped)
    component))

(defn new-sort [msg-chan response-chan]
  (->Sort (atom :init) msg-chan response-chan))
