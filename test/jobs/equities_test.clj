(ns jobs.equities-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [jobs.equities :as equities]
            [jobs.fixture :as fixture])
  (:gen-class))

(deftest integration-test
  (jdbc/with-db-connection [cxn (env :jdbc-db-uri)]
    (let [schema            (->> "create schema dw;"
                                 (jdbc/execute! cxn))
          setup              (->> "test/setup.sql"
                                  io/resource
                                  slurp
                                  (jdbc/execute! cxn))
          insert-source-data (->> "test/equities.sql"
                                  io/resource
                                  slurp
                                  (jdbc/execute! cxn))
          ;_                 ('go-run-etl)
          ;expected-result    (->> "select * from dw.equities limit 5"
                               ;(jdbc/query cxn)
                               ;flatten)
          teardown          (->> "drop schema dw cascade;"
                                 (jdbc/execute! cxn))
          _ (println setup)
          ]

        (testing "some stuff"
          (is (= 1
                 1))))))
