(ns markets-etl.api
  (:require [clj-http.client :as http]
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

(defn- http-get [uri]
  (let [response (http/get uri
                           {:query-params {"api_key" (env :quandl-api-key)}})]
    (if (= 200 (:status response))
      (:body response)
      (:status response))))

(defn- request [url params]
  (let [auth-params                         (merge {:api_key (env :quandl-api-key)}
                                                   params)
        response                            (http/get url
                                                      {:query-params auth-params})
        {:keys [status headers body error]} (http/get url
                                                      {:query-params auth-params})]
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
        {:keys [status body]}  response]
    (if (= 200 status)
      (-> body
          json/read-str)
      (println "Failed request, exception: " status))))
