(ns markets-etl.sql
  (:require [clojure.string :as string]
            [clojure.java.jdbc :as jdbc]
            [environ.core :refer [env]]
            [markets-etl.util :as util]
            [clj-time.jdbc])
  (:import [java.sql BatchUpdateException]
           [java.util Properties]
           [java.sql DriverManager Connection]))

(clojure.lang.RT/loadClassForName "org.postgresql.Driver")

(defn get-dw-conn []
  (env :db-jdbc-uri))

(defn get-custom-dw-conn []
  (DriverManager/getConnection
   (env :db-jdbc-uri)))

(defn- prepare-statement
  [sql params]
  (loop [sql sql
         kvs (map identity params)]
    (if (empty? kvs)
      sql
      (let [[[k v] & others] kvs]
        (recur (string/replace sql (str k) (str (jdbc/sql-value v)))
               others)))))

(defn query
  ([sql]
   (query sql {}))
  ([sql params]
   (with-open [conn (get-custom-dw-conn)]
     (let [sql     (-> sql
                       (string/replace #";" "")
                       (string/replace #"--" "")
                       (prepare-statement params))
           results (-> conn
                       (.createStatement)
                       (.executeQuery sql))]
       (jdbc/metadata-result results)))))

(defn insert-multi! [table data]
  (jdbc/with-db-connection [conn (get-dw-conn)]
    (jdbc/insert-multi! conn table data)))

(defn insert! [table data]
  (jdbc/with-db-connection [conn (get-dw-conn)]
    (jdbc/insert! conn table data)))

(defn update-or-insert! [table where-clause data]
  (jdbc/with-db-connection [conn (get-dw-conn)]
    (jdbc/with-db-transaction [conn conn]
      (let [result (jdbc/update! conn table data where-clause)]
        (if (zero? (first result))
          (insert! table data)
          result)))))

