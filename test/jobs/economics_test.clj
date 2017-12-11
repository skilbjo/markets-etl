(ns jobs.economics-test
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [clojure.test :refer :all]
            [environ.core :refer [env]]
            [jobs.economics :refer :all :rename {-main _}]
            [fixtures.economics :as f]
            [fixtures.fixtures :refer [*cxn*] :as fix]))

(use-fixtures :each (fix/with-database))

(deftest integration-test
  (->> f/source
       (execute! *cxn*))

  (testing "some stuff"
    (is (= f/result
           (->> "select * from dw.economics"
                (jdbc/query *cxn*)
                flatten)))))
