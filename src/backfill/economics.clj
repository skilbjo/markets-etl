(ns backfill.economics
  (:require [clojure.java.jdbc :as jdbc]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [clojure.tools.cli :as cli]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]]
            [jobs.economics :refer :all :rename {-main _
                                                 query-params __
                                                 cli-options ___}]
            [markets-etl.api :as api]
            [markets-etl.error :as error]
            [markets-etl.util :as util])
  (:gen-class))

(def cli-options
  [["-d" "--date yyyy-mm-dd" "Date (month) to start processing"
    :parse-fn #(f/parse %)
    :default util/five-years-ago]
   ["-h" "--help"]])

(defn -main [& args]
  (error/set-default-error-handler)
  (let [{:keys [options summary errors]} (cli/parse-opts args cli-options)]
    (jdbc/with-db-connection [cxn (-> :jdbc-db-uri env)]
      (let [month        (:date options)
            query-params {:limit      2600
                          :start_date (-> month t/first-day-of-the-month)
                          :end_date   month}
            data         (->> (concat quandl)
                              (map #(api/get-data % query-params))
                              flatten)]

        (execute! cxn data)))))
