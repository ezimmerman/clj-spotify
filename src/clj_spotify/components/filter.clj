(ns clj-spotify.components.filter
  (:require [com.stuartsierra.component :as component]
            [clojure.core.async :as async]))

(defn get-items [msg]
  (get-in msg [:artists :items])
  )

(defn get-names [items-v]
  (reduce #(conj % (:name %2)) [] items-v))


(defn process-msg [status msg-request-chan msg-response-chan]
  (async/go (while (= @status :running)
              (let [msg (async/<! msg-request-chan)
                    filtered (get-names (get-items msg))]
                (async/>! msg-response-chan filtered)))
            (async/close! msg-request-chan))
  )

(defrecord Filter [status msg-request-chan msg-response-chan]
  component/Lifecycle
  (start [component]
         (reset! (:status component) :running)
         (process-msg status msg-request-chan msg-response-chan))
  (stop [component]
        (reset! (:status component) :stopped)
        component))

(defn new-filter [msg-request-chan msg-response-chan]
  (->Filter (atom :init) msg-request-chan msg-response-chan))

