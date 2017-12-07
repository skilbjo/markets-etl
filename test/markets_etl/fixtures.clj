(ns markets-etl.fixtures
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [environ.core :refer [env]]))

(def ^:dynamic *cxn*
  nil)

(defn with-database []
  (fn [f]
      (println (env :jdbc-db-uri))
      (println (env :test-jdbc-db-uri))

    #_(binding [*cxn* (env :test-jdbc-db-uri)]
      (println (env :jdbc-db-uri))
      (println (env :test-jdbc-db-uri))
      (->> "test/setup.sql"
           io/resource
           slurp
           (fn [x] (println x) x)
           (jdbc/execute! *cxn*))
      (println "testing some stuff!")
      (->> "drop schema dw cascade;"
           (jdbc/execute! *cxn*)))))
