(ns markets-etl.api
  (:require
    [clj-http.client :as http]
    [clj-http.client :as http]
    [clj-time.format :as format]
    [clojure.data.json :as json]
    [clojure.pprint :as p]
    [clojure.string :as string]
    [environ.core :refer [env]]
    [markets-etl.util :as util]))

(def ^:private quandl-api
  {:protocol  "https://"
   :url       "www.quandl.com/api/v3/datasets/"
   :format    "data.json"})

(defn date-time? [d] (or (string? d) (instance? org.joda.time.DateTime d)))

(defn- clean-dataset [d]
  (let [column-names    (get d "column_names")
        data            (get d "data")]
  (p/pprint column-names)
  (p/pprint data)
  (zipmap (map util/keywordize column-names)
          (apply map vector data))))
          ;:date
          ;#(map format/parse %)))
  ;(update (zipmap (map util/keywordize column-names)
                  ;(apply map vector data)))
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
        response (http/get url {:query-params auth-params})
        {:keys [status headers body error]} (http/get url {:query-params auth-params})]
    {:status status :body body}))

(defn query-quandl
  [dataset ticker & paramz]
  {:pre [(every? true? (util/allowed? paramz))]}
  (let [params   (first paramz)
        url      (str (:protocol quandl-api)
                      (:url quandl-api)
                      (str dataset "/")
                      (str ticker "/")
                      (:format quandl-api))
        response (request url params)
        ;_        (p/pprint response)
        {:keys [status body]}  response]
    (if (= 200 status)
      (clean-dataset (-> body
                         json/read-str
                         (get "dataset_data")))
      (println "Failed request, exception: " status))))
  ;(-> (str (:protocol quandl-api)
           ;(:url quandl-api)
           ;(str dataset "/")
           ;(str ticker "/")
           ;(:format quandl-api))
      ;http-get))

