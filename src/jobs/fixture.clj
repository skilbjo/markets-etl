(ns jobs.fixture
  (:require [clj-time.coerce :as coerce]
            [clojure.string :as string])
  (:gen-class))

(def fixture
'(({:dataset "wiki",
   :ticker "FB"
   :data
   {"dataset_data"
    {"collapse" nil,
     "end_date" "2017-01-05",
     "column_index" nil,
     "transform" nil,
     "limit" 2,
     "order" nil,
     "start_date" "2017-01-04",
     "column_names"
     ["Date"
      "Open"
      "High"
      "Low"
      "Close"
      "Volume"
      "Ex-Dividend"
      "Split Ratio"
      "Adj. Open"
      "Adj. High"
      "Adj. Low"
      "Adj. Close"
      "Adj. Volume"],
     "data"
     [["2017-01-05"
       118.86
       120.95
       118.3209
       120.67
       1.9361354E7
       0.0
       1.0
       118.86
       120.95
       118.3209
       120.67
       1.9361354E7]
      ["2017-01-04"
       117.55
       119.66
       117.29
       118.69
       1.9338497E7
       0.0
       1.0
       117.55
       119.66
       117.29
       118.69
       1.9338497E7]],
     "frequency" "daily"}}})))

