(ns clj-spotify.components.console-output
  (:require [com.stuartsierra.component :as component]
            [clojure.core.async :as async]))

(defn output [status request-ch]
  (async/go (while (= @status :running)
             (println (interpose ", " (async/<! request-ch))))
            (async/close! request-ch)))

(defrecord console-output [status request-ch]
  component/Lifecycle
  (start [component]
    (reset! (:status component) :running)
    (output status request-ch)
    component)
  (stop [component]
    (reset! (:status component) :stopped)
    component))

(defn new-console-output [request-ch]
  (->console-output (atom :init) request-ch))