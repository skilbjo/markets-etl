(ns fixtures.economics)

(def quandl
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

(def fred-api
  '({:observation_start "2019-05-05",
     :realtime_start "2020-05-05",
     :realtime_end "2020-05-05",
     :observations
     [{:realtime_start "2020-05-05",
       :realtime_end "2020-05-05",
       :date "2019-04-01",
       :value "21340.267"}],
     :limit 1,
     :file_type "json",
     :offset 0,
     :order_by "observation_date",
     :output_type 1,
     :sort_order "asc",
     :ticker "GDP",
     :observation_end "2020-05-05",
     :count 3,
     :units "lin",
     :dataset "FRED-API"}
    {:observation_start "2019-05-05",
     :realtime_start "2020-05-05",
     :realtime_end "2020-05-05",
     :observations
     [{:realtime_start "2020-05-05",
       :realtime_end "2020-05-05",
       :date "2019-01-01",
       :value "19351.27"}],
     :limit 1,
     :file_type "json",
     :offset 0,
     :order_by "observation_date",
     :output_type 1,
     :sort_order "asc",
     :ticker "GNPCA",
     :observation_end "2020-05-05",
     :count 0,
     :units "lin",
     :dataset "FRED-API"}
    {:observation_start "2019-05-05",
     :realtime_start "2020-05-05",
     :realtime_end "2020-05-05",
     :observations
     [{:realtime_start "2020-05-05",
       :realtime_end "2020-05-05",
       :date "2019-05-01",
       :value "100"}],
     :limit 1,
     :file_type "json",
     :offset 0,
     :order_by "observation_date",
     :output_type 1,
     :sort_order "asc",
     :ticker "UMCSENT",
     :observation_end "2020-05-05",
     :count 10,
     :units "lin",
     :dataset "FRED-API"}
    {:observation_start "2019-05-05",
     :realtime_start "2020-05-05",
     :realtime_end "2020-05-05",
     :observations
     [{:realtime_start "2020-05-05",
       :realtime_end "2020-05-05",
       :date "2019-05-01",
       :value "3.6"}],
     :limit 1,
     :file_type "json",
     :offset 0,
     :order_by "observation_date",
     :output_type 1,
     :sort_order "asc",
     :ticker "UNRATE",
     :observation_end "2020-05-05",
     :count 10,
     :units "lin",
     :dataset "FRED-API"}))

(def result
  '({:dataset "FRED",
     :ticker "DFF",
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :value 1.16M}
    {:dataset "FRED",
     :ticker "DFF",
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :value 1.16M}
    {:dataset "FRED",
     :ticker "DFF",
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :value 1.16M}
    {:dataset "FRED",
     :ticker "DFF",
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :value 1.16M}
    {:dataset "FRED",
     :ticker "DFF",
     :date #inst "2017-12-03T00:00:00.000-00:00",
     :value 1.16M}
    {:dataset "FRED-API",
     :ticker "GDP",
     :date #inst "2019-04-01T00:00:00.000-00:00",
     :value 21340.27M}
    {:dataset "FRED-API",
     :ticker "GNPCA",
     :date #inst "2019-01-01T00:00:00.000-00:00",
     :value 19351.27M}
    {:dataset "FRED-API",
     :ticker "UMCSENT",
     :date #inst "2019-05-01T00:00:00.000-00:00",
     :value 100.00M}
    {:dataset "FRED-API",
     :ticker "UNRATE",
     :date #inst "2019-05-01T00:00:00.000-00:00",
     :value 3.60M}))
