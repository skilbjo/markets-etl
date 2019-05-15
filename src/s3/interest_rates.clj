(ns s3.interest-rates
  (:require [clj-time.coerce :as coerce]
            [clojure.string :as string]
            [clojure.tools.cli :as cli]
            [environ.core :refer [env]]
            [jobs.interest-rates :refer :all :rename {-main _
                                                      execute! __}]
            [markets-etl.api :as api]
            [markets-etl.error :as error]
            [markets-etl.s3 :as s3]
            [markets-etl.util :as util])
  (:gen-class))

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
                         (map #(api/get-data % query-params*))
                         flatten)]

    (execute! (-> query-params* :start_date) data)))
