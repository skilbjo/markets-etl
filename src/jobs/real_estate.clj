(ns jobs.real-estate
  (:require [clj-time.coerce :as coerce]
            [clojure.data.json :as json]
            [clojure.java.jdbc :as jdbc]
            [clojure.string :as string]
            [environ.core :refer [env]]
            [markets-etl.api :as api]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def datasets
  '({:dataset "ZILLOW"
     :ticker ["Z94108_ZHVIAH"]}))

(def query-params
  {:limit 20
   :start_date util/last-year
   :end_date util/now})

(defn prepare-row [{:keys [dataset
                           ticker]
                    {:keys [column_names
                            data]} :dataset_data}]
  (let [columns       (->> (-> column_names
                               string/lower-case
                               (string/replace #"\." "")
                               (string/replace #"-" "_")
                               json/read-str)
                           (map #(string/replace % #" " "_"))
                           (map #(keyword %)))]

    ;; fix this ;;

    #_(let [area-category   (re-find #"[a-zA-Z]" ticker)
            indicator-code  (-> ticker
                                (string/split #"_")
                                (nth 1))
            area            (-> ticker
                                (string/split #"[a-zA-Z]")
                                (nth 1)
                                (string/replace #"_" ""))]
        {:dataset        dataset
         :ticker         ticker
         :date           date
         :value          value
         :area-category  area-category
         :indicator-code indicator-code
         :area           area})

    (->> data
         (map #(zipmap columns %))
         (map #(update % :date coerce/to-sql-date))
         (map #(assoc % :dataset dataset :ticker ticker)))))

(defn update-or-insert! [db {:keys [dataset
                                    ticker
                                    date] :as record}]
  (sql/update-or-insert! db
                         :dw.real_estate
                         [(util/multi-line-string
                           "dataset = ? and "
                           "ticker  = ? and "
                           "date    = ?     ")
                          dataset
                          ticker
                          date]
                         record))

(defn execute! [cxn data]
  (jdbc/with-db-transaction [txn cxn]
    (->> data
         (map prepare-row)
         flatten
         (map #(update-or-insert! txn %))
         doall)))

(defn -main [& args]
  (jdbc/with-db-connection [cxn (env :jdbc-db-uri)]
    (let [get-data (fn [{:keys [dataset
                                ticker]}]
                     (->> ticker
                          (map (fn [tkr]
                                 (-> (api/query-quandl! dataset
                                                        tkr
                                                        query-params)
                                     (assoc :dataset dataset :ticker tkr))))))
          data        (->> datasets
                           (map get-data)
                           flatten)]

      (execute! cxn data))))
