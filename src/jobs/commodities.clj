(ns jobs.commodities
  (:require [clj-time.coerce :as coerce]
            [clj-time.core :as time]
            [clj-time.format :as format]
            [clojure.data.json :as json]
            [clojure.java.jdbc :as jdbc]
            [clojure.tools.cli :as cli]
            [clojure.string :as string]
            [clojure.set :only [rename-keys] :as clj-set]
            [environ.core :refer [env]]
            [markets-etl.api :as api]
            [markets-etl.error :as error]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def api-keys   ;; env vars are encrypted on lambda
  (delay        ;; defs evaluated at compile time; delay until runtime
   {:quandl-api-key        (-> :quandl-api-key env)}))

(def cli-options
  [["-d" "--date DATE" "Start date (month) (yyyy-mm-dd format) to start processing"
    :parse-fn #(format/parse %)
    :default  util/last-week]
   ["-h" "--help"]])

(def crypto
  (list {:dataset "LOCALBTC"
         :ticker ["USD"]}))

(def precious-metals
  (list {:dataset "PERTH"
         :ticker ["LONMETALS"]}))

(def gold
  (list {:dataset "LBMA"
         :ticker ["GOLD"]}))

(def oil
  (list {:dataset "OPEC"
         :ticker ["ORB"]}))

(def query-params
  {:limit      500
   :start_date util/last-week
   :end_date   util/now})

(defmulti prepare-row :dataset)

(defmethod prepare-row "PERTH" [{:keys [dataset
                                        ticker]
                                 {:keys [column_names
                                         data]} :dataset_data}]
  (when (seq data)
    (let [columns       (->> (-> column_names
                                 string/lower-case
                                 (string/replace #"\." "")
                                 (string/replace #"-" "_")
                                 json/read-str)
                             (map #(string/replace % #" " "_"))
                             (map keyword))
          data' (->> data
                     (map #(zipmap columns %))
                     (map #(update % :date coerce/to-sql-date)))

          gold (->> data'
                    (map #(assoc {}
                                 :date    (-> % :date)
                                 :open    (-> % :gold_am_fix util/string->decimal)
                                 :average (-> (util/average
                                               (list (-> % :gold_am_fix)
                                                     (-> % :gold_pm_fix)))
                                              util/string->decimal)
                                 :close   (-> % :gold_pm_fix util/string->decimal)
                                 :ticker  "GOLD")))
          silver (->> data'
                      (map #(assoc {}
                                   :date    (-> % :date)
                                   :close   (-> % :silver_fix util/string->decimal)
                                   :ticker "SILVER")))
          platinum (->> data'
                        (map #(assoc {}
                                     :date    (-> % :date)
                                     :open    (-> % :platinum_am_fix util/string->decimal)
                                     :average (-> (util/average
                                                   (list (-> % :platinum_am_fix)
                                                         (-> % :platinum_pm_fix)))
                                                  util/string->decimal)
                                     :close   (-> % :platinum_pm_fix util/string->decimal)
                                     :ticker "PLATINUM")))
          palladium (->> data'
                         (map #(assoc {}
                                      :date    (-> % :date)
                                      :open    (-> % :platinum_am_fix util/string->decimal)
                                      :average (-> (util/average
                                                    (list (-> % :platinum_am_fix)
                                                          (-> % :platinum_pm_fix)))
                                                   util/string->decimal)
                                      :close   (-> % :platinum_pm_fix util/string->decimal)
                                      :ticker "PALLADIUM")))
          data* (->> (concat gold silver platinum palladium)
                     (map #(assoc %
                                  :dataset dataset)))]
      data*)))

(defmethod prepare-row "LOCALBTC" [{:keys [dataset
                                           ticker]
                                    {:keys [column_names
                                            data]} :dataset_data}]
  (when (seq data)
    (let [columns       (->> (-> column_names
                                 string/lower-case
                                 (string/replace #"\." "")
                                 (string/replace #"\(" "")
                                 (string/replace #"\)" "")
                                 (string/replace #"-" "_")
                                 json/read-str)
                             (map #(string/replace % #" " "_"))
                             (map keyword))
          data' (->> data
                     (map #(zipmap columns %))
                     (map #(update % :date coerce/to-sql-date)))]
      (->> data'
           (map #(assoc {}
                        :date    (-> % :date)
                        :average (-> % :24h_average util/string->decimal)
                        :close   (-> % :last util/string->decimal)
                        :volume  (-> % :volume_btc util/string->decimal)
                        :dataset dataset
                        :ticker  (str "BTC" ticker)))))))

(defmethod prepare-row "LBMA" [{:keys [dataset
                                       ticker]
                                {:keys [column_names
                                        data]} :dataset_data}]
  (when (seq data)
    (let [columns       (->> (-> column_names
                                 string/lower-case
                                 (string/replace #"\." "")
                                 (string/replace #"\(" "")
                                 (string/replace #"\)" "")
                                 (string/replace #"-" "_")
                                 json/read-str)
                             (map #(string/replace % #" " "_"))
                             (map keyword))
          data' (->> data
                     (map #(zipmap columns %))
                     (map #(update % :date coerce/to-sql-date)))]
      (->> data'
           (map #(assoc {}
                        :date    (-> % :date)
                        :average (-> (util/average
                                      (list (-> % :usd_am)
                                            (-> % :usd_pm))) util/string->decimal)
                        :close   (-> % :usd_pm util/string->decimal)
                        :dataset dataset
                        :ticker  ticker))))))

(defmethod prepare-row "OPEC" [{:keys [dataset
                                       ticker]
                                {:keys [column_names
                                        data]} :dataset_data}]
  (when (seq data)
    (let [columns       (->> (-> column_names
                                 string/lower-case
                                 (string/replace #"\." "")
                                 (string/replace #"-" "_")
                                 json/read-str)
                             (map #(string/replace % #" " "_"))
                             (map keyword))]
      (->> data
           (map #(zipmap columns %))
           (map #(update % :date coerce/to-sql-date))
           (map #(clj-set/rename-keys % {:value :close}))
           (map #(assoc %
                        :dataset dataset
                        :ticker (str ticker "-OIL")))))))

(defn update-or-insert! [db {:keys [dataset
                                    ticker
                                    date] :as record}]
  (sql/update-or-insert! db
                         :dw.commodities_fact
                         [(util/multi-line-string
                           "dataset = ? and "
                           "ticker  = ? and "
                           "date    = ?     ")
                          dataset
                          ticker
                          date]
                         record))

(defn execute! [cxn data]
  (error/set-default-error-handler)
  (jdbc/with-db-transaction [txn cxn]
    (->> data
         (map prepare-row)
         flatten
         (remove nil?)
         (map #(update-or-insert! txn %))
         doall)))

(defn -main [& args]
  (error/set-default-error-handler)
  (jdbc/with-db-connection [cxn (-> :jdbc-db-uri env)]
    (let [{:keys [options summary errors]} (cli/parse-opts args cli-options)
          query-params*        (if args
                                 {:limit      (:limit query-params)
                                  :start_date (-> options
                                                  :date
                                                  time/first-day-of-the-month
                                                  util/joda-date->date-str)
                                  :end_date   (-> options
                                                  :date
                                                  util/joda-date->date-str)}
                                 query-params)
          data        (->> (concat crypto precious-metals gold oil)
                           (map #(api/get-data % @api-keys query-params*))
                           flatten)]

      (execute! cxn data))))
