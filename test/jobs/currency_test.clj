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
  (->> (concat f/alpha-vantage f/quandl)
       (execute! *cxn*))

  (testing "currency integration test"
    (let [actual  (->> "select * from dw.currency_fact"
                       (jdbc/query *cxn*)
                       flatten)]
      (is (= f/result
             (->> actual
                  (map #(dissoc %
                                :dw_created_at)))))
      (is (every? true?
                  (->> actual
                       (map #(contains? %
                                        :dw_created_at)))))
      (is (not (empty? actual))))))
