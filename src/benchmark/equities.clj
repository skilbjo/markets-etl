(ns benchmark.equities
  (:require [clj-time.core :as t]
            [clj-time.format :as clj-f]
            [clojure.core.reducers :as r]
            [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [clojure.java.shell :as shell]
            [clojure.tools.cli :as cli]
            [clojure.tools.logging :as log]
            #_[criterium.core :as criterium] ; only used for testing locally
            [environ.core :refer [env]]
            [fixtures.equities :as f]
            [jobs.equities :refer :all :rename {-main _
                                                query-params __}]
            [markets-etl.api :as api]
            [markets-etl.util :as util])
  (:gen-class))

(defn execute!' [cxn data]
  (jdbc/with-db-transaction [txn cxn]
    (->> data
         (r/map prepare-row)
         (into '())
         flatten
         (map #(update-or-insert! txn %))
         doall)))

(def cli-options
  [["-d" "--date yyyy-mm-dd" "Date (month) to start processing"
    :parse-fn #(clj-f/parse %)
    :default util/last-week]
   ["-h" "--help"]])

(defn -main [& args]
  (let [{:keys [options summary errors]} (cli/parse-opts args cli-options)]
    (jdbc/with-db-connection [*cxn* (-> :test-jdbc-db-uri env)]
      (->> "test/ddl.sql"
           io/resource
           slurp
           (jdbc/execute! *cxn*)) ; for some reason, during benchmark, throws
      (->> "alter table dw.equities_fact drop constraint equities_pk"      ; here
           (jdbc/execute! *cxn*))
      (let [month        (:date options)
            query-params {:limit      2600
                          :start_date (-> month #_t/first-day-of-the-month)
                          :end_date   month}
            data         (->> (concat morningstar quandl)
                              #_(map #(api/get-data % query-params))
                              flatten)
            data'        (->> (concat f/morningstar f/quandl)
                              flatten)]

        (log/info "Benchmarking the traditional pipeline...")
        (-> (->> data'
                 (execute! *cxn*))
            #_(criterium/bench)
            #_criterium/with-progress-reporting)
        (log/info "Now on to reducers...")
        (-> (->> data'
                 (execute!' *cxn*))
            #_(criterium/bench)
            #_criterium/with-progress-reporting)

        (->> "drop schema dw cascade;"
             (jdbc/execute! *cxn*))))))
