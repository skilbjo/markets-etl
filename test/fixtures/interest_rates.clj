(ns fixtures.interest-rates)

(def source
  '({:dataset_data
     {:transform nil,
      :limit 20,
      :frequency "daily",
      :column_index nil,
      :end_date "2017-12-04",
      :start_date "2017-12-03",
      :column_names ["Date" "Value"],
      :order nil,
      :collapse nil,
      :data [["2017-12-04" 1.16] ["2017-12-03" 1.16]]},
     :dataset "FED",
     :ticker "RIFSPFF_N_D"}
    {:dataset_data
     {:transform nil,
      :limit 20,
      :frequency "daily",
      :column_index nil,
      :end_date "2017-12-08",
      :start_date "2017-12-03",
      :column_names
      ["Date"
       "1 MO"
       "3 MO"
       "6 MO"
       "1 YR"
       "2 YR"
       "3 YR"
       "5 YR"
       "7 YR"
       "10 YR"
       "20 YR"
       "30 YR"],
      :order nil,
      :collapse nil,
      :data
      [["2017-12-08"
        1.14
        1.28
        1.45
        1.65
        1.8
        1.92
        2.14
        2.29
        2.38
        2.59
        2.77]
       ["2017-12-07"
        1.16
        1.29
        1.47
        1.67
        1.8
        1.92
        2.14
        2.29
        2.37
        2.58
        2.76]
       ["2017-12-06"
        1.18
        1.3
        1.48
        1.68
        1.78
        1.92
        2.11
        2.25
        2.33
        2.53
        2.71]
       ["2017-12-05"
        1.21
        1.3
        1.48
        1.64
        1.83
        1.94
        2.15
        2.28
        2.36
        2.55
        2.73]
       ["2017-12-04"
        1.16
        1.29
        1.45
        1.66
        1.8
        1.93
        2.15
        2.29
        2.37
        2.58
        2.77]]},
     :dataset "USTREASURY",
     :ticker "YIELD"}
    {:dataset_data
     {:transform nil,
      :limit 20,
      :frequency "daily",
      :column_index nil,
      :end_date "2017-12-07",
      :start_date "2017-12-03",
      :column_names
      ["Date"
       "LT Composite > 10 Yrs"
       "Treasury 20-Yr CMT"
       "Extrapolation Factor"],
      :order nil,
      :collapse nil,
      :data
      [["2017-12-07" 2.66 2.58 nil]
       ["2017-12-06" 2.61 2.53 nil]
       ["2017-12-05" 2.63 2.55 nil]
       ["2017-12-04" 2.66 2.58 nil]]},
     :dataset "USTREASURY",
     :ticker "LONGTERMRATES"}))

(def result
  '({:dataset "FED",
     :date #inst "2017-12-03T08:00:00.000-00:00",
     :key "value",
     :ticker "RIFSPFF_N_D",
     :value 1.16M}
    {:dataset "FED",
     :date #inst "2017-12-02T08:00:00.000-00:00",
     :key "value",
     :ticker "RIFSPFF_N_D",
     :value 1.16M}
    {:dataset "USTREASURY",
     :date #inst "2017-12-07T08:00:00.000-00:00",
     :key "1_mo",
     :ticker "YIELD",
     :value 1.14M}
    {:dataset "USTREASURY",
     :date #inst "2017-12-06T08:00:00.000-00:00",
     :key "1_mo",
     :ticker "YIELD",
     :value 1.16M}
    {:dataset "USTREASURY",
     :date #inst "2017-12-05T08:00:00.000-00:00",
     :key "1_mo",
     :ticker "YIELD",
     :value 1.18M}
    {:dataset "USTREASURY",
     :date #inst "2017-12-04T08:00:00.000-00:00",
     :key "1_mo",
     :ticker "YIELD",
     :value 1.21M}
    {:dataset "USTREASURY",
     :date #inst "2017-12-03T08:00:00.000-00:00",
     :key "1_mo",
     :ticker "YIELD",
     :value 1.16M}
    {:dataset "USTREASURY",
     :date #inst "2017-12-06T08:00:00.000-00:00",
     :key "lt_composite_>_10_yrs",
     :ticker "LONGTERMRATES",
     :value 2.66M}
    {:dataset "USTREASURY",
     :date #inst "2017-12-05T08:00:00.000-00:00",
     :key "lt_composite_>_10_yrs",
     :ticker "LONGTERMRATES",
     :value 2.61M}
    {:dataset "USTREASURY",
     :date #inst "2017-12-04T08:00:00.000-00:00",
     :key "lt_composite_>_10_yrs",
     :ticker "LONGTERMRATES",
     :value 2.63M}
    {:dataset "USTREASURY",
     :date #inst "2017-12-03T08:00:00.000-00:00",
     :key "lt_composite_>_10_yrs",
     :ticker "LONGTERMRATES",
     :value 2.66M}))
