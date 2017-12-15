(ns jobs.currency-test
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [clojure.test :refer :all]
            [environ.core :refer [env]]
            [jobs.currency :refer :all :rename {-main _}]
            [fixtures.currency :as f]
            [fixtures.fixtures :refer [*cxn*] :as fix]))

(use-fixtures :each (fix/with-database))

(deftest integration-test
  (->> f/source
       (execute! *cxn*))

  (testing "currency integration test"
    (is (= f/result
           (->> "select * from dw.currency"
                (jdbc/query *cxn*)
                flatten)))))
