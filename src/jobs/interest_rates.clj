(ns jobs.interest-rates
  (:require [clj-time.coerce :as coerce]
            [clojure.data.json :as json]
            [clojure.java.jdbc :as jdbc]
            [clojure.string :as string]
            [environ.core :refer [env]]
            [markets-etl.api :as api]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def datasets
  '({:dataset "FED"
     :ticker ["RIFSPFF_N_D"]}
    {:dataset "USTREASURY"
     :ticker ["YIELD" "LONGTERMRATES"]}))

(def query-params
  {:limit 20
   :start_date util/last-week
   :end_date util/now})

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
                           (map #(keyword %)))
        data'             (->> data
                               (map #(zipmap columns %))
                               (map #(update % :date coerce/to-sql-date))
                               (map #(assoc % :dataset dataset :ticker ticker)))
        transform-row     (fn [{:keys [date] :as m}]
                            (-> (->> m
                                     (map (fn [[k v]]
                                            (case k
                                              :dataset nil
                                              :ticker  nil
                                              :date    nil
                                              {:key    (-> k name)
                                               :value  v})))
                                     (remove nil?)
                                     first)
                                (assoc :dataset dataset
                                       :ticker  ticker
                                       :date    date)))]
    (->> data'
         (map transform-row))))

(defn update-or-insert! [db {:keys [dataset
                                    ticker
                                    date] :as record}]
  (sql/update-or-insert! db
                         :dw.interest_rates
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
