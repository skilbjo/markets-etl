(ns jobs.real-estate-test
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [clojure.test :refer :all]
            [environ.core :refer [env]]
            [jobs.real-estate :refer :all :rename {-main _}]
            [fixtures.real-estate :as f]
            [fixtures.fixtures :refer [*cxn*] :as fix]))

(use-fixtures :each (fix/with-database))

(deftest integration-test
  (->> f/source
       (execute! *cxn*))

  (testing "real_estate integration test"
    (is (= f/result
           (->> "select * from dw.real_estate"
                (jdbc/query *cxn*)
                flatten)))))
