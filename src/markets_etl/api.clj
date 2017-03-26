(ns markets-etl.api
  (:require
    [clj-http.client :as http]
    [clj-http.client :as http]
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

(defn query-quandl [dataset ticker]
  (-> (str (:protocol quandl-api)
           (:url quandl-api)
           (str dataset "/")
           (str ticker "/")
           (:format quandl-api))
      http-get))

