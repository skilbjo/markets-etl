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

(def internalize-identifier (comp string/lower-case util/dasherize))
(def internalize-map-identifier (comp keyword string/lower-case util/dasherize))

(defn get-dw-conn []
  (env :db-jdbc-uri))

(defn query [sql params]
  (with-open [conn (get-dw-conn)]
    (jdbc/query conn sql params)))

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

