(ns markets-etl.error
  (:require [clojure.tools.logging :as log])
  (:import  [clojure.lang IExceptionInfo]))

(defn set-default-error-handler []
  (Thread/setDefaultUncaughtExceptionHandler
   (reify Thread$UncaughtExceptionHandler
     (uncaughtException [_ thread e]
       (log/error e
                  (str "Uncaught exception on " (.getName thread)))
       (when (instance? IExceptionInfo e)
         (log/error (ex-data e)))
       (System/exit 1)))))
