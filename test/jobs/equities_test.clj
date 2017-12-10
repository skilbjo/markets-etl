(ns jobs.equities-test
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [clojure.test :refer :all]
            [environ.core :refer [env]]
            [jobs.equities :as equities]
            [markets-etl.fixtures :refer [*cxn*] :as fix]))

(use-fixtures :each (fix/with-database))

(deftest integration-test
  (let [schema            (->> "select now();"
                               (jdbc/execute! *cxn*))
          ;setup              (->> "test/setup.sql"
                                  ;io/resource
                                  ;slurp
                                  ;(jdbc/execute! *cxn*))
          ;insert-source-data (->> "test/equities.sql"
                                  ;io/resource
                                  ;slurp
                                  ;(jdbc/execute! *cxn*))
          ;;_                 ('go-run-etl)
          ;;expected-result    (->> "select * from dw.equities limit 5"
                               ;;(jdbc/query *cxn*)
                               ;;flatten)
          ;teardown          (->> "drop schema dw cascade;"
                                 ;(jdbc/execute! *cxn*))
          ;_ (println setup)
]

    (testing "some stuff"
      (is (= 1
             1)))))
