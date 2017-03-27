(ns markets-etl.api
  (:require
    [clj-http.client :as http]
    [clj-http.client :as http]
    [clj-time.format :as format]
    [clojure.string :as string]
    [environ.core :refer [env]]
    [markets-etl.util :as util]))

(def ^:private quandl-api
  {:protocol  "https://"
   :url       "www.quandl.com/api/v3/datasets/"
   :format    "data.json"})

;(defn- assemble-url [dataset] (str base-url dataset "/data.json"))
(defn- keywordize [s]
  (-> s
      (string/replace #"\s" "-")
      string/lower-case keyword))

(defn date-time? [d] (or (string? d) (instance? org.joda.time.DateTime d)))

(defn- clean-dataset [d]
  d)
  ;(update (zipmap (map keywordize (:column_names d))
                  ;(apply map vector (:data d)))
          ;:date
          ;#(map format/parse %)))

(defn- http-get [uri]
  (let [response (http/get uri
                           {:query-params {"api_key" (env :quandl-api-key)}})]
    (if (= 200 (:status response))
      (:body response)
      (:status response))))

(defn- request [url params]
  (let [auth-params (merge {:api_key (env :quandl-api-key)} params) ; Allow custom value.
        _   (println "auth-params are " auth-params)
        {:keys [status headers body error]} (http/get url {:query-params auth-params})]
    (if error
        (println "Failed request, exception: " error)
        body)))

(defn query-quandl
  [dataset ticker & params]
  {:pre [(every? true? (util/allowed? params))]}
  (let [url                         (str (:protocol quandl-api)
                                         (:url quandl-api)
                                         (str dataset "/")
                                         (str ticker "/")
                                         (:format quandl-api))
        _                           (println params)
        response                    (request url (first params))
        {:keys [status body data]}  response]
    (if (= 200 status)
      (println "Failed request, exception: " status))
      (clean-dataset body)))
  ;(-> (str (:protocol quandl-api)
           ;(:url quandl-api)
           ;(str dataset "/")
           ;(str ticker "/")
           ;(:format quandl-api))
      ;http-get))

