(ns markets-etl.util
  (:require
   [clj-time.core :as time]
   [clj-time.coerce :as coerce]
   [clojure.pprint :as pprint]
   [clojure.string :as string]))

; -- dev -----------------------------------------------
(defn print-and-die [x]
  (pprint/pprint x)
  (System/exit 0))

(defn just-die [x]
  (System/exit 0))

(defn printit [x]
  (pprint/pprint x)
  x)

(defn sleep [ms]
  (Thread/sleep ms))

(defn random-uuid []
  (str (java.util.UUID/randomUUID)))

; -- time ----------------------------------------------
(def now (time/now))

(def yesterday (time/yesterday))

(def last-week (-> 1 time/weeks time/ago))

(def last-year (-> 1 time/years time/ago))

; -- string --------------------------------------------
(defn dasherize [s]
  (string/replace s #"_" "-"))

(defn underscoreize [s]
  (string/replace s #"-" "_"))

(defn no-doterize [s]
  (string/replace s #"\." ""))

(defn no-parens [s]
  (-> s
      (string/replace #"\(" "")
      (string/replace #"\)" "")))

(defn keywordize [s]
  (-> s
      (string/replace #"\s" "-")
      string/lower-case keyword))

(defn postgreserize [s]
  (-> s
      (string/replace #":" "")  ; remove keyword
      (underscoreize)           ; clojure - to postgres _
      (no-parens)               ; remove ()'s
      (no-doterize)             ; remove .'s
      (keywordize)))

; -- data types ----------------------------------------
(def to-date coerce/to-sql-date)

(defn string->decimal [n]
  (try
    (BigDecimal. n)
    (catch NumberFormatException e
      n)
    (catch NullPointerException e
      n)))

(defn date-me [k v]
  (condp #(string/starts-with? %2 %1) (name k)
    "date"    (coerce/to-sql-date v)
    v))

(defn date-time? [d]
  (or (string? d)
      (instance? org.joda.time.DateTime d)))

(def ^:private allowed
  {:collapse     #{"none" "daily" "weekly" "monthly" "quarterly" "annual"}
   :transform    #{"none" "rdiff" "diff" "cumul" "normalize"}
   :order        #{"asc" "desc"}
   :rows         integer?
   :limit        integer?
   :column_index integer?
   :start_date   date-time?
   :end_date     date-time?})

(defn allowed? [m]
  (->> m
       first
       (map (fn [[k v]]
              ((allowed k) v)))))

; -- collections ---------------------------------------
(defn map-f-k [f coll]
  (reduce-kv (fn [m k v]
    (assoc m (f k) v)) {} coll))

(defn map-f-v [f coll]
  (reduce-kv (fn [m k v]
    (assoc m k (f v))) {} coll))

(defn map-fkv-v [f coll]
  (reduce-kv (fn [m k v]
    (assoc m k (f k v))) {} coll))

(defn map-seq-f-k [f coll]
  (map #(map-f-k f %) coll))

(defn map-seq-f-v [f coll]
  (map #(map-f-v f %) coll))

(defn map-seq-fkv-v [f coll]
  (map #(map-fkv-v f %) coll))

(defn sequentialize [x]
  (if (sequential? x)
    x
    (vector x)))

(defn multi-line-string [& lines]
  (->> (map sequentialize lines)
       (map string/join)
       (string/join "\n")))
