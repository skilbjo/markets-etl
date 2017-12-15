(ns jobs.equities-test
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [clojure.test :refer :all]
            [environ.core :refer [env]]
            [jobs.equities :refer :all :rename {-main _}]
            [fixtures.equities :as f]
            [fixtures.fixtures :refer [*cxn*] :as fix]))

(use-fixtures :each (fix/with-database))

(deftest integration-test
  (->> f/source
       (execute! *cxn*))

  (testing "equities integration test"
    (is (= f/result
           (->> "select * from dw.equities"
                (jdbc/query *cxn*)
                flatten)))))
