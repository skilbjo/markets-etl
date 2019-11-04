(ns markets-etl.util
  (:require [clj-http.client :as http]
            [clj-time.coerce :as coerce]
            [clj-time.core :as time]
            [clj-time.format :as formatter]
            [clojure.pprint :as pprint]
            [clojure.string :as string])
  (:import [com.amazonaws.services.kms AWSKMS AWSKMSClientBuilder]
           [com.amazonaws.services.kms.model DecryptRequest]
           [java.util Base64]
           (java.nio ByteBuffer)
           (java.nio.charset Charset)))

; -- dev -----------------------------------------------
(defn print-it [coll]
  (pprint/pprint coll)
  coll)

(defn print-and-die [coll]
  (pprint/pprint coll)
  (System/exit 0))

; -- time ----------------------------------------------
(defn joda-date->date-str [d]
  (formatter/unparse (formatter/formatters :date)
                     d))

(def now (-> (time/now)
             joda-date->date-str))

(def tomorrow (-> (time/now)
                  (time/plus (-> 1 time/days))
                  joda-date->date-str))

(def yesterday (-> (time/yesterday)
                   joda-date->date-str))

(def two-days-ago (-> 2 time/days time/ago joda-date->date-str))

(def three-days-ago (-> 3 time/days time/ago joda-date->date-str))

(def four-days-ago (-> 4 time/days time/ago joda-date->date-str))

(def last-week (-> 1 time/weeks time/ago joda-date->date-str))

(def two-weeks-ago (-> 2 time/weeks time/ago joda-date->date-str))

(def last-month (-> 1 time/months time/ago joda-date->date-str))

(def last-quarter (-> 3 time/months time/ago joda-date->date-str))

(def last-year (-> 1 time/years time/ago joda-date->date-str))

(def two-years-ago (-> 2 time/years time/ago joda-date->date-str))

(def five-years-ago (-> 5 time/years time/ago joda-date->date-str))

; -- data types ----------------------------------------
(defn string->decimal [n]
  (when n
    (-> n
        java.math.BigDecimal.
        (.setScale 4 BigDecimal/ROUND_HALF_UP))))

(defn excel-date-epoch->joda-date [n]
  (let [_excel_epoch_start  (time/date-time 1899 12 30)]
    (-> (->> n
             time/days
             (time/plus _excel_epoch_start))  ; this is how excel date conversions work
        (time/plus (-> 2 time/days))))) ; morningstar's api is not exactly perfect though

(defn space->underscore [s]
  (string/replace s #" " "_"))

(defn remove-special-characters [s]
  (-> s
      (string/replace #"\(" "")
      (string/replace #"\)" "")))

; -- collections ---------------------------------------
(defn sequentialize [x]
  (if (sequential? x)
    x
    (vector x)))

(defn multi-line-string [& lines]
  (->> (map sequentialize lines)
       (map string/join)
       (string/join "\n")))

; -- alerts --------------------------------------------
(defn notify-healthchecks-io [api-key]
  (http/get (str "https://hchk.io/"
                 api-key)))

; -- aws -----------------------------------------------
(defn decrypt [ciphertext]
  (let [decoder (Base64/getDecoder)
        decoded-text (.decode decoder ciphertext)
        kms-client (AWSKMSClientBuilder/defaultClient)
        decode-request (doto (DecryptRequest.)
                         (.withCiphertextBlob (ByteBuffer/wrap decoded-text)))
        decode-response (.decrypt kms-client decode-request)]
    (.toString (.decode (Charset/forName "UTF-8") (.getPlaintext decode-response)))))
