(ns jobs.interest-rates-test
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [clojure.test :refer :all]
            [environ.core :refer [env]]
            [jobs.interest-rates :refer :all :rename {-main _}]
            [fixtures.interest-rates :as f]
            [fixtures.fixtures :refer [*cxn*] :as fix]))

(use-fixtures :each (fix/with-database))

(deftest integration-test
  (->> f/source
       (execute! *cxn*))

  (testing "interest_rates integration test"
    (is (= f/result
           (->> "select * from dw.interest_rates"
                (jdbc/query *cxn*)
                flatten)))))
