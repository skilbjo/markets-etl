(ns fixtures.fixtures
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [environ.core :refer [env]]))

(def ^:dynamic *cxn*
  nil)

(defn with-database []
  (fn [f]
    (binding [*cxn* (env :test-jdbc-db-uri)]
      (->> "test/ddl.sql"
           io/resource
           slurp
           (jdbc/execute! *cxn*))
      (f)
      (->> "drop schema dw cascade;"
           (jdbc/execute! *cxn*)))))
