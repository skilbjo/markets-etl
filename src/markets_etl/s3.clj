(ns markets-etl.s3
  (:require [amazonica.aws.s3 :as s3]
            [clojure.string :as string]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.java.shell :as shell]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]]
            [orca.core :as orca]))

; https://github.com/metasoarous/semantic-csv/blob/master/src/semantic_csv/impl/core.cljc#L24
(defn stringify-keyword [x]
  (cond
    (string? x)   x
    (keyword? x)  (->> x str (drop 1) (apply str))
    :else         (str x)))

; https://github.com/metasoarous/semantic-csv/blob/master/src/semantic_csv/transducers.cljc#L361
(defn vectorize*
  ([]
   (vectorize* {}))
  ([{:keys [header prepend-header format-header]
     :or {prepend-header true format-header stringify-keyword}}]
   (fn [rf]
     (let [hdr (volatile! header)
           prepend-hdr (volatile! prepend-header)]
       (fn
         ([] (rf))
         ([result] (rf result))
         ([result input]
          (when (empty? @hdr)
            (do (vreset! hdr (into [] (keys input)))))
          (if @prepend-hdr
            (do (vreset! prepend-hdr false)
                (rf
                 (if format-header
                   (rf result (mapv format-header @hdr))
                   (rf result @hdr))
                 (mapv (partial get input) @hdr)))
            (rf result (mapv (partial get input) @hdr)))))))))

; https://github.com/metasoarous/semantic-csv/blob/master/src/semantic_csv/core.cljc#L375
(defn vectorize
  ([rows]
   (vectorize {} rows))
  ([opts rows]
   (sequence (vectorize* opts) rows)))

(defn insert-to-athena [job date coll]
  (let [dataset        (-> coll first :dataset)
        job*           (-> job (string/replace #"_" {"_" "-"}))
        convert-to-csv (fn [job row]
                         (io/delete-file (str "/tmp/"
                                              job
                                              ".csv")
                                         true)
                         (with-open [writer (io/writer (str "/tmp/"
                                                            job
                                                            ".csv"))]
                           (csv/write-csv writer row)))
        orc-schema     (str "<id:string,date:timestamp>")
        convert-to-orc #(orca/file-encoder output orc-schema 1024 {:overwrite? true})
        gzip-csv       (fn [job]
                         (let [clean-cmd (str "if [[ -f /tmp/" job ".csv.gz ]]; then rm /tmp/" job ".csv.gz; fi")
                               cp-cmd    (str "cp")  ; TODO finish this
                               gzip-cmd  (str "gzip" " -9 " "/tmp/" job ".csv")  ; aws lambda gzip doesn't have -k flag
                               mv-cmd    (str "mv")] ; TODO finish this
                           (shell/sh "bash" "-c" clean-cmd)
                           (shell/sh "bash" "-c" gzip-cmd)))
        s3-put         (fn [job]
                         (let [s3-path (str "datalake/markets-etl/"
                                            job
                                            "/date="
                                            date
                                            "/"
                                            job
                                            ".csv.gz")]
                           (log/info "s3-path is:" s3-path)
                           (s3/put-object :bucket-name "skilbjo-data"
                                          :key         s3-path
                                          :metadata    {:server-side-encryption "AES256"}
                                          :file        (str "/tmp/"
                                                            job
                                                            ".csv.gz"))))]
    (->> coll                    ; lets have only one "date" column in athena
         (map #(dissoc % :date)) ; so "date" is the partition
         vectorize
         (convert-to-csv job*))

    (gzip-csv job*)

    (if (empty? (first coll))
      '()  ; this is a quoted empty list, ie exit early
      (s3-put job*))))
