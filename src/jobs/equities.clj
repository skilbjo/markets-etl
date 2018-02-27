(ns jobs.equities
  (:require [clj-time.coerce :as coerce]
            [clojure.data.json :as json]
            [clojure.java.jdbc :as jdbc]
            [clojure.string :as string]
            [environ.core :refer [env]]
            [markets-etl.api :as api]
            [markets-etl.error :as error]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def datasets
  '({:dataset "WIKI"
     :ticker ["FB" "AMZN" "GOOG" "NVDA" "BRK.B" "CY" "INTC" "TSM" "TXN" "V"]}))

(def morningstar
  '({:dataset "MSTAR"
     :ticker ["VEMAX" "VEURX" "VEXPX" "VGWAX" "VITAX" "VIMAX" "VMRAX" "VPACX" "VGSLX" "VTIAX" "VTSAX" "VWINX" "VWENX" "VWNDX" "VFH" "VEA" "VWO" "VHT" "VGT"]}))

(def query-params
  {:limit      10
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
                               json/read-str)
                           (map #(string/replace % #" " "_"))
                           (map keyword))]
    (->> data
         (map #(zipmap columns %))
         (map #(update % :date coerce/to-sql-date))
         (map #(assoc % :dataset dataset :ticker ticker)))))

(defn update-or-insert! [db {:keys [dataset
                                    ticker
                                    date] :as record}]
  (sql/update-or-insert! db
                         :dw.equities
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
         (map #(update-or-insert! txn %))
         doall)))

(defn -main [& args]
  (error/set-default-error-handler)
  (jdbc/with-db-connection [cxn (-> :jdbc-db-uri env)]
    (let [get-data (fn [{:keys [dataset
                                ticker]}]
                     (->> ticker
                          (map (fn [tkr]
                                 (-> (api/query-quandl! dataset
                                                        tkr
                                                        query-params)
                                     (assoc :dataset dataset :ticker tkr))))))
          data        (->> datasets
                           (map get-data)
                           flatten)]

      (execute! cxn data))))
