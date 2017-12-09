(ns jobs.equities
  (:require [clj-time.coerce :as coerce]
            [clojure.string :as string]
            [clojure.core.reducers :as r]
            [clojure.data.json :as json]
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
   :start_date util/last-week
   :end_date   util/now})

(defn -main [& args]
  (let [get-data       (fn [{:keys [dataset
                                    ticker]}]
                         (->> ticker
                              (map (fn [tkr]
                                     (-> (api/query-quandl! dataset
                                                            tkr
                                                            query-params)
                                         (assoc :dataset dataset :ticker tkr))))))
        ;data                (->> datasets
                                 ;(map get-data))
        ;data                (-> f/fixture flatten)
        data              (->> datasets
                               (map get-data))
        prepare-row         (fn [{:keys [dataset
                                         ticker
                                         data] :as m}]
                              (let [data'         (-> data :dataset_data)
                                    columns       (->> (-> data'
                                                           :column_names
                                                           string/lower-case
                                                           (string/replace #"\." "")
                                                           json/read-str)
                                                      (map #(string/replace % #" " "_"))
                                                      (map #(keyword %)))
                                    data*         (-> data' :data)
                                    ;to-date-inst  (fn [k v]
                                                    ;(case k
                                                      ;:date (-> v coerce/to-sql-date)
                                    to-date-inst  (fn [coll]
                                                    (doseq [[k v] coll]
                                                      {k (case k
                                                           :date (-> v coerce/to-sql-date)
                                                           v)}))
                                    ]
                              {:dataset dataset
                               :ticker  ticker
                               :data    (->> data*
                                             (map #(zipmap columns %))
                                             (map to-date-inst))}))

        database-it           (fn [{:keys [dataset ticker data] :as m}]
                                (->> data
                                     (util/map-seq-f-k util/postgreserize)
                                     (util/map-seq-fkv-v util/date-me)
                                     (map #(assoc %
                                                  :dataset dataset
                                                  :ticker ticker))))
        map-update-or-insert! (fn [table col]
                                (map (fn [{:keys [dataset ticker date] :as m}]
                                       (sql/update-or-insert! table
                                                              [(util/multi-line-string
                                                                "dataset = ? and    "
                                                                "ticker  = ? and    "
                                                                "date    = ?        ")
                                                               dataset ticker date]
                                                              m)) col))
        ]
    (->> data
         ;(map prepare-row)
         util/print-it
         doall
         )

    ;#_(->> (map get-data datasets)    ; Live call
         ;flatten
         ;(r/map clean-dataset)
         ;(r/map database-it)
         ;(into '())
         ;flatten
         ;(map-update-or-insert! :dw.equities)
         ;doall)
  ))

