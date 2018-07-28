(ns jobs.real-estate-test
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [clojure.test :refer :all]
            [environ.core :refer [env]]
            [markets-etl.util :as util]
            [jobs.real-estate :refer :all :rename {-main _}]
            [fixtures.real-estate :as f]
            [fixtures.fixtures :refer [*cxn*] :as fix]))

(use-fixtures :each (fix/with-database))

(defn contains-every-key? [m & ks]
  (every? #(contains? m %) ks))

(deftest integration-test
  (->> f/source
       (execute! *cxn*))

  (testing "real_estate integration test"
    (let [actual  (->> "select * from dw.real_estate_fact"
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
