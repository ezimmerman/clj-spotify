(ns components.test-console-output
  (:require [expectations :refer :all]
            [org.httpkit.client :as http]
            [clj-spotify.components.console-output :as out]
            [clojure.core.async :as async]))

(def request-ch (async/chan))
(def component-state (atom :running))

(expect clojure.core.async.impl.channels.ManyToManyChannel
        (do (out/output component-state request-ch)
            (async/go (async/>! request-ch {:message "the Message"}))))