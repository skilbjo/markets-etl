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

(defn date-time? [d] (or (string? d) (instance? org.joda.time.DateTime d)))

(def ^:private allowed {:collapse     #{"none" "daily" "weekly" "monthly" "quarterly" "annual"}
                        :transform    #{"none" "rdiff" "diff" "cumul" "normalize"}
                        :order        #{"asc" "desc"}
                        :rows         integer?
                        :limit        integer?
                        :column_index integer?
                        :start_date   date-time?
                        :end_date     date-time?})

(defn allowed? [m]
  (doseq [[k v] m]
    (println k " & " v)
    (println ((allowed k) v))
    ((allowed k) v))
  ;(println k)
  ;(println v))
  ;(let [k (keys m)]
    ;(println k)
    ;;(map #(allowed %)  k)
    ;(println ((allowed (first k))
              ;((first k) m)))
    ;((allowed (first k))
              ;((first k) m))))

    ;(map allowed k))

    ;(println (allowed k)))
    ;(println (str "allowed? " k))
    ;(allowed k))
  ;((allowed k) m))

(defn sequentialize [x]
  (if (sequential? x)
    x
    (vector x)))

(defn multi-line-string [& lines]
  (->> (map sequentialize lines)
       (map s/join)
       (s/join "\n")))
