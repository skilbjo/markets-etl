(ns jobs.economics
  (:require [markets-etl.api :as api]
            [jobs.fixture :as f]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def datasets
  '({:dataset "FRED"
     :ticker ["GDP" "M1" "DFF" "UNRATE"]}))

(def query-params
  {:limit 100
   :start_date "2016-01-01"
   :end_date util/now})

(defn -main [& args]
  (let [flatten-ticker        (fn [dataset ticker]
                                {:dataset dataset
                                 :ticker  ticker
                                 :data    (-> (api/query-quandl dataset
                                                                ticker
                                                                query-params))})
        get-quandl-data       (fn [{:keys [dataset ticker] :as m}]
                                (map #(flatten-ticker dataset %) ticker))
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
                                                              m)) col))]
    (->> (map get-quandl-data datasets)    ; Live call
         flatten
         (map clean-dataset)
         (map database-it)
         flatten
         (map-update-or-insert! :dw.equities)
         util/printit
         )))

