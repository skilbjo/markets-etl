(ns fixtures.real-estate)

(def source
  '({:dataset_data
     {:transform nil,
      :limit 20,
      :frequency "monthly",
      :column_index nil,
      :end_date "2017-10-31",
      :start_date "2017-12-03",
      :column_names ["Date" "Value"],
      :order nil,
      :collapse nil,
      :data []},
     :dataset "ZILLOW",
     :ticker "Z94108_ZHVIAH"}))

(def result
  '())
