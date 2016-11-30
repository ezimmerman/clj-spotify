(ns components.test-sort
  (:use org.httpkit.fake)
  (:require [clojure.test :refer :all]
            [clj-spotify.components.sort :as sort]
            [clojure.core.async :as async]))


(def request-ch (async/chan))
(def response-ch (async/chan))
(def component-state (atom :running))
(def msg ["Prince Royce" "Prince" "test artist" "new artist"])

(deftest test-sort
         (do (sort/process-msg component-state request-ch response-ch)
             (async/>!! request-ch msg)
             (is (= ["Prince" "Prince Royce" "new artist" "test artist"] (async/<!! response-ch)))))