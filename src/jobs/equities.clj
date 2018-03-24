(ns jobs.equities
  (:require [clj-time.coerce :as coerce]
            [clojure.core.reducers :as r]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [clojure.java.jdbc :as jdbc]
            [clojure.string :as string]
            [environ.core :refer [env]]
            [markets-etl.api :as api]
            [markets-etl.error :as error]
            [markets-etl.sql :as sql]
            [markets-etl.util :as util])
  (:gen-class))

(def datasets
  '({:dataset "WIKI"
     :ticker ["FB" "AMZN" "GOOG" "NVDA" "CY" "INTC" "TXN" "V"]}))

(def morningstar
  '({:dataset "MSTAR"
     :ticker ["BRK.B" "TSM" "VEMAX" "VEURX" "VEXPX" "VGWAX" "VITAX" "VIMAX"
              "VMRAX" "VPACX" "VGSLX" "VTIAX" "VTSAX" "VWINX" "VWENX" "VWNDX"
              "VFH" "VEA" "VWO" "VHT" "VGT"
              "FB" "AMZN" "GOOG" "NVDA" "CY" "INTC" "TXN" "V"]}))

(def query-params
  {:limit      500
   :start_date util/last-week
   :end_date   util/now})

(defmulti prepare-row :dataset)

(defmethod prepare-row "MSTAR" [{:keys [dataset
                                        ticker
                                        pricedatalist]}]
  (let [pricedatalist' (first pricedatalist)
        dates          (->> pricedatalist'
                            :dateindexs
                            (map util/excel-date-epoch->joda-date)
                            (map #(assoc {} :date %)))
        prices         (->> pricedatalist'
                            :datapoints
                            (map first)
                            (map util/string->decimal))]
    (->> prices
         (map #(assoc {} :close %))
         (map list dates)
         (map #(merge (first %) (second %)))
         (map #(update % :date coerce/to-sql-date))
         (map #(assoc %
                      :dataset     dataset
                      :ticker      ticker
                      :open        nil
                      :low         nil
                      :high        nil
                      :volume      nil
                      :split_ratio nil
                      :adj_open    nil
                      :adj_close   nil
                      :adj_low     nil
                      :adj_high    nil
                      :adj_volume  nil
                      :ex_dividend nil)))))

(defmethod prepare-row :default [{:keys [dataset
                                         ticker]
                                  {:keys [column_names
                                          data]} :dataset_data}]
  (let [columns       (->> (-> column_names
                               string/lower-case
                               (string/replace #"\." "")
                               (string/replace #"-" "_")
                               json/read-str)
                           (map #(string/replace % #" " "_"))
                           (map keyword))]
    (->> data
         (map #(zipmap columns %))
         (map #(update % :date coerce/to-sql-date)) ; needed as Quandl returns
         (map #(update % :open (fn [v] (-> v        ; prices more than 3 decimal
                                           java.math.BigDecimal. ; places out
                                           (.setScale 2 BigDecimal/ROUND_HALF_UP)))))
         (map #(assoc % :dataset dataset :ticker ticker)))))

(defn update-or-insert! [db {:keys [dataset
                                    ticker
                                    date
                                    open
                                    low
                                    high
                                    volume
                                    split_ratio
                                    adj_open
                                    adj_close
                                    adj_low
                                    adj_high
                                    adj_volume
                                    ex_dividend] :as record}]
  ; morningstar data is available real time; although quandl data is much
  ; richer in attributes (volume, opening balance, min, max). write morningstar
  ; data first, but then go update it with quandl data
  (condp = dataset
    "MSTAR" (sql/query-or-insert! db
                                  :dw.equities
                                  [(util/multi-line-string
                                    "select          " ; if richer attributes
                                    "  *             " ; have not been set by
                                    "from            " ; quandl
                                    "  dw.equities   "
                                    "where           "
                                    "ticker  = ? and "
                                    "date    = ?     ")
                                   ticker
                                   date]
                                  record)
    "WIKI" (sql/update-or-insert!' db
                                   :dw.equities
                                   [(util/multi-line-string  ; update MSTAR record
                                     "ticker = ? and " ; but don't overwrite
                                     "date   = ? and " ; it's dataset to WIKI
                                     "(open  = ? or open is null)") ; <- this
                                    ticker             ; has to be a non-MSTAR
                                    date               ; field
                                    open]
                                   (-> record
                                       (dissoc :dataset))
                                   record))
  ; the above command will correctly update the MSTAR record, but it will
  ; also unfortunately delete the WIKI record. Force the WIKI record.
  ; TODO refactor this and the above so tests pass but with less code here
  (condp = dataset
    "WIKI" (sql/update-or-insert! db
                                  :dw.equities
                                  [(util/multi-line-string
                                    "dataset  = ? and "
                                    "ticker   = ? and "
                                    "date     = ? ")
                                   dataset
                                   ticker
                                   date]
                                  record)
    "MSTAR" nil))

(defn execute! [cxn data]
  (jdbc/with-db-transaction [txn cxn]
    (->> data
         (map prepare-row)
         flatten
         (map #(update-or-insert! txn %))
         doall)))

(defn execute!' [cxn data]
  (jdbc/with-db-transaction [txn cxn]
    (->> data
         (r/map prepare-row)
         (into '())
         flatten
         (map #(update-or-insert! txn %))
         doall)))

(defn -main [& args]
  (error/set-default-error-handler)
  (jdbc/with-db-connection [cxn (-> :jdbc-db-uri env)]
    (let [data        (->> (concat morningstar datasets)
                           (map #(api/get-data % query-params))
                           flatten)]
      (execute! cxn data))))
