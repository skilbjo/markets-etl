(ns markets-etl.util
  (:require
   [clojure.pprint :as pprint]
   [clojure.string :as string]))

(defn date-time? [d] (or (string? d) (instance? org.joda.time.DateTime d)))
(def ^:private allowed {:collapse     #{"none" "daily" "weekly" "monthly" "quarterly" "annual"}
                        :transform    #{"none" "rdiff" "diff" "cumul" "normalize"}
                        :order        #{"asc" "desc"}
                        :rows         integer?
                        :limit        integer?
                        :column_index integer?
                        :start_date   date-time?
                        :end_date     date-time?})
(defn print-and-die [x]
  (pprint/pprint x)
  (System/exit 0))

(defn just-die [x]
  (System/exit 0))

(defn printit [x]
  (pprint/pprint x)
  x)

(defn dasherize [s]
  (string/replace s #"_" "-"))

(defn underscoreize [s]
  (string/replace s #"-" "_"))

(defn no-doterize [s]
  (string/replace s #"." ""))

(defn postgreserize [s]
  (printit s)
  (comp underscoreize no-doterize s))

(defn random-uuid []
  (str (java.util.UUID/randomUUID)))

(defn sleep [ms]
  (Thread/sleep ms))

(defn keywordize [s]
  (-> s
      (string/replace #"\s" "-")
      string/lower-case keyword))

(defn string->decimal [n]
  (try
    (BigDecimal. n)
    (catch NumberFormatException e
      n)
    (catch NullPointerException e
      n)))

(defn allowed? [m]
  (->> m
       first
       (map (fn [[k v]]
              ((allowed k) v)))))

(defn sequentialize [x]
  (if (sequential? x)
    x
    (vector x)))

(defn multi-line-string [& lines]
  (->> (map sequentialize lines)
       (map string/join)
       (string/join "\n")))
