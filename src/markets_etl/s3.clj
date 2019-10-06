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
(defn ^:private stringify-keyword [x]
  (cond
    (string? x)   x
    (keyword? x)  (->> x str (drop 1) (apply str))
    :else         (str x)))

; https://github.com/metasoarous/semantic-csv/blob/master/src/semantic_csv/transducers.cljc#L361
(defn ^:private vectorize*
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
(defn ^:private vectorize
  ([rows]
   (vectorize {} rows))
  ([opts rows]
   (sequence (vectorize* opts) rows)))

(defn ^:private create-csv [job file row]
  (io/delete-file file true)
  (with-open [writer (io/writer file)]
    (csv/write-csv writer row))
  row)

(defn ^:private gzip-csv [job csv-file]
  (let [csv-bak-file   (str csv-file ".bak")
        gzip-file      (str csv-file ".gz")
        clean-cmd (str "if [[ -f " gzip-file " ]]; then rm " gzip-file "; fi")
        cp-cmd    (str "cp " csv-file " " csv-bak-file)
        gzip-cmd  (str "gzip" " -9 " csv-file)  ; aws lambda gzip doesn't have -k flag
        mv-cmd    (str "mv " csv-bak-file " " csv-file)]
    (shell/sh "bash" "-c" clean-cmd)
    (shell/sh "bash" "-c" cp-cmd)
    (shell/sh "bash" "-c" gzip-cmd)
    (shell/sh "bash" "-c" mv-cmd)))

(defn ^:private create-orc [job file row]
  (let [schemas {:currency       (str "struct<dataset:string,ticker:string,currency:string,rate:double,high:double,low:double>")
                 :equities       (str "struct<open:double,adj_volume:double,adj_close:double,ticker:string,adj_low:double,ex_dividend:double,close:double,volume:double,high:double,high:double,split_ratio:double,low:double,adj_open:double,dataset:string>")
                 :economics      (str "struct<value:double,dataset:string,ticker:string>")
                 :interest-rates (str "struct<key:string,value:double,dataset:string,ticker:string>")
                 :real-estate    (str "struct<dataset:string,ticker:string,value:double,area_category:string,indicator_code:string,area:string>")}
        schema  (-> job keyword schemas)]
    (orca/write-rows file row schema {:overwrite? true})
    row))

(defn insert-to-athena [job date coll]
  (let [dataset        (-> coll first :dataset)
        job*           (-> job (string/replace #"_" {"_" "-"}))
        csv-file       (str "/tmp/" job ".csv")
        orc-file       (str "/tmp/" job ".orc")
        s3-put         (fn [job file file-type]
                         (let [s3-path (str "datalake/markets-etl/" job "/date=" date "/" job "." file-type)]
                           (log/info "s3-path is:" s3-path)
                           (s3/put-object :bucket-name "skilbjo-data"
                                          :key         s3-path
                                          :metadata    {:server-side-encryption "AES256"}
                                          :file        file)))]
    (->> coll                    ; lets have only one "date" column in athena
         (map #(dissoc % :date)) ; so "date" is the partition
         vectorize
         rest                    ; remove header
         (create-csv job* csv-file)
         (create-orc job* orc-file))

    (gzip-csv job* csv-file)

    (if (empty? (first coll))
      '()  ; this is a quoted empty list, ie exit early
      (s3-put job* orc-file "orc"))))
