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
     :ticker ["FB"]}))

(defn -main [& args]
  (let [flatten-ticker  (fn [dataset ticker]
                          {:dataset dataset
                           :data    (-> (api/query-quandl dataset
                                                          ticker
                                                          {:limit 1
                                                           :start_date "2017-01-04"
                                                           :end_date "2017-01-05"})
                                        ;)})
                                        ;util/printit)})
                                        json/read-str)})
        get-quandl-data (fn [{:keys [dataset ticker] :as m}]
                          (map #(flatten-ticker dataset %) ticker))
        ;_               (p/pprint (get-quandl-data (first datasets)))
                         ]
    ;(->> f/fixture
         ;util/printit
    (->> (map get-quandl-data datasets)
         flatten
         util/printit
         )))
