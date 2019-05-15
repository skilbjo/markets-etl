(ns s3.aws-lambda
  (:require [clj-time.core :as time]
            [clj-time.format :as format]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [clojure.tools.cli :as cli]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]]
            [s3.currency :as currency]
            [s3.economics :as economics]
            [s3.equities :as equities]
            [s3.interest-rates :as interest-rates]
            [s3.real-estate :as real-estate]
            [markets-etl.util :as util])
  (:gen-class
   :name jobs.aws-lambda
   :implements [com.amazonaws.services.lambda.runtime.RequestStreamHandler]))

(def cli-options
  [["-d" "--date DATE" "Start date (month) (yyyy-mm-dd format) to start processing"
    :default  util/last-week]
   ["-h" "--help"]])

(defn -main [& args]
  (let [{:keys [options summary errors]} (cli/parse-opts args cli-options)
        date                             (-> options :date)]
    (log/info "Starting jobs... ")
    (currency/-main "-d" date)
    (equities/-main "-d" date)
    #_(economics/-main      "-d" date)
    #_(real-estate/-main    "-d" date)
    #_(interest-rates/-main "-d" date)
    (log/info "Notifying healthchecks.io ... ")
    (util/notify-healthchecks-io (-> :healthchecks-io-api-key env))
    (log/info "Finished!")))

(defn main [& args]
  (log/info "Starting jobs... ")

  (log/info "Currency... ")
  (currency/-main)

  (log/info "Economics... ")
  (economics/-main)

  (log/info "Equities... ")
  (equities/-main)

  (log/info "Interest rates... ")
  (interest-rates/-main)

  (log/info "Real estate... ")
  (real-estate/-main)

  (log/info "Finished!")
  (log/info "Notifying healthchecks.io ... "))

(defn -handleRequest [_ event _ context]
  (let [event' (-> event
                   io/reader
                   (json/read :key-fn keyword))]
    (main)))
