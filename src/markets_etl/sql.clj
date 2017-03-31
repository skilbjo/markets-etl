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

(def query-dw jdbc/query)
(defn insert-dw-multi! [t-con table rows]
  (jdbc/insert-multi! t-con table rows))

(defn get-dw-conn []
  {:connection-uri (env :db-jdbc-uri)})

