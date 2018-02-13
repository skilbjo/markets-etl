(ns markets-etl.error
  (:require [clojure.tools.logging :as log]))

(defn set-default-error-handler []
  (Thread/setDefaultUncaughtExceptionHandler
   (reify Thread$UncaughtExceptionHandler
     (uncaughtException [_ thread e]
       (log/error e
                  (str "Uncaught exception on " (.getName thread)))))))
