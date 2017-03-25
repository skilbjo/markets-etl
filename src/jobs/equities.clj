(ns jobs.equities
  (:require [clojure.string :as string]
            [clj-time.core :as time]
            [clj-time.coerce :as coerce]
            [clj-time.format :as format]
            [market-etl.util :as util]
            [market-etl.sql :as sql])
  (:gen-class))

(def now-utc (time/now))

(defn -main [& args]
  (let [get-athena-metadata (fn [table]
                              {:table table
                               :meta (sql/query-athena-metadata (sql/get-athena-conn)
                                                                "select * from :table limit 1"
                                                                {:table table})})
        make-sql            (fn [{:keys [table meta]}]
                              {:table table
                               :sql (generate-sql table meta)})
        get-stats           (fn [{:keys [table sql]}]
                              (->> (sql/query-athena (sql/get-athena-conn) sql)
                                   (map #(assoc %
                                                :stats_taken_at now-utc
                                                :schema_ (first (string/split table #"\."))
                                                :table_ (second (string/split table #"\."))))))
        make-kv-map         (fn [k v stat-type]
                              {:column_ (-> (name k)
                                            (string/split #"-" 2)
                                            second)
                               :stat_type stat-type :value (util/string->decimal v)})
        split-stats         (fn [{:keys [stats_taken_at schema_ table_] :as stats}]
                              (->> stats
                                   (map (fn [[k v]]
                                          (condp #(string/starts-with? %2 %1) (name k)
                                            nil)))
                                   (remove nil?)
                                   (map #(assoc %
                                                :stats_taken_at stats_taken_at
                                                :schema_ schema_
                                                :table_  table_
                                                :source_data_date (:source-data-date stats)))))]
    (->> (map get-athena-metadata dnb-source-tables)
         (map make-sql)
         (map get-stats)
         flatten
         (map split-stats)
         flatten
         (sql/insert-dw-multi! (sql/get-dw-conn) :status.athena_stats))))
