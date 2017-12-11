(ns fixtures.real-estate)

(def source
  '({:dataset_data
     {:transform nil,
      :limit 10,
      :frequency "monthly",
      :column_index nil,
      :end_date "2017-10-31",
      :start_date "2017-09-11",
      :column_names ["Date" "Value"],
      :order nil,
      :collapse nil,
      :data [["2017-10-31" 1015800.0] ["2017-09-30" 1012600.0]]},
     :dataset "ZILLOW",
     :ticker "Z94108_ZHVIAH"}))

(def result
  '({:area "94108",
     :area_category "Z",
     :dataset "ZILLOW",
     :date #inst "2017-10-30T07:00:00.000-00:00",
     :indicator_code "ZHVIAH",
     :ticker "Z94108_ZHVIAH",
     :value 1015800.00M}
    {:area "94108",
     :area_category "Z",
     :dataset "ZILLOW",
     :date #inst "2017-09-29T07:00:00.000-00:00",
     :indicator_code "ZHVIAH",
     :ticker "Z94108_ZHVIAH",
     :value 1012600.00M}))
