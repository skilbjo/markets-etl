(ns fixtures.currency)

(def alpha-vantage
  '({:meta_data
     {:1._information "Forex Daily Prices (open, high, low, close)",
      :2._from_symbol "EUR",
      :3._to_symbol "USD",
      :4._output_size "Compact",
      :5._last_refreshed "2018-11-01 17:25:00",
      :6._time_zone "GMT+8"},
     :time_series_fx_daily {:2018-09-04 {:1._open "1.1617",
                                         :2._high "1.1618",
                                         :3._low "1.1529",
                                         :4._close "1.1583"},
                            :2018-07-25 {:1._open "1.1682",
                                         :2._high "1.1739",
                                         :3._low "1.1663",
                                         :4._close "1.1731"},
                            :2018-10-02 {:1._open "1.1576",
                                         :2._high "1.1580",
                                         :3._low "1.1504",
                                         :4._close "1.1550"},
                            :2018-07-26 {:1._open "1.1731",
                                         :2._high "1.1744",
                                         :3._low "1.1636",
                                         :4._close "1.1641"}}
     :dataset "ALPHA-VANTAGE"
     :ticker  "EURUSD"}))

(def quandl
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
  '({:dataset "ALPHA-VANTAGE",
     :ticker "EURUSD",
     :currency "EUR",
     :date #inst "2018-09-04T00:00:00.000-00:00",
     :rate 1.15830000000000M,
     :high 1.16180000000000M,
     :low 1.15290000000000M}
    {:dataset "ALPHA-VANTAGE",
     :ticker "EURUSD",
     :currency "EUR",
     :date #inst "2018-07-25T00:00:00.000-00:00",
     :rate 1.17310000000000M,
     :high 1.17390000000000M,
     :low 1.16630000000000M}
    {:dataset "ALPHA-VANTAGE",
     :ticker "EURUSD",
     :currency "EUR",
     :date #inst "2018-10-02T00:00:00.000-00:00",
     :rate 1.15500000000000M,
     :high 1.15800000000000M,
     :low 1.15040000000000M}
    {:dataset "ALPHA-VANTAGE",
     :ticker "EURUSD",
     :currency "EUR",
     :date #inst "2018-07-26T00:00:00.000-00:00",
     :rate 1.16410000000000M,
     :high 1.17440000000000M,
     :low 1.16360000000000M}
    {:dataset "CURRFX",
     :ticker "EURUSD",
     :currency "EUR",
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :rate 1.18042850494380M,
     :high 1.18091630935670M,
     :low 1.17771756649020M}
    {:dataset "CURRFX",
     :ticker "EURUSD",
     :currency "EUR",
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :rate 1.18329191207890M,
     :high 1.18483412265780M,
     :low 1.17813384532930M}
    {:dataset "CURRFX",
     :ticker "EURUSD",
     :currency "EUR",
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :rate 1.18677461147310M,
     :high 1.18793070316310M,
     :low 1.18088853359220M}
    {:dataset "CURRFX",
     :ticker "EURUSD",
     :currency "EUR",
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :rate 1.18598639965060M,
     :high 1.18993782997130M,
     :low 1.18312382698060M}
    {:dataset "CURRFX",
     :ticker "GBPUSD",
     :currency "GBP",
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :rate 1.33922588825230M,
     :high 1.34311127662660M,
     :low 1.33233857154850M}
    {:dataset "CURRFX",
     :ticker "GBPUSD",
     :currency "GBP",
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :rate 1.34152567386630M,
     :high 1.34372484683990M,
     :low 1.33605885505680M}
    {:dataset "CURRFX",
     :ticker "GBPUSD",
     :currency "GBP",
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :rate 1.34752726554870M,
     :high 1.34807229042050M,
     :low 1.33761370182040M}
    {:dataset "CURRFX",
     :ticker "GBPUSD",
     :currency "GBP",
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :rate 1.34940028190610M,
     :high 1.35385787487030M,
     :low 1.34224593639370M}))
