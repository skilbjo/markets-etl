(ns markets-etl.util
  (:require
   [clojure.string :as s]))

(defn dasherize [s]
  (s/replace s #"_" "-"))

(defn underscoreize [s]
  (s/replace s #"-" "_"))

(defn random-uuid []
  (str (java.util.UUID/randomUUID)))

(defn get-property [p]
  (System/getProperty p))

(def temp-dir (get-property "java.io.tmpdir"))

(defn sleep [ms]
  (Thread/sleep ms))

(defn printit [x] ; This fails in circleCI tests but is helpful for development
  (clojure.pprint/pprint x)
  x)

(defn string->decimal [n]
  (try
    (BigDecimal. n)
    (catch NumberFormatException e
      n)
    (catch NullPointerException e
      n)))

(defn sequentialize [x]
  (if (sequential? x)
    x
    (vector x)))

(defn multi-line-string [& lines]
  (->> (map sequentialize lines)
       (map s/join)
       (s/join "\n")))
