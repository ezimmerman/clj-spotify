(ns components.test-feed
  (:use org.httpkit.fake)
  (:require [clojure.test :refer :all]
            [org.httpkit.client :as http]
            [clj-spotify.components.feed :as feed]
            [clojure.core.async :as async]))

(def ch (async/chan 1))

(deftest  test-a-fake-response
        (with-fake-http ["http://fake" "a fake response"]
                        (is (= "a fake response" (:body (feed/get-messages "http://fake" {:timeout 200}))))))

(deftest test-feed-ch
        (with-fake-http ["http://fake" "a fake response"]
                        (feed/process-messages "http://fake" {:timeout 200} ch)
                        (is (= "a fake response" (:body (async/<!! ch))))))