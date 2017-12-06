(ns jobs.equities
  (:require [clojure.core.reducers :as r]
            [fixtures.equities :as f]
            [markets-etl.api :as api]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def datasets
  '({:dataset "WIKI"
   :ticker ["FB" "AMZN" "GOOG" "NVDA"]}))

(def query-params
  {:limit 10
   :start_date util/last-week
   :end_date util/now})

(defn -main [& args]
  (let [get-quandl-data       (fn [{:keys [dataset ticker]}]
                                (->> ticker
                                     (map (fn [ticker]
                                            {:dataset dataset
                                             :ticker  ticker
                                             :data    (api/query-quandl! dataset
                                                                         ticker
                                                                         query-params)}))))

        data                (->> datasets
                                 (map get-quandl-data))
        ;data                (-> f/fixture)
        clean-dataset         (fn [{:keys [dataset ticker data] :as response}]
                                (let [column-names    (map util/keywordize
                                                           (-> data
                                                               (get "dataset_data")
                                                               (get "column_names")))
                                      data            (-> data
                                                          (get "dataset_data")
                                                          (get "data"))]
                                  {:dataset dataset
                                   :ticker  ticker
                                   :data    (map #(zipmap column-names %) data)}))
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
         util/print-it)

    #_(->> datasets
         (map get-quandl-data)
         util/print-it
         doall)
    ;(->> f/fixture-multi                  ; Testing
         ;flatten
         ;(r/map clean-dataset)
         ;(r/map database-it)
         ;(into '())
         ;flatten
         ;util/printit)))
    #_(->> (map get-quandl-data datasets)    ; Live call
         flatten
         (r/map clean-dataset)
         (r/map database-it)
         (into '())
         flatten
         (map-update-or-insert! :dw.equities)
         doall)))

