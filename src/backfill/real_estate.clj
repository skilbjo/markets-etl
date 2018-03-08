(ns backfill.real-estate
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]]
            [jobs.real-estate :refer :all :rename {-main _
                                                   query-params __}]
            [markets-etl.api :as api]
            [markets-etl.error :as error]
            [markets-etl.util :as util])
  (:gen-class))

(def query-params
  {:limit      2600
   :start_date util/five-years-ago
   :end_date   util/now})

(defn -main [& args]
  (error/set-default-error-handler)
  (jdbc/with-db-connection [cxn (-> :jdbc-db-uri env)]
    (let [month (first args)
          data  (->> (concat datasets)
                     (map #(api/get-data % query-params))
                     flatten)]

      (execute! cxn data))))
