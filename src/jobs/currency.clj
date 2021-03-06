(ns jobs.currency
  (:require [clj-time.coerce :as coerce]
            [clj-time.core :as time]
            [clj-time.format :as format]
            [clojure.data.json :as json]
            [clojure.java.jdbc :as jdbc]
            [clojure.set :as set]
            [clojure.string :as string]
            [clojure.tools.cli :as cli]
            [environ.core :refer [env]]
            [markets-etl.api :as api]
            [markets-etl.error :as error]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def api-keys   ;; env vars are encrypted on lambda
  (delay        ;; defs evaluated at compile time; delay until runtime
   {:quandl-api-key        (-> :quandl-api-key env)
    :alpha-vantage-api-key (-> :alpha-vantage-api-key env)}))

(def cli-options
  [["-d" "--date DATE" "Start date (month) (yyyy-mm-dd format) to start processing"
    :parse-fn #(format/parse %)
    :default  util/last-week]
   ["-h" "--help"]])

(def currencies
  ["EURUSD" "GBPUSD"])

(def quandl
  (list {:dataset "CURRFX"
         :ticker  currencies}))

(def alpha-vantage
  (list {:dataset "ALPHA-VANTAGE"
         :ticker  currencies}))

(def query-params
  {:limit      500
   :start_date util/last-week
   :end_date   util/now})

(defmulti prepare-row :dataset)

(defmethod prepare-row "ALPHA-VANTAGE" [{:keys [dataset
                                                ticker
                                                time_series_fx_daily]}]
  (->> time_series_fx_daily
       (map identity)
       (map #(assoc {}
                    :dataset     dataset
                    :ticker      ticker
                    :currency    (-> ticker (string/split #"USD") first)
                    :date        (-> % first name coerce/to-sql-date)
                    :rate        (-> % second :4._close util/string->decimal)
                    :high        (-> % second :2._high util/string->decimal)
                    :low         (-> % second :3._low util/string->decimal)))))

(defmethod prepare-row :default [{:keys [dataset
                                         ticker]
                                  {:keys [column_names
                                          data]} :dataset_data}]
  (when (seq data)
    (let [columns       (->> (-> column_names
                                 string/lower-case
                                 (string/replace #"\." "")
                                 (string/replace #"-" "_")
                                 (string/replace #"\(" "")
                                 (string/replace #"\)" "")
                                 json/read-str)
                             (map #(string/replace % #" " "_"))
                             (map keyword))]
      (->> data
           (map #(zipmap columns %))
           (map #(update % :date coerce/to-sql-date))
           (map #(update % :date coerce/to-sql-date))
           (map #(assoc %
                        :dataset dataset
                        :ticker ticker
                        :currency (subs ticker 0 3)))
           (map #(set/rename-keys % {:high_est :high
                                     :low_est  :low}))))))

(defn update-or-insert! [db {:keys [dataset
                                    ticker
                                    date] :as record}]
  (sql/update-or-insert! db
                         :dw.currency_fact
                         [(util/multi-line-string
                           "dataset = ? and "
                           "ticker  = ? and "
                           "date    = ?     ")
                          dataset
                          ticker
                          date]
                         record))

(defn execute! [cxn data]
  (jdbc/with-db-transaction [txn cxn]
    (->> data
         (map prepare-row)
         flatten
         (remove nil?)
         (map #(update-or-insert! txn %))
         doall)))

(defn -main [& args]
  (error/set-default-error-handler)
  (jdbc/with-db-connection [cxn (-> :jdbc-db-uri env)]
    (let [{:keys [options summary errors]} (cli/parse-opts args cli-options)
          query-params*        (if args
                                 {:limit      (:limit query-params)
                                  :start_date (-> options
                                                  :date
                                                  time/first-day-of-the-month
                                                  util/joda-date->date-str)
                                  :end_date   (-> options
                                                  :date
                                                  util/joda-date->date-str)}
                                 query-params)
          data        (->> (concat alpha-vantage quandl)
                           (map #(api/get-data % @api-keys query-params*))
                           flatten)]

      (execute! cxn data))))
