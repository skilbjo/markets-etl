(ns fixtures.economics)

(def source
  '({:dataset_data
     {:transform nil,
      :limit 20,
      :frequency "quarterly",
      :column_index nil,
      :end_date "2017-07-01",
      :start_date "2017-12-03",
      :column_names ["Date" "Value"],
      :order nil,
      :collapse nil,
      :data []},
     :dataset "FRED",
     :ticker "GDP"}
    {:dataset_data
     {:transform nil,
      :limit 20,
      :frequency "weekly",
      :column_index nil,
      :end_date "2017-11-27",
      :start_date "2017-12-03",
      :column_names ["Date" "Value"],
      :order nil,
      :collapse nil,
      :data []},
     :dataset "FRED",
     :ticker "M1"}
    {:dataset_data
     {:transform nil,
      :limit 20,
      :frequency "daily",
      :column_index nil,
      :end_date "2017-12-07",
      :start_date "2017-12-03",
      :column_names ["Date" "Value"],
      :order nil,
      :collapse nil,
      :data
      [["2017-12-07" 1.16]
       ["2017-12-06" 1.16]
       ["2017-12-05" 1.16]
       ["2017-12-04" 1.16]
       ["2017-12-03" 1.16]]},
     :dataset "FRED",
     :ticker "DFF"}
    {:dataset_data
     {:transform nil,
      :limit 20,
      :frequency "monthly",
      :column_index nil,
      :end_date "2017-11-01",
      :start_date "2017-12-03",
      :column_names ["Date" "Value"],
      :order nil,
      :collapse nil,
      :data []},
     :dataset "FRED",
     :ticker "UNRATE"}))

(def result
  '({:dataset "FRED",
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :ticker "DFF",
     :value 1.16M}
    {:dataset "FRED",
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :ticker "DFF",
     :value 1.16M}
    {:dataset "FRED",
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :ticker "DFF",
     :value 1.16M}
    {:dataset "FRED",
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :ticker "DFF",
     :value 1.16M}
    {:dataset "FRED",
     :date #inst "2017-12-03T00:00:00.000-00:00",
     :ticker "DFF",
     :value 1.16M}))
