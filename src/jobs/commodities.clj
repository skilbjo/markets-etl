(ns jobs.commodities
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

(def crypto
  (list {:dataset "LOCALBTC"
         :ticker ["USD"]}))

(def precious-metals
  (list {:dataset "PERTH"
         :ticker ["LONMETALS"]}))

(def gold
  (list {:dataset "LBMA"
         :ticker ["GOLD"]}))

(def oil
  (list {:dataset "OPEC"
         :ticker ["ORB"]}))

(def query-params
  {:limit      5
   :start_date util/last-week
   :end_date   util/now})

(defmulti prepare-row :dataset)

(defmethod prepare-row "PERTH" [{:keys [dataset
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
           (map #(update % :date coerce/to-sql-date)) ; needed as Quandl returns
           (map #(update % :open (fn [v] (-> v        ; prices more than 3 decimal
                                             java.math.BigDecimal. ; places out
                                             (.setScale 4 BigDecimal/ROUND_HALF_UP)))))
           (map #(assoc % :dataset dataset :ticker ticker))))))

(defmethod prepare-row "LOCALBTC" [{:keys [dataset
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
           (map #(update % :date coerce/to-sql-date)) ; needed as Quandl returns
           (map #(update % :open (fn [v] (-> v        ; prices more than 3 decimal
                                             java.math.BigDecimal. ; places out
                                             (.setScale 4 BigDecimal/ROUND_HALF_UP)))))
           (map #(assoc % :dataset dataset :ticker ticker))))))

(defmethod prepare-row "LBMA" [{:keys [dataset
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
           (map #(update % :date coerce/to-sql-date)) ; needed as Quandl returns
           (map #(update % :open (fn [v] (-> v        ; prices more than 3 decimal
                                             java.math.BigDecimal. ; places out
                                             (.setScale 4 BigDecimal/ROUND_HALF_UP)))))
           (map #(assoc % :dataset dataset :ticker ticker))))))

(defmethod prepare-row "OPEC" [{:keys [dataset
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
           (map #(update % :date coerce/to-sql-date)) ; needed as Quandl returns
           (map #(update % :open (fn [v] (-> v        ; prices more than 3 decimal
                                             java.math.BigDecimal. ; places out
                                             (.setScale 4 BigDecimal/ROUND_HALF_UP)))))
           (map #(assoc % :dataset dataset :ticker ticker))))))
(defn update-or-insert! [db {:keys [dataset
                                    ticker
                                    date] :as record}]
  (sql/update-or-insert! db
                         :dw.commodities_fact
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
         #_(map #(update-or-insert! txn %))
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
          data        (->> (concat #_crypto #_precious-metals #_gold oil)
                           (map #(api/get-data % @api-keys query-params*))
                           flatten)]

      (util/print-it data)

      #_(execute! cxn data))))
