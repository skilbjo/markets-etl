(ns markets-etl.api
  (:require [clj-http.client :as http]
            [clojure.string :as string]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]]
            [markets-etl.util :as util]))

(def ^:private quandl-api
  {:protocol  "https://"
   :url       "www.quandl.com/api/v3/datasets/"
   :format    "data.json"})

(def ^:private morningstar-api
  {:protocol  "http://"
   :url       "globalquote.morningstar.com/globalcomponent/RealtimeHistoricalStockData.ashx?"
   :required_params "&showVol=true&dtype=his"})

(def ^:private intrinio-api
  {:protocol  "https://"
   :url       "api.intrinio.com/"})

(def ^:private allowed
  {:collapse     #{"none" "daily" "weekly" "monthly" "quarterly" "annual"}
   :transform    #{"none" "rdiff" "diff" "cumul" "normalize"}
   :order        #{"asc" "desc"}
   :rows         integer?
   :limit        integer?
   :column_index integer?
   :start_date   #(or (string? %)
                      (instance? org.joda.time.DateTime %))
   :end_date     #(or (string? %)
                      (instance? org.joda.time.DateTime %))})

(defn allowed? [m]
  (->> m
       (map (fn [[k v]]
              ((allowed k) v)))))

(defn query-tiingo!
  ([ticker]
   (query-tiingo! ticker {}))
  ([ticker paramz]
   {:pre [(every? true? (allowed? paramz))]}
   (let [params   (dissoc paramz :limit)
         url      (str (:protocol intrinio-api)
                       (:url intrinio-api)
                       (str "ticker=" ticker)
                       (str "&range="
                            (:start_date params)
                            "|"
                            (:end_date params))
                       )
         response (http/get url
                            {:query-params params})
         {:keys [status body]}  response
         _        (log/debug ticker)
         _        (log/debug params)
         #__      #_(log/debug body)]
     (if (= 200 status)
       (-> body
           (json/read-str :key-fn keyword))
       (log/error "Failed request, exception: " status)))))

(defn query-intrinio!
  ([ticker]
   (query-intrinio! ticker {}))
  ([ticker paramz]
   {:pre [(every? true? (allowed? paramz))]}
   (let [params   (dissoc paramz :limit)
         url      (str (:protocol intrinio-api)
                       (:url intrinio-api)
                       (str "ticker=" ticker)
                       (str "&range="
                            (:start_date params)
                            "|"
                            (:end_date params))
                       )
         response (http/get url
                            {:query-params params})
         {:keys [status body]}  response
         _        (log/debug ticker)
         _        (log/debug params)
         #__      #_(log/debug body)]
     (if (= 200 status)
       (-> body
           (json/read-str :key-fn keyword))
       (log/error "Failed request, exception: " status)))))

(defn query-morningstar!
  ([ticker]
   (query-morningstar! ticker {}))
  ([ticker paramz]
   {:pre [(every? true? (allowed? paramz))]}
   (let [params   (dissoc paramz :limit)
         url      (str (:protocol morningstar-api)
                       (:url morningstar-api)
                       (str "ticker=" ticker)
                       (str "&range="
                            (:start_date params)
                            "|"
                            (:end_date params))
                       (:required_params morningstar-api))
         response (http/get url
                            {:query-params params})
         {:keys [status body]}  response
         body'    (-> body
                      (string/replace #"NaN" "null"))
         _        (log/debug ticker)
         _        (log/debug params)
         _        (log/debug url)
         #__      #_(log/debug body')]
     (if (and (= 200 status) ((comp not empty?) body'))
       (-> body'
           (json/read-str :key-fn (comp keyword string/lower-case)))
       (log/error "Failed request, exception: " status)))))

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
         {:keys [status body]}  response
         _        (log/debug ticker)
         _        (log/debug params)
         #__      #_(log/debug body)]
     (if (= 200 status)
       (-> body
           (json/read-str :key-fn keyword))
       (log/error "Failed request, exception: " status)))))

(defmulti get-data :dataset)

(defmethod get-data "MSTAR" [{:keys [dataset
                                     ticker]}
                             query-params]
  (->> ticker
       (map (fn [tkr]
              (-> (query-morningstar! tkr
                                      query-params)
                  (assoc :dataset dataset :ticker tkr))))))

(defmethod get-data :default [{:keys [dataset
                                      ticker]}
                              query-params]
  (->> ticker
       (map (fn [tkr]
              (-> (query-quandl! dataset
                                 tkr
                                 query-params)
                  (assoc :dataset dataset :ticker tkr))))))
