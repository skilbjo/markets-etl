(ns jobs.equities-test
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [criterium.core :as criterium]
            [clojure.test :refer :all]
            [environ.core :refer [env]]
            [jobs.equities :refer :all :rename {-main _}]
            [benchmark.equities :refer :all :rename {-main __}]
            [fixtures.equities :as f]
            [fixtures.fixtures :refer [*cxn*] :as fix]
            [markets-etl.util :as util]))

(use-fixtures :each (fix/with-database))

(deftest integration-test
  (->> (concat f/tiingo) ;; f/morningstar f/quandl)
       (execute! *cxn*))

  (testing "Quandl & Morningstar API equities integration test"
    (let [actual  (->> "select * from dw.equities_fact"
                       (jdbc/query *cxn*)
                       flatten)]
      ;(is (= f/result
      (is (= f/tiingo-result
             (->> actual
                  (map #(dissoc %
                                :dw_created_at)))))
      (is (every? true?
                  (->> actual
                       (map #(contains? %
                                        :dw_created_at)))))
      (is (not (empty? actual))))))

#_(deftest integration-test'
    (->> (concat f/tiingo) ;; f/morningstar f/quandl)
         (execute!' *cxn*))

    (testing "Equities integration test, using reducers"
      (let [actual  (->> "select * from dw.equities_fact"
                         (jdbc/query *cxn*)
                         flatten)]
        (is (= f/result'
               (->> actual
                    (map #(dissoc %
                                  :dw_created_at)))))
        (is (every? true?
                    (->> actual
                         (map #(contains? %
                                          :dw_created_at)))))
        (is (not (empty? actual))))))
