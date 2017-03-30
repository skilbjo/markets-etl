(ns jobs.equities
  (:require [clj-time.coerce :as coerce]
            [clj-time.core :as time]
            [clojure.data.json :as json]
            [clojure.pprint :as p]
            [clojure.string :as string]
            [markets-etl.api :as api]
            [jobs.fixture :as f]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def now-utc (time/now))

(def datasets
  '({:dataset "wiki"
     :ticker ["FB" "AMZN" "GOOG"]}))

(defn -main [& args]
  (let [flatten-ticker  (fn [dataset ticker]
                          {:dataset dataset
                           :ticker  ticker
                           :data    (-> (api/query-quandl dataset
                                                          ticker
                                                          {:limit 2
                                                           :start_date "2017-01-04"
                                                           :end_date "2017-01-05"}))})
                                        ;util/printit)})
                                        ;json/read-str)})
        get-quandl-data (fn [{:keys [dataset ticker] :as m}]
                          (map #(flatten-ticker dataset %) ticker))
        clean-dataset   (fn [{:keys [dataset ticker data] :as response}]
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
                         ]
    ;(->> f/fixture                         ; Testing
         ;flatten
         ;(map clean-dataset)
         ;util/printit
    (->> (map get-quandl-data datasets)    ; Live call
         flatten
         (map clean-dataset)
         util/printit
         )))
