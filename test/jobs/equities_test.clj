(ns jobs.equities-test
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [criterium.core :as criterium]
            [clojure.test :refer :all]
            [environ.core :refer [env]]
            [jobs.equities :refer :all :rename {-main _}]
            [fixtures.equities :as f]
            [fixtures.fixtures :refer [*cxn*] :as fix]))

(use-fixtures :each (fix/with-database))

#_(deftest integration-test
  #_(criterium/bench (->> (concat f/morningstar f/quandl)
                        (execute! *cxn*)))
  (->> (concat f/morningstar f/quandl)
       (execute! *cxn*))

  (testing "Quandl & Morningstar API equities integration test"
    (let [actual  (->> "select * from dw.equities"
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

(deftest integration-test'
  #_(criterium/bench (->> (concat f/morningstar f/quandl)
                        (execute!' *cxn*)))
  (->> (concat f/morningstar f/quandl)
       (execute!' *cxn*))

  (testing "Quandl & Morningstar API equities integration test"
    (let [actual  (->> "select * from dw.equities"
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
