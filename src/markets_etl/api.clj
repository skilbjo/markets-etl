(ns markets-etl.api
  (:require
    [clj-http.client :as http]
    [clj-http.client :as http]
    [clojure.string :as string]
    [environ.core :refer [env]]
    [markets-etl.util :as util]))

(def quandl-api
  {:protocol  "https://"
   :url       "www.quandl.com/api/v3/datasets/"
   :series    {:wiki   "WIKI/"
               :stocks "stocks"}
   :format    "data.json"
   :api-key   (str "?api_key=" (env :quandl-api-key))})

(defn get-quandl-api [series ticker]
  (str (:protocol quandl-api)
       (:url quandl-api)
       (str series "/")
       (str ticker "/")
       (:format quandl-api)
       (:api-key quandl-api)))

(defn http-get [uri]
  (println uri)
  (let [response        (http/get uri)]
    (if (= 200 (:status response))
      (:body response)
      (:status response))))
