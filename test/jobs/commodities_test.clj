(ns jobs.commodities-test
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [clojure.test :refer :all]
            [environ.core :refer [env]]
            [jobs.commodities :refer :all :rename {-main _}]
            [fixtures.commodities:as f]
            [fixtures.fixtures :refer [*cxn*] :as fix]))

(use-fixtures :each (fix/with-database))

(deftest integration-test
  (->> (concat f/crypto f/precious-metals f/gold f/oil)
       (execute! *cxn*))

  (testing "commoditiesintegration test"
    (let [actual  (->> "select * from dw.commoditiesfact"
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
