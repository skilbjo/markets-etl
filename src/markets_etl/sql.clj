(ns markets-etl.sql
  (:require [clojure.string :as string]
            [clojure.java.jdbc :as jdbc]
            [environ.core :refer [env]]
            [markets-etl.util :as util])
  (:import [java.sql DriverManager]))

(clojure.lang.RT/loadClassForName "org.postgresql.Driver")

(defn update-or-insert! [db table where-clause data]
  (let [result (-> (jdbc/update! db table data where-clause)
                   first)]
    (if (zero? result)
      (jdbc/insert! db table data)
      result)))

(defn update-or-insert!' [db table where-clause update-data data]
  (let [result (-> (jdbc/update! db table update-data where-clause)
                   first)]
    (if (zero? result)
      (jdbc/insert! db table data)
      result)))

(defn update-or-ignore!  [db table where-clause data]
  (let [result (-> (jdbc/update! db table data where-clause)
                   first)]
    (if (zero? result)
      data
      result)))

(defn query-or-insert! [db table where-clause data]
  (let [result (jdbc/query db where-clause)]
    (if (empty? result)
      (jdbc/insert! db table data)
      result)))

(defn- prepare-statement
  [sql params]
  (loop [sql sql
         kvs (map identity params)]
    (if (empty? kvs)
      sql
      (let [[[k v] & others] kvs]
        (recur (string/replace sql (str k) (str (jdbc/sql-value v)))
               others)))))
