(ns jobs.equities
  (:require [clj-time.coerce :as coerce]
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

(def stocks
  ["FB" "AMZN" "GOOG" "NVDA" "CY" "INTC" "TXN" "V" "SAP" "SQ" "PYPL" "BRK.B"
   "TSM"])

(def etfs
  ["VFH" "VEA" "VWO" "VHT" "VGT"])

(def mutual-funds
  ["VEMAX" "VEURX" "VEXPX" "VGWAX" "VITAX" "VIMAX" "VMRAX" "VPACX" "VGSLX"
   "VTIAX" "VTSAX" "VWINX" "VWENX" "VWNDX" "VMMXX" "VWIGX" "VINEX" "VMMSX"])

(def quandl
  '({:dataset "WIKI"
     :ticker ["FB" "AMZN" "GOOG" "NVDA" "CY" "INTC" "TXN" "V"]}))

(def intrinio
  (->> quandl
       (map #(assoc % :dataset "INTRINIO"))))

(def tiingo
  (list {:dataset "TIINGO"
         :ticker (->> (conj stocks etfs mutual-funds)
                      (remove #{"BRK.B"})
                      (conj ["BRK-B"])
                      flatten
                      (into []))}))

(def morningstar
  (list {:dataset "MSTAR"
         :ticker (->> (conj stocks etfs mutual-funds)
                      flatten
                      (into []))}))

(def query-params
  {:limit      500
   :start_date util/last-week
   :end_date   util/now})

(defmulti prepare-row :dataset)

(defmethod prepare-row "TIINGO" [{:keys [dataset
                                         ticker
                                         date
                                         open
                                         close
                                         low
                                         high
                                         volume
                                         splitfactor
                                         adjopen
                                         adjclose
                                         adjlow
                                         adjhigh
                                         adjvolume
                                         divcash]}]
  {:dataset     dataset
   :ticker      ticker
   :date        (coerce/to-sql-date date)
   :open        open
   :close       close
   :low         low
   :high        high
   :volume      volume
   :split_ratio splitfactor
   :adj_open    adjopen
   :adj_close   adjclose
   :adj_low     adjlow
   :adj_high    adjhigh
   :adj_volume  adjvolume
   :ex_dividend divcash})

(defmethod prepare-row "INTRINIO" [{:keys [dataset
                                           ticker
                                           data]}]
  (->> data
       (map #(dissoc % :adj_factor))    ;; no documentation from api on this
       (map #(update % :date coerce/to-sql-date))
       (map #(update % :open util/string->decimal))
       (map #(assoc %                   ;; intrinio and quandl data is the same
                    :dataset     "WIKI" ;; intrinio is a more reliable vendor
                    :ticker      ticker))))

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

  ;; this only applies to Morningstar inserts - other d'sets see next cond stmt
  (condp = dataset
    "MSTAR" (sql/query-or-insert! db
                                  :dw.equities_fact
                                  [(util/multi-line-string
                                    "select          " ; if richer attributes
                                    "  *             " ; have not been set by
                                    "from            " ; quandl
                                    "  dw.equities_fact "
                                    "where           "
                                    "ticker  = ? and "
                                    "date    = ?     ")
                                   ticker
                                   date]
                                  record)
    "WIKI" (sql/update-or-insert!' db
                                   :dw.equities_fact
                                   [(util/multi-line-string  ; update MSTAR record
                                     "ticker = ? and " ; but don't overwrite
                                     "date   = ? and " ; it's dataset to WIKI
                                     "dataset in ('MSTAR','WIKI') and "
                                     "(open  = ? or open is null)") ; <- this
                                    ticker             ; has to be a non-MSTAR
                                    date               ; field
                                    open]
                                   (-> record
                                       (dissoc :dataset))
                                   record)
    nil)
  ; the above command will correctly update the MSTAR record, but it will
  ; also unfortunately delete the WIKI record. Force the WIKI record.
  ; TODO refactor this and the above so tests pass but with less code here

  ; 2018-08-01 update: both Quandl *and* Morningstar API have gone down.
  ; Quandl WIKI dataset not likely to return, and Morningstar API is uncertain.
  ; TIINGO has EOD prices, and Intrinio has the same signature as Quandl
  (condp = dataset
    "TIINGO" (sql/update-or-insert! db
                                    :dw.equities_fact
                                    [(util/multi-line-string
                                      "dataset  = ? and "
                                      "ticker   = ? and "
                                      "date     = ? ")
                                     dataset
                                     ticker
                                     date]
                                    record)
    "WIKI" (sql/update-or-insert! db
                                  :dw.equities_fact
                                  [(util/multi-line-string
                                    "dataset  = ? and "
                                    "ticker   = ? and "
                                    "date     = ? ")
                                   dataset
                                   ticker
                                   date]
                                  record)
    "MSTAR" (sql/update-or-ignore! db
                                   :dw.equities_fact
                                   [(util/multi-line-string
                                     "dataset  = ? and "
                                     "ticker   = ? and "
                                     "date   = ? and "
                                     "(open is null)")
                                    dataset
                                    ticker
                                    date]
                                   record)))

(defn execute! [cxn data]
  (jdbc/with-db-transaction [txn cxn]
    (->> data
         (map prepare-row)
         flatten
         (map #(update-or-insert! txn %))
         doall)))

(defn -main [& args]
  (error/set-default-error-handler)

  (jdbc/with-db-connection [cxn (-> :jdbc-db-uri env)]
    (let [data        (->> (concat tiingo morningstar quandl #_intrinio)
                           (map #(api/get-data % query-params))
                           flatten)]
      (execute! cxn data)))

  (util/notify-healthchecks-io (env :healthchecks-io-api-key)))
