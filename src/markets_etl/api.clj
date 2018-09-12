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
   :url       "api.intrinio.com/"
   :prefix    "prices"})

(def ^:private tiingo-api
  {:protocol  "https://"
   :url       "api.tiingo.com/tiingo/daily/"
   :suffix    "prices"})

(def ^:private alpha-vantage-api
  {:protocol  "https://"
   :url       "www.alphavantage.co/query?"
   :params    "&outputsize=compact&datatype=json"
   :api-key   (str "&apikey=" (-> :alpha-vantage-api-key env))})

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

(defn query-alpha-vantage!
  ([ticker]
   (query-alpha-vantage! ticker {}))
  ([ticker paramz]
   {:pre [(every? true? (allowed? paramz))]}
   (let [params   (dissoc paramz :limit)
         url      (str (:protocol alpha-vantage-api)
                       (:url alpha-vantage-api)
                       (str "function=TIME_SERIES_DAILY")
                       (str "&symbol=" ticker)
                       (:params alpha-vantage-api)
                       (:api-key alpha-vantage-api))
         response (try
                    (http/get url)
                    (catch Exception e
                      #_(log/error "Error in query-alpha-vantage!: "
                                   (ex-data e))
                      (ex-data e)))
         {:keys [status body]}  response
         _        (log/debug ticker)
         _        (log/debug params)
         #__      #_(log/info body)]
     (if (= 200 status)
       (-> body
           (json/read-str :key-fn (comp util/space->underscore string/lower-case keyword)))
       (log/error "Alpha-vantage request, status:" status "Ticker:" ticker)))))

(defn query-tiingo!
  ([ticker]
   (query-tiingo! ticker {}))
  ([ticker paramz]
   {:pre [(every? true? (allowed? paramz))]}
   (let [params   (dissoc paramz :limit)
         url      (str (:protocol tiingo-api)
                       (:url tiingo-api)
                       (str ticker "/")
                       (:suffix tiingo-api)
                       (str "?startDate="
                            (:start_date params)
                            "&endDate="
                            (:end_date params)))

         response (try
                    (http/get url
                              {:headers {:authorization (str "Token "
                                                             (-> :tiingo-api-key
                                                                 env))}})
                    (catch Exception e
                      #_(log/error "Error in query-morningstar!: "
                                   (ex-data e))
                      (ex-data e)))
         {:keys [status body]}  response
         _        (log/debug ticker)
         _        (log/debug params)
         #__      #_(log/info body)]
     (if (= 200 status)
       (-> body
           (json/read-str :key-fn (comp keyword string/lower-case)))
       (log/error "Tiingo request, status:" status "Ticker:" ticker)))))

(defn query-intrinio!
  ([ticker]
   (query-intrinio! ticker {}))
  ([ticker paramz]
   {:pre [(every? true? (allowed? paramz))]}
   (let [params   (dissoc paramz :limit)
         url      (str (:protocol intrinio-api)
                       (:url intrinio-api)
                       (:prefix intrinio-api)
                       (str "?identifier=" ticker)
                       (str "&start_date="
                            (:start_date params)
                            "&end_date="
                            (:end_date params)))
         response (try
                    (http/get url
                              {:basic-auth ["" ""]})
                    (catch Exception e
                      #_(log/error "Error in query-morningstar!: "
                                   (ex-data e))
                      (ex-data e)))
         {:keys [status body]}  response
         _        (log/debug ticker)
         _        (log/debug params)
         #__      #_(log/debug body)]
     (if (= 200 status)
       (-> body
           (json/read-str :key-fn keyword))
       (log/error "Intrinio request, exception:" status "Ticker:" ticker)))))

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
         response (try
                    (http/get url
                              {:query-params params})
                    (catch Exception e
                      #_(log/error "Error in query-morningstar!: "
                                   (ex-data e))
                      (ex-data e)))
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
       (log/error "Morningstar-api request, exception:" status
                  "Ticker:" ticker)))))

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
         response (try
                    (http/get url
                              {:query-params params})
                    (catch Exception e
                      #_(log/error "Error in query-morningstar!: "
                                   (ex-data e))
                      (ex-data e)))
         {:keys [status body]}  response
         _        (log/debug ticker)
         _        (log/debug params)
         #__      #_(log/debug body)]
     (if (= 200 status)
       (-> body
           (json/read-str :key-fn keyword)
           ;; This is commented out for quandl workflow to work without data
           ;; if WIKI dataset is turned back on - uncomment 'first'
           #_first)
       (log/error "Quandl request, exception:" status "Ticker:" ticker)))))

(defmulti get-data :dataset)

(defmethod get-data "TIINGO" [{:keys [dataset
                                      ticker]}
                              query-params]
  (->> ticker
       (map (fn [tkr]
              (->> (query-tiingo! tkr
                                  query-params)
                   (map #(assoc % :dataset dataset :ticker tkr)))))))

(defmethod get-data "MSTAR" [{:keys [dataset
                                     ticker]}
                             query-params]
  (->> ticker
       (map (fn [tkr]
              (-> (query-morningstar! tkr
                                      query-params)
                  (assoc :dataset dataset :ticker tkr))))))

(defmethod get-data "INTRINIO" [{:keys [dataset
                                        ticker]}
                                query-params]
  (->> ticker
       (map (fn [tkr]
              (-> (query-intrinio! tkr query-params)
                  (assoc :dataset dataset :ticker tkr))))))

(defmethod get-data :default [{:keys [dataset
                                      ticker] :as m}
                              query-params]
  (->> ticker
       (map (fn [tkr]
              (-> (query-quandl! dataset tkr query-params)
                  (assoc :dataset dataset :ticker tkr))))))
