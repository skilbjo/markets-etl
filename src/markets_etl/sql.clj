(ns markets-etl.sql
  (:require
   [clojure.java.jdbc :as jdbc]
   [environ.core :refer [env]]
   [markets-etl.util :as util]
   [clj-time.jdbc]
   [clojure.string :as string])
  (:import
   [java.sql BatchUpdateException]
   [java.util Properties]
   [java.sql DriverManager Connection]))

(clojure.lang.RT/loadClassForName "org.postgresql.Driver")

(def internalize-identifier (comp string/lower-case util/dasherize))
(def internalize-map-identifier (comp keyword string/lower-case util/dasherize))

(def query-dw jdbc/query)
(def insert-dw-multi! jdbc/insert-multi!)

(defn get-dw-conn []
  {:connection-uri (env :dw-jdbc-uri)})

