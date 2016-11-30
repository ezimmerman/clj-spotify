(ns components.test-filter
  (:require [clojure.test :refer :all]
            [clj-spotify.components.filter :as filter]
            [clojure.core.async :as async]))


(def request-ch (async/chan))
(def response-ch (async/chan))
(def component-state (atom :running))
(def msg {:artists {:items [{:name "Prince Royce", :type "artist"} {:name "Prince", :type "artist"}]}})

(deftest test-filter
  (do (filter/process-msg component-state request-ch response-ch)
      (async/>!! request-ch msg)
      (is (= ["Prince Royce" "Prince"] (async/<!! response-ch)))))