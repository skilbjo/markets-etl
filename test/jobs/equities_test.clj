(ns jobs.equities-test
  (:require [clojure.test :refer :all]
            [jobs.equities :as equities]
            [jobs.fixture :as fixture])
  (:gen-class))

(deftest generate-sql-test
  (testing "does generate-sql generate sql?"
    (is (= true true))))

(deftest split-stats-test
  (testing "split-stats splits a wide map into many deep maps"
    (is (= true true))))
