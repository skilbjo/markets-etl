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

(def fixture-multi
  '(({:dataset "wiki",
      :ticker "FB",
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
        "frequency" "daily"}}}
     {:dataset "wiki",
      :ticker "AMZN",
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
          761.55
          782.3999
          760.2557
          780.45
          5830068.0
          0.0
          1.0
          761.55
          782.3999
          760.2557
          780.45
          5830068.0]
         ["2017-01-04"
          758.39
          759.68
          754.2
          757.18
          2510526.0
          0.0
          1.0
          758.39
          759.68
          754.2
          757.18
          2510526.0]],
        "frequency" "daily"}}}
     {:dataset "wiki",
      :ticker "GOOG",
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
          786.08
          794.48
          785.02
          794.02
          1297561.0
          0.0
          1.0
          786.08
          794.48
          785.02
          794.02
          1297561.0]
         ["2017-01-04"
          788.36
          791.34
          783.16
          786.9
          1060648.0
          0.0
          1.0
          788.36
          791.34
          783.16
          786.9
          1060648.0]],
        "frequency" "daily"}}})))

