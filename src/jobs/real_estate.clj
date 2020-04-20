(ns jobs.real-estate
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
    {:quandl-api-key        (-> :quandl-api-key env)}))

(def cli-options
  [["-d" "--date DATE" "Start date (month) (yyyy-mm-dd format) to start processing"
    :parse-fn #(format/parse %)
    :default  util/last-week]
   ["-h" "--help"]])

(def datasets
  '({:dataset "ZILLOW"
     :ticker ["Z94108_ZHVIAH"]}))

(def query-params
  {:limit      500
   :start_date util/last-quarter
   :end_date   util/now})

(defn prepare-row [{:keys [dataset
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
                             (map keyword))
          area_category     (re-find #"[a-zA-Z]" ticker)
          indicator_code    (-> ticker
                                (string/split #"_")
                                (nth 1))
          area              (-> ticker
                                (string/split #"[a-zA-Z]")
                                (nth 1)
                                (string/replace #"_" ""))
          data'             (->> data
                                 (map #(zipmap columns %))
                                 (map #(update % :date coerce/to-sql-date))
                                 (map #(assoc % :dataset dataset :ticker ticker)))
          transform-row     (fn [m]
                              {:dataset        dataset
                               :ticker         ticker
                               :date           (-> m :date)
                               :value          (-> m :value)
                               :area_category  area_category
                               :indicator_code indicator_code
                               :area           area})]
      (->> data'
           (map transform-row)))))

(defn update-or-insert! [db {:keys [dataset
                                    ticker
                                    date] :as record}]
  (sql/update-or-insert! db
                         :dw.real_estate_fact
                         [(util/multi-line-string
                           "dataset = ? and "
                           "ticker  = ? and "
                           "date    = ?     ")
                          dataset
                          ticker
                          date]
                         record))

(defn execute! [cxn data]
  (error/set-default-error-handler)
  (jdbc/with-db-transaction [txn cxn]
    (->> data
         (map prepare-row)
         flatten
         (remove nil?)
         (map #(update-or-insert! txn %))
         doall)))

(defn -main [& args]
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
          data        (->> datasets
                           (map #(api/get-data % @api-keys query-params*))
                           flatten)]

      (execute! cxn data))))
