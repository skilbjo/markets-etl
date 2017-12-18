(ns jobs.currency
  (:require [amazonica.aws.s3 :as s3]
            ;[amazonica.aws.s3transfer :as s3xfer]
            [clj-time.coerce :as coerce]
            [clojure.data.csv :as csv]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [clojure.string :as string]
            [environ.core :refer [env]]
            [fixtures.currency :as f]
            [markets-etl.api :as api]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def datasets
  '({:dataset "CURRFX"
     :ticker ["EURUSD" "GBPUSD"]}))

(def query-params
  {:limit      20
   :start_date util/last-week
   :end_date   util/now})

(defn prepare-row [{:keys [dataset
                           ticker]
                    {:keys [column_names
                            data]} :dataset_data}]
  (let [columns       (->> (-> column_names
                               string/lower-case
                               (string/replace #"\." "")
                               (string/replace #"-" "_")
                               (string/replace #"\(" "")
                               (string/replace #"\)" "")
                               json/read-str)
                           (map #(string/replace % #" " "_"))
                           (map #(keyword %)))]
    (->> data
         (map #(zipmap columns %))
         (map #(update % :date coerce/to-sql-date))
         (map #(assoc % :dataset dataset
                      :ticker ticker
                      :currency (-> ticker (subs 0 3)))))))

(defn update-or-insert! [db {:keys [dataset
                                    ticker
                                    date] :as record}]
  (sql/update-or-insert! db
                         :dw.currency
                         [(util/multi-line-string
                           "dataset = ? and "
                           "ticker  = ? and "
                           "date    = ?     ")
                          dataset
                          ticker
                          date]
                         record))

(defn write-to-csv [coll]
  (let [columns [:dataset
                 :currency
                 :date
                 :rate
                 :high_est
                 :low_est]
        headers (map name columns)
        rows    (map #(map % columns) coll)]
    (with-open [writer (io/writer "/tmp/currency.csv")]
      (csv/write-csv writer (cons headers rows)))))

(defn upload-to-s3 [file]
  (s3/put-object :bucket-name "skilbjo-data"
              :key         (str "datalake/currency/"
                                "s3uploaddate=" util/now)
              :metadata    {:server-side-encryption "AES256"}
              :file        "/tmp/currency.csv"))

(defn execute! [cxn data]
  (jdbc/with-db-transaction [txn cxn]
    (->> data
         (map prepare-row)
         flatten
         ;util/print-and-die
         (map #(write-to-csv %))
         #_(map #(update-or-insert! txn %))
         doall)))

(defn -main [& args]
  (jdbc/with-db-connection [cxn (-> :jdbc-db-uri env)]
    (let [get-data (fn [{:keys [dataset
                                ticker]}]
                     (->> ticker
                          (map (fn [tkr]
                                 (-> (api/query-quandl! dataset
                                                        tkr
                                                        query-params)
                                     (assoc :dataset dataset :ticker tkr))))))
          ;data        (->> datasets
                           ;(map get-data)
                           ;flatten)
          data  (-> f/source)]

      (execute! cxn data))))
