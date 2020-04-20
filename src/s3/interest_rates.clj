(ns s3.interest-rates
  (:require [clj-time.coerce :as coerce]
            [clojure.string :as string]
            [clojure.tools.cli :as cli]
            [jobs.interest-rates :refer :all :rename {-main _
                                                      execute! __
                                                      query-params ___
                                                      api-keys ____}]
            [markets-etl.api :as api]
            [markets-etl.error :as error]
            [markets-etl.s3 :as s3]
            [markets-etl.util :as util])
  (:gen-class))

(def api-keys   ;; env vars are encrypted on lambda
  (delay        ;; defs evaluated at compile time; delay until runtime
   {:quandl-api-key        (util/decrypt :quandl-api-key)}))

(def query-params
  {:limit      500
   :start_date util/yesterday
   :end_date   util/now})

(defn execute! [date data]
  (->> data
       (map prepare-row)
       flatten
       (remove nil?)                                     ; date partition should only
       (filter #(= (coerce/to-sql-date date) (:date %))) ; contain 1 day of data
       (s3/insert-to-athena (-> "jobs.interest_rates" str (string/split #"\.") second) date)
       doall))

(defn -main [& args]
  (error/set-default-error-handler)
  (let [{:keys [options summary errors]} (cli/parse-opts args cli-options)
        query-params*        (if args
                               {:limit      (:limit query-params)
                                :start_date (-> options
                                                :date
                                                util/joda-date->date-str)
                                :end_date   (-> options
                                                :date
                                                util/joda-date->date-str)}
                               query-params)
        data        (->> datasets
                         (map #(api/get-data % @api-keys query-params*))
                         flatten)]

    (execute! (-> query-params* :start_date) data)))
