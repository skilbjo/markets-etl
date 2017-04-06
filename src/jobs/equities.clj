(ns jobs.equities
  (:require [clj-time.core :as time]
            [clojure.pprint :as p]
            [markets-etl.api :as api]
            [jobs.fixture :as f]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def now-utc (time/now))

(def datasets
  '({:dataset "wiki"
     :ticker ["FB" "AMZN" "GOOG"]}))

(def query-params
  {:limit 2
   :start_date "2017-01-01"
   :end_date now-utc})

(defn -main [& args]
  (let [flatten-ticker  (fn [dataset ticker]
                          {:dataset dataset
                           :ticker  ticker
                           :data    (-> (api/query-quandl dataset
                                                          ticker
                                                          query-params))})
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
        map-kv           (fn [f coll]
                          (reduce-kv (fn [m k v]
                                       (assoc m (f k) v)) {} coll))
        mapp-kv         (fn [f coll]
                          (map #(map-kv f %) coll))
        ;map-kv          (fn [f coll]
                          ;(reduce-kv (fn [m k v] (assoc m k (f v))) (empty coll) coll))
        prepare-row     (fn [{:keys [dataset ticker data] :as m}]
                            (->> data
                                 (mapp-kv util/postgreserize)
                                ;(reduce-kv (fn [[k v]]
                                             ;{k v}) {})
                                 ;(map (fn [m]
                                        ;(reduce-kv (fn [[k v]]
                                                     ;{k v}) {} m)))
                                        ;{k v}))
                                 (map #(assoc %
                                              :dataset dataset
                                              :ticker ticker)
                                      )))]
    (->> f/fixture-multi                    ; Testing
         flatten
         (map clean-dataset)
         (map prepare-row)
         flatten
    ;(->> (map get-quandl-data datasets)    ; Live call
         ;flatten
         ;(map clean-dataset)
         ;(map prepare-row)
         ;flatten
         util/printit
         (sql/insert-dw-multi! (sql/get-dw-conn) :dw.equities)
         )))
