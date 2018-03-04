(ns fixtures.currency)

(def source
  '({:dataset_data
     {:transform nil,
      :limit 200,
      :frequency "daily",
      :column_index nil,
      :end_date "2017-12-07",
      :start_date "2017-12-03",
      :column_names ["Date" "Rate" "High (est)" "Low (est)"],
      :order nil,
      :collapse nil,
      :data
      [["2017-12-07" 1.1804285049438 1.1809163093567 1.1777175664902]
       ["2017-12-06" 1.1832919120789 1.1848341226578 1.1781338453293]
       ["2017-12-05" 1.1867746114731 1.1879307031631 1.1808885335922]
       ["2017-12-04" 1.1859863996506 1.1899378299713 1.1831238269806]]},
     :dataset "CURRFX",
     :ticker "EURUSD"}
    {:dataset_data
     {:transform nil,
      :limit 200,
      :frequency "daily",
      :column_index nil,
      :end_date "2017-12-07",
      :start_date "2017-12-03",
      :column_names ["Date" "Rate" "High (est)" "Low (est)"],
      :order nil,
      :collapse nil,
      :data
      [["2017-12-07" 1.3392258882523 1.3431112766266 1.3323385715485]
       ["2017-12-06" 1.3415256738663 1.3437248468399 1.3360588550568]
       ["2017-12-05" 1.3475272655487 1.3480722904205 1.3376137018204]
       ["2017-12-04" 1.3494002819061 1.3538578748703 1.3422459363937]]},
     :dataset "CURRFX",
     :ticker "GBPUSD"}))

(def result
  '({:currency "EUR",
     :dataset "CURRFX",
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :high_est 1.18091630935670M,
     :low_est 1.17771756649020M,
     :rate 1.18042850494380M,
     :ticker "EURUSD"}
    {:currency "EUR",
     :dataset "CURRFX",
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :high_est 1.18483412265780M,
     :low_est 1.17813384532930M,
     :rate 1.18329191207890M,
     :ticker "EURUSD"}
    {:currency "EUR",
     :dataset "CURRFX",
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :high_est 1.18793070316310M,
     :low_est 1.18088853359220M,
     :rate 1.18677461147310M,
     :ticker "EURUSD"}
    {:currency "EUR",
     :dataset "CURRFX",
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :high_est 1.18993782997130M,
     :low_est 1.18312382698060M,
     :rate 1.18598639965060M,
     :ticker "EURUSD"}
    {:currency "GBP",
     :dataset "CURRFX",
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :high_est 1.34311127662660M,
     :low_est 1.33233857154850M,
     :rate 1.33922588825230M,
     :ticker "GBPUSD"}
    {:currency "GBP",
     :dataset "CURRFX",
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :high_est 1.34372484683990M,
     :low_est 1.33605885505680M,
     :rate 1.34152567386630M,
     :ticker "GBPUSD"}
    {:currency "GBP",
     :dataset "CURRFX",
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :high_est 1.34807229042050M,
     :low_est 1.33761370182040M,
     :rate 1.34752726554870M,
     :ticker "GBPUSD"}
    {:currency "GBP",
     :dataset "CURRFX",
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :high_est 1.35385787487030M,
     :low_est 1.34224593639370M,
     :rate 1.34940028190610M,
     :ticker "GBPUSD"}))
