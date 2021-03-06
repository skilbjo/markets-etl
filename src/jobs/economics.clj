(ns jobs.economics
  (:require [clj-time.coerce :as coerce]
            [clj-time.core :as time]
            [clj-time.format :as format]
            [clojure.data.json :as json]
            [clojure.java.jdbc :as jdbc]
            [clojure.tools.cli :as cli]
            [clojure.string :as string]
            [environ.core :refer [env]]
            [markets-etl.api :as api]
            [markets-etl.error :as error]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def api-keys   ;; env vars are encrypted on lambda
  (delay        ;; defs evaluated at compile time; delay until runtime
   {:quandl-api-key        (-> :quandl-api-key env)
    :fred-api-key          (-> :fred-api-key env)}))

(def cli-options
  [["-d" "--date DATE" "Start date (month) (yyyy-mm-dd format) to start processing"
    :parse-fn #(format/parse %)
    :default  util/last-week]
   ["-h" "--help"]])

(def quandl
  '({:dataset "FRED"
     :ticker ["GDP" "M1" "DFF" "UNRATE"]}))

(def fred-api                                ; GDP          : GNPCA / GDP
  '({:dataset "FRED-API"                     ; Sentiment    : UMCSENT
     :ticker ["GDP" "GNPCA" "UMCSENT"        ; Unemployment : UNRATE
              "UNRATE"]}))

(def query-params
  {:limit      500
   :start_date util/last-quarter
   :end_date   util/now})

(defmulti prepare-row :dataset)

(defmethod prepare-row "FRED-API" [{:keys [dataset
                                           ticker
                                           observations]}]
  (->> observations
       (map #(assoc {}
                    :dataset  dataset
                    :ticker   ticker
                    :date     (-> % :date coerce/to-sql-date)
                    :value    (-> % :value util/string->decimal)))
       (filter #(some? (:value %))))) ; fred-api will give nils for certain dates; filter these out

(defmethod prepare-row :default [{:keys [dataset
                                         ticker]
                                  {:keys [column_names
                                          data]} :dataset_data}]
  (when (seq data)
    (let [columns       (->> (-> column_names
                                 string/lower-case
                                 (string/replace #"\." "")
                                 (string/replace #"-" "_")
                                 json/read-str)
                             (map #(string/replace % #" " "_"))
                             (map keyword))]
      (->> data
           (map #(zipmap columns %))
           (map #(update % :date coerce/to-sql-date))
           (map #(assoc % :dataset dataset :ticker ticker))))))

(defn update-or-insert! [db {:keys [dataset
                                    ticker
                                    date] :as record}]
  (sql/update-or-insert! db
                         :dw.economics_fact
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
          data        (->> (concat quandl fred-api)
                           (map #(api/get-data % @api-keys query-params*))
                           flatten)]

      (execute! cxn data))))
