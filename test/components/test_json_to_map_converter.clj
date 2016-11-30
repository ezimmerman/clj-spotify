(ns components.test-json-to-map-converter
  (:require [clojure.test :refer :all]
            [clj-spotify.components.json-to-map-converter :as converter]
            [clojure.core.async :as async]))


(def request-ch (async/chan))
(def response-ch (async/chan))
(def component-state (atom :running))
(def msg {:body "{\"foo\":\"bar\"}"})

(deftest test-convert
  (do (converter/convert component-state request-ch response-ch)
      (async/>!! request-ch msg)
      (is (= {:foo "bar"} (async/<!! response-ch)))))