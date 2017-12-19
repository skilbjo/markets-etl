(ns markets-etl.util
  (:require [clj-time.core :as time]
            [clj-time.coerce :as coerce]
            [clj-time.format :as formatter]
            [clojure.pprint :as pprint]
            [clojure.string :as string]))

; -- dev -----------------------------------------------
(defn print-it [coll]
  (pprint/pprint coll)
  coll)

(defn print-and-die [coll]
  (pprint/pprint coll)
  (System/exit 0))

; -- time ----------------------------------------------
(def now (formatter/unparse (formatter/formatters :date)
                            (time/now)))

(def yesterday (time/yesterday))

(def last-week (-> 1 time/weeks time/ago))

(def last-month (-> 1 time/months time/ago))

(def last-quarter (-> 3 time/months time/ago))

(def last-year (-> 1 time/years time/ago))

; -- data types ----------------------------------------
(defn string->decimal [n]
  (try
    (BigDecimal. n)
    (catch NumberFormatException e
      n)
    (catch NullPointerException e
      n)))

; -- collections ---------------------------------------
(defn sequentialize [x]
  (if (sequential? x)
    x
    (vector x)))

(defn multi-line-string [& lines]
  (->> (map sequentialize lines)
       (map string/join)
       (string/join "\n")))
