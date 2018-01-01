(ns markets-etl.util-test
  (:require [clojure.test :refer :all]
            [markets-etl.util :as util])
  (:gen-class))

(deftest ^:parallel string->decimal-test
  (testing "does string->decimal turn a string into a decimal type?"
    (is (= (BigDecimal. "0.01")
           (util/string->decimal "0.01"))))

  (testing "passing it nil"
    (is (= nil
           (util/string->decimal nil)))))
