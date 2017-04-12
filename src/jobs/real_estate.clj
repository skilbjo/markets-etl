(ns jobs.real-estate
  (:require [clojure.string :as string]
            [markets-etl.api :as api]
            [jobs.fixture :as f]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def datasets
  '({:dataset "ZILL"
     :ticker ["Z94108_A"]}))

(def query-params
  {:limit 100
   :start_date "2016-01-01"
   :end_date util/now-utc})

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
        tranform              (fn [{:keys [dataset ticker date value] :as m}]
                                (let [area-category   (re-find #"[a-zA-Z]" ticker)
                                      indicator-code  (-> ticker
                                                          (string/split #"_")
                                                          (nth 1))
                                      area            (-> ticker
                                                          (string/split #"[a-zA-Z]")
                                                          (nth 1)
                                                          (string/replace #"_" ""))]
                                  {:dataset        dataset
                                   :ticker         ticker
                                   :date           date
                                   :value          value
                                   :area-category  area-category
                                   :indicator-code indicator-code
                                   :area           area}))
        database-it           (fn [{:keys [dataset ticker data] :as m}]
                                  (->> data
                                       (map #(assoc %
                                                    :dataset dataset
                                                    :ticker ticker))
                                       (map tranform)
                                       (util/map-seq-f-k util/postgreserize)
                                       (util/map-seq-fkv-v util/date-me)))
        map-update-or-insert! (fn [table col]
                                (map (fn [{:keys [dataset
                                                  ticker
                                                  date
                                                  area_category
                                                  indicator_code
                                                  area] :as m}]
                                       (sql/update-or-insert! table
                                                              [(util/multi-line-string
                                                                "dataset        = ? and    "
                                                                "ticker         = ? and    "
                                                                "date           = ? and    "
                                                                "area_category  = ? and    "
                                                                "indicator_code = ? and    "
                                                                "area    = ?        ")
                                                               dataset
                                                               ticker
                                                               date
                                                               area_category
                                                               indicator_code
                                                               area]
                                                              m)) col))]
    ;(->> f/fixture-multi                    ; Testing
         ;flatten
         ;(map clean-dataset)
         ;(map database-it)
         ;flatten
    (->> (map get-quandl-data datasets)    ; Live call
         flatten
         (map clean-dataset)
         (map database-it)
         flatten
         (map-update-or-insert! :dw.real_estate)
         util/printit
         )))

