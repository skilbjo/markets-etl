(ns jobs.equities
  (:require [clj-time.coerce :as coerce]
            [clojure.core.reducers :as r]
            [clojure.data.json :as json]
            [clojure.java.jdbc :as jdbc]
            [clojure.string :as string]
            [environ.core :refer [env]]
            [fixtures.equities :as f]
            [markets-etl.api :as api]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def datasets
  '({:dataset "WIKI"
   :ticker ["FB" "AMZN" "GOOG" "NVDA"]}))

(def query-params
  {:limit      10
   :start_date util/yesterday
   :end_date   util/last-week})

#_(defn execute! [cxn data]
  (jdbc/with-db-transaction [txn cxn]
    (->> data
         (map prepare-row)
         flatten
         (map #(update-or-insert! txn %))
         util/print-it
         )))


(defn -main [& args]
    (let [get-data       (fn [{:keys [dataset
                                      ticker]}]
                           (->> ticker
                                (map (fn [tkr]
                                       (-> (api/query-quandl! dataset
                                                              tkr
                                                              query-params)
                                           (assoc :dataset dataset :ticker tkr))))
                                flatten))
          ;data              (->> datasets
          ;(map get-data))
          data                (-> f/fixture)
          prepare-row         (fn [{:keys [dataset
                                           ticker]
                                    {:keys [column_names
                                            data]} :dataset_data}]
                                (let [columns       (->> (-> column_names
                                                             string/lower-case
                                                             (string/replace #"\." "")
                                                             (string/replace #"-" "_")
                                                             json/read-str)
                                                         (map #(string/replace % #" " "_"))
                                                         (map #(keyword %)))]
                                  (->> data
                                       (map #(zipmap columns %))
                                       (map #(update % :date coerce/to-sql-date))
                                       (map #(assoc % :dataset dataset :ticker ticker)))))
          update-or-insert! (fn [db {:keys [dataset
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
                                                     record))]
  (jdbc/with-db-connection [cxn (env :jdbc-db-uri)]
      (jdbc/with-db-transaction [txn cxn]
        (->> data
             (map prepare-row)
             flatten
             (map #(update-or-insert! txn %))
             util/print-it
             )))))
