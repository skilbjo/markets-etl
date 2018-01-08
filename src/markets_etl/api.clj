(ns markets-etl.api
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]
            [environ.core :refer [env]]
            [markets-etl.util :as util]))

(def ^:private quandl-api
  {:protocol  "https://"
   :url       "www.quandl.com/api/v3/datasets/"
   :format    "data.json"})

(def ^:private allowed
  {:collapse     #{"none" "daily" "weekly" "monthly" "quarterly" "annual"}
   :transform    #{"none" "rdiff" "diff" "cumul" "normalize"}
   :order        #{"asc" "desc"}
   :rows         integer?
   :limit        integer?
   :column_index integer?
   :start_date   #(instance? org.joda.time.DateTime %)
   :end_date     #(or (string? %)
                      (instance? org.joda.time.DateTime %))})

(defn allowed? [m]
  (->> m
       (map (fn [[k v]]
              ((allowed k) v)))))

(defn query-quandl!
  ([dataset ticker]
   (query-quandl! dataset ticker {}))
  ([dataset ticker paramz]
   {:pre [(every? true? (allowed? paramz))]}
   (let [url      (str (:protocol quandl-api)
                       (:url quandl-api)
                       (str dataset "/")
                       (str ticker "/")
                       (:format quandl-api))
         params   (-> paramz
                      (assoc :api_key (-> :quandl-api-key env)))
         response (http/get url
                            {:query-params params})
         {:keys [status body]}  response]
     (if (= 200 status)
       (-> body
           (json/read-str :key-fn keyword))
       (println "Failed request, exception: " status)))))
