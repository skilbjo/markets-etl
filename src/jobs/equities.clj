(ns jobs.equities
  (:require [clj-time.coerce :as coerce]
            [clj-time.core :as time]
            [clojure.data.json :as json]
            [clojure.pprint :as p]
            [clojure.string :as string]
            [markets-etl.api :as api]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def now-utc (time/now))

(def datasets
  '({:dataset "wiki"
     :ticker ["FB" "SNAP"]}))

(defn -main [& args]
  (let [flatten-ticker  (fn [dataset ticker]
                          {:dataset dataset
                           :data    (-> (api/get-quandl-api dataset ticker)
                                        ;(api/http-get))})
                                        str)})
        get-quandl-data (fn [{:keys [dataset ticker] :as m}]
                          (map #(flatten-ticker dataset %) ticker))
        ;_               (p/pprint (get-quandl-data (first datasets)))
                         ]
    ;nil))
    (->> (map get-quandl-data datasets)
         util/printit
         )))
