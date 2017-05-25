(ns jobs.economics
  (:require [clojure.string :as string]
            [markets-etl.api :as api]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def datasets
  '({:dataset "FRED"
     :ticker ["GDP" "M1" "DFF" "UNRATE"]}))

(def query-params
  {:limit 20
   :start_date util/last-week
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
        tranform              (fn [{:keys [dataset ticker date] :as m}]
                                (->> m
                                     (map (fn [[k v]]
                                            (condp #(string/starts-with? %2 %1) (name k)
                                              "dataset" nil
                                              "ticker"  nil
                                              "date"    nil
                                              {(keyword "value") v})))
                                     (remove nil?)
                                     ;(util/printit)
                                     (map #(merge {:dataset        dataset
                                                   :ticker         ticker
                                                   :date           date}
                                                  %))))
        database-it           (fn [{:keys [dataset ticker data] :as m}]
                                (->> data
                                     (map #(assoc %
                                                  :dataset dataset
                                                  :ticker ticker))
                                     (map tranform)
                                     flatten
                                     (util/map-seq-f-k util/postgreserize)
                                     (util/map-seq-fkv-v util/date-me)))
        map-update-or-insert! (fn [table col]
                                (map (fn [{:keys [dataset ticker date] :as m}]
                                       (sql/update-or-insert! table
                                                              [(util/multi-line-string
                                                                "dataset = ? and    "
                                                                "ticker  = ? and    "
                                                                "date    = ?        ")
                                                               dataset ticker date]
                                                              m)) col))]
    (->> (map get-quandl-data datasets)
         flatten
         (map clean-dataset)
         (map database-it)
         flatten
         (map-update-or-insert! :dw.economics)
         util/printit)))

