(ns fixtures.equities)

(def alpha-vantage
  '({:meta_data
     {:1._information "Daily Prices (open, high, low, close) and Volumes",
      :2._symbol "VMRAX",
      :3._last_refreshed "2018-09-10"
      :4._output_size "Compact",
      :5._time_zone "US/Eastern"},
     :time_series_daily {:2018-09-11 {:1._open 102.5300,
                                      :2._high 102.5300,
                                      :3._low 102.5300,
                                      :4._close 102.5300,
                                      :5._volume 0},
                         :2018-09-10 {:1._open 103.9700,
                                      :2._high 103.9700,
                                      :3._low 103.9700,
                                      :4._close 103.9700,
                                      :5._volume 0},
                         :2018-09-09 {:1._open 102.1400,
                                      :2._high 102.1400,
                                      :3._low 102.1400,
                                      :4._close 102.1400,
                                      :5._volume 0},
                         :2018-09-08 {:1._open 101.2300,
                                      :2._high 101.2300,
                                      :3._low 101.2300,
                                      :4._close 101.2300,
                                      :5._volume 0},
                         :2018-09-07 {:1._open 103.0500,
                                      :2._high 103.0500,
                                      :3._low 103.0500,
                                      :4._close 103.0500,
                                      :5._volume 0},
                         :2018-09-06 {:1._open 104.1500,
                                      :2._high 104.1500,
                                      :3._low 104.1500,
                                      :4._close 104.1500,
                                      :5._volume 0}}}))

(def intrinio-during-the-day
  '({:data
     [{:open 180.04,
       :date "2018-08-10",
       :adj_volume 2.1282584E7,
       :adj_close 179.42,
       :adj_low 179.42,
       :ex_dividend 0.0,
       :close 179.42,
       :volume 2.1282584E7,
       :high 182.1,
       :adj_high 182.1,
       :adj_factor 1,
       :split_ratio 1.0,
       :low 179.42,
       :adj_open 182.04}
      {:open 185.8492,
       :date "2018-08-09",
       :adj_volume 1.973212E7,
       :adj_close 183.09,
       :adj_low 182.48,
       :ex_dividend 0.0,
       :close 183.09,
       :volume 1.973212E7,
       :high 186.57,
       :adj_high 186.57,
       :adj_factor 1,
       :split_ratio 1.0,
       :low 182.48,
       :adj_open 185.8492}],
     :result_count 5,
     :page_size 100,
     :current_page 1,
     :total_pages 1,
     :api_call_credits 1,
     :dataset "INTRINIO",
     :ticker "FB"}))

(def intrinio-at-end-of-day
  '({:data
     [{:open 182.04,
       :date "2018-08-10",
       :adj_volume 2.1282584E7,
       :adj_close 180.26,
       :adj_low 179.42,
       :ex_dividend 0.0,
       :close 180.26,
       :volume 2.1282584E7,
       :high 182.1,
       :adj_high 182.1,
       :adj_factor 1,
       :split_ratio 1.0,
       :low 179.42,
       :adj_open 182.04}
      {:open 185.8492,
       :date "2018-08-09",
       :adj_volume 1.973212E7,
       :adj_close 183.09,
       :adj_low 182.48,
       :ex_dividend 0.0,
       :close 183.09,
       :volume 1.973212E7,
       :high 186.57,
       :adj_high 186.57,
       :adj_factor 1,
       :split_ratio 1.0,
       :low 182.48,
       :adj_open 185.8492}],
     :result_count 5,
     :page_size 100,
     :current_page 1,
     :total_pages 1,
     :api_call_credits 1,
     :dataset "INTRINIO",
     :ticker "FB"}))

(def intrinio-result
  '({:open 185.85M,
     :date #inst "2018-08-09T00:00:00.000-00:00",
     :adj_volume 19732120.00M,
     :adj_close 183.09M,
     :ticker "FB",
     :adj_low 182.48M,
     :ex_dividend 0.00M,
     :close 183.09M,
     :volume 19732120.00M,
     :high 186.57M,
     :adj_high 186.57M,
     :split_ratio 1.00M,
     :low 182.48M,
     :adj_open 185.85M,
     :dataset "WIKI"}
    {:open 182.04M,
     :date #inst "2018-08-10T00:00:00.000-00:00",
     :adj_volume 21282584.00M,
     :adj_close 180.26M,
     :ticker "FB",
     :adj_low 179.42M,
     :ex_dividend 0.00M,
     :close 180.26M,
     :volume 21282584.00M,
     :high 182.10M,
     :adj_high 182.10M,
     :split_ratio 1.00M,
     :low 179.42M,
     :adj_open 182.04M,
     :dataset "WIKI"}))

(def tiingo ; ticker is FB - which is not reported in api response
  '({:dataset "TIINGO"
     :ticker "FB"
     :open 215.715,
     :date "2018-07-25T00:00:00.000Z",
     :adjClose 217.5,
     :adjVolume 64592585,
     :adjHigh 218.62,
     :close 217.5,
     :volume 64592585,
     :high 218.62,
     :adjOpen 215.715,
     :adjLow 214.27,
     :divCash 0.0,
     :low 214.27,
     :splitFactor 1.0}
    {:dataset "TIINGO"
     :ticker "FB"
     :open 174.89,
     :date "2018-07-26T00:00:00.000Z",
     :adjClose 176.26,
     :adjVolume 169803668,
     :adjHigh 180.13,
     :close 176.26,
     :volume 169803668,
     :high 180.13,
     :adjOpen 174.89,
     :adjLow 173.75,
     :divCash 0.0,
     :low 173.75,
     :splitFactor 1.0}
    {:dataset "TIINGO"
     :ticker "FB"
     :open 179.87,
     :date "2018-07-27T00:00:00.000Z",
     :adjClose 174.89,
     :adjVolume 60073749,
     :adjHigh 179.93,
     :close 174.89,
     :volume 60073749,
     :high 179.93,
     :adjOpen 179.87,
     :adjLow 173.0,
     :divCash 0.0,
     :low 173.0,
     :splitFactor 1.0}))

(def morningstar
  '({:dividenddata nil,
     :fairvaluelist nil,
     :max 41,
     :min 39,
     :pricedatalist
     [{:description "",
       :min 39.42,
       :startpos 40.47,
       :slot 3,
       :symbol "XNAS:VEMAX",
       :datapoints [[40.47] [40.68] [39.94] [39.44] [39.42]],
       :max 40.68,
       :dailymaxnumpoint -1,
       :charttype 0,
       :dateindexs [43152 43155 43156 43157 43158]}],
     :pricetype "",
     :volumelist nil,
     :dataset "MSTAR",
     :ticker "VEMAX"}
    {:dividenddata nil,
     :fairvaluelist nil,
     :max 41,
     :min 39,
     :pricedatalist
     [{:description "",
       :min 158.68,
       :startpos 40.47,
       :slot 3,
       :symbol "XNAS:FB",
       :datapoints [[160.78] [158.68] [165.26]]
       :max 40.68,
       :dailymaxnumpoint -1,
       :charttype 0,
       :dateindexs [43074 43075 43076]}],
     :pricetype "",
     :volumelist nil,
     :dataset "MSTAR",
     :ticker "FB"}))

(def quandl
  '({:dataset_data
     {:transform nil,
      :limit 10,
      :frequency "daily",
      :column_index nil,
      :end_date "2017-12-08",
      :start_date "2017-12-02",
      :column_names
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
      :order nil,
      :collapse nil,
      :data
      [["2017-12-08"
        181.53
        182.28
        178.7401
        179.0
        1.937731E7
        0.0
        1.0
        181.53
        182.28
        178.7401
        179.0
        1.937731E7]
       ["2017-12-07"
        175.8
        180.39
        175.8
        180.14
        1.9588217E7
        0.0
        1.0
        175.8
        180.39
        175.8
        180.14
        1.9588217E7]
       ["2017-12-06"
        172.5
        176.77
        171.9
        176.06
        2.0059293E7
        0.0
        1.0
        172.5
        176.77
        171.9
        176.06
        2.0059293E7]
       ["2017-12-05"
        170.45
        175.38
        169.01
        172.83
        2.0028656E7
        0.0
        1.0
        170.45
        175.38
        169.01
        172.83
        2.0028656E7]
       ["2017-12-04"
        176.29
        176.57
        170.79
        171.47
        2.4184006E7
        0.0
        1.0
        176.29
        176.57
        170.79
        171.47
        2.4184006E7]]},
     :dataset "WIKI",
     :ticker "FB"}
    {:dataset_data
     {:transform nil,
      :limit 10,
      :frequency "daily",
      :column_index nil,
      :end_date "2017-12-08",
      :start_date "2017-12-02",
      :column_names
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
      :order nil,
      :collapse nil,
      :data
      [["2017-12-08"
        1170.4
        1172.79
        1157.1
        1162.0
        2957785.0
        0.0
        1.0
        1170.4
        1172.79
        1157.1
        1162.0
        2957785.0]
       ["2017-12-07"
        1156.59
        1163.19
        1151.0
        1159.79
        2408323.0
        0.0
        1.0
        1156.59
        1163.19
        1151.0
        1159.79
        2408323.0]
       ["2017-12-06"
        1137.99
        1155.89
        1136.08
        1152.35
        2756730.0
        0.0
        1.0
        1137.99
        1155.89
        1136.08
        1152.35
        2756730.0]
       ["2017-12-05"
        1128.26
        1159.27
        1124.74
        1141.57
        4033184.0
        0.0
        1.0
        1128.26
        1159.27
        1124.74
        1141.57
        4033184.0]
       ["2017-12-04"
        1173.85
        1175.2
        1128.0
        1133.95
        5872358.0
        0.0
        1.0
        1173.85
        1175.2
        1128.0
        1133.95
        5872358.0]]},
     :dataset "WIKI",
     :ticker "AMZN"}
    {:dataset_data
     {:transform nil,
      :limit 10,
      :frequency "daily",
      :column_index nil,
      :end_date "2017-12-08",
      :start_date "2017-12-02",
      :column_names
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
      :order nil,
      :collapse nil,
      :data
      [["2017-12-08"
        1037.49
        1042.05
        1032.52
        1037.05
        1277894.0
        0.0
        1.0
        1037.49
        1042.05
        1032.52
        1037.05
        1277894.0]
       ["2017-12-07"
        1020.43
        1034.24
        1018.07
        1030.93
        1387107.0
        0.0
        1.0
        1020.43
        1034.24
        1018.07
        1030.93
        1387107.0]
       ["2017-12-06"
        1001.5
        1024.97
        1001.14
        1018.38
        1254837.0
        0.0
        1.0
        1001.5
        1024.97
        1001.14
        1018.38
        1254837.0]
       ["2017-12-05"
        995.94
        1020.61
        988.28
        1005.15
        2023376.0
        0.0
        1.0
        995.94
        1020.61
        988.28
        1005.15
        2023376.0]
       ["2017-12-04"
        1012.66
        1016.1
        995.57
        998.68
        1891408.0
        0.0
        1.0
        1012.66
        1016.1
        995.57
        998.68
        1891408.0]]},
     :dataset "WIKI",
     :ticker "GOOG"}
    {:dataset_data
     {:transform nil,
      :limit 10,
      :frequency "daily",
      :column_index nil,
      :end_date "2017-12-08",
      :start_date "2017-12-02",
      :column_names
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
      :order nil,
      :collapse nil,
      :data
      [["2017-12-08"
        194.01
        194.78
        191.15
        191.49
        1.1483868E7
        0.0
        1.0
        194.01
        194.78
        191.15
        191.49
        1.1483868E7]
       ["2017-12-07"
        191.96
        193.6
        190.12
        191.99
        1.3190983E7
        0.0
        1.0
        191.96
        193.6
        190.12
        191.99
        1.3190983E7]
       ["2017-12-06"
        185.7
        190.14
        184.84
        189.26
        1.1464458E7
        0.0
        1.0
        185.7
        190.14
        184.84
        189.26
        1.1464458E7]
       ["2017-12-05"
        182.4
        192.7
        180.58
        187.74
        2.4480558E7
        0.0
        1.0
        182.4
        192.7
        180.58
        187.74
        2.4480558E7]
       ["2017-12-04"
        200.05
        200.3
        184.5
        186.66
        2.7080405E7
        0.0
        1.0
        200.05
        200.3
        184.5
        186.66
        2.7080405E7]]},
     :dataset "WIKI",
     :ticker "NVDA"}))

(def result
  '({:open 179.87M,
     :date #inst "2018-07-27T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "FB",
     :adj_low nil,
     :ex_dividend nil,
     :close 174.89M,
     :volume 60073749.00M,
     :high 179.93M,
     :adj_high nil,
     :split_ratio nil,
     :low 173.00M,
     :adj_open nil,
     :dataset "TIINGO"}
    {:open nil,
     :date #inst "2018-02-23T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "VEMAX",
     :adj_low nil,
     :ex_dividend nil,
     :close 40.47M,
     :volume nil,
     :high nil,
     :adj_high nil,
     :split_ratio nil,
     :low nil,
     :adj_open nil,
     :dataset "MSTAR"}
    {:open 215.72M,
     :date #inst "2018-07-25T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "FB",
     :adj_low nil,
     :ex_dividend nil,
     :close 217.50M,
     :volume 64592585.00M,
     :high 218.62M,
     :adj_high nil,
     :split_ratio nil,
     :low 214.27M,
     :adj_open nil,
     :dataset "TIINGO"}
    {:open 174.89M,
     :date #inst "2018-07-26T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "FB",
     :adj_low nil,
     :ex_dividend nil,
     :close 176.26M,
     :volume 169803668.00M,
     :high 180.13M,
     :adj_high nil,
     :split_ratio nil,
     :low 173.75M,
     :adj_open nil,
     :dataset "TIINGO"}
    {:open nil,
     :date #inst "2018-02-26T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "VEMAX",
     :adj_low nil,
     :ex_dividend nil,
     :close 40.68M,
     :volume nil,
     :high nil,
     :adj_high nil,
     :split_ratio nil,
     :low nil,
     :adj_open nil,
     :dataset "MSTAR"}
    {:open nil,
     :date #inst "2018-02-27T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "VEMAX",
     :adj_low nil,
     :ex_dividend nil,
     :close 39.94M,
     :volume nil,
     :high nil,
     :adj_high nil,
     :split_ratio nil,
     :low nil,
     :adj_open nil,
     :dataset "MSTAR"}
    {:open nil,
     :date #inst "2018-02-28T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "VEMAX",
     :adj_low nil,
     :ex_dividend nil,
     :close 39.44M,
     :volume nil,
     :high nil,
     :adj_high nil,
     :split_ratio nil,
     :low nil,
     :adj_open nil,
     :dataset "MSTAR"}
    {:open nil,
     :date #inst "2018-03-01T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "VEMAX",
     :adj_low nil,
     :ex_dividend nil,
     :close 39.42M,
     :volume nil,
     :high nil,
     :adj_high nil,
     :split_ratio nil,
     :low nil,
     :adj_open nil,
     :dataset "MSTAR"}
    {:open nil,
     :date #inst "2017-12-09T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "FB",
     :adj_low nil,
     :ex_dividend nil,
     :close 165.26M,
     :volume nil,
     :high nil,
     :adj_high nil,
     :split_ratio nil,
     :low nil,
     :adj_open nil,
     :dataset "MSTAR"}
    {:open 181.53M,
     :date #inst "2017-12-08T00:00:00.000-00:00",
     :adj_volume 19377310.00M,
     :adj_close 179.00M,
     :ticker "FB",
     :adj_low 178.74M,
     :ex_dividend 0.00M,
     :close 179.00M,
     :volume 19377310.00M,
     :high 182.28M,
     :adj_high 182.28M,
     :split_ratio 1.00M,
     :low 178.74M,
     :adj_open 181.53M,
     :dataset "MSTAR"}
    {:open 181.53M,
     :date #inst "2017-12-08T00:00:00.000-00:00",
     :adj_volume 19377310.00M,
     :adj_close 179.00M,
     :ticker "FB",
     :adj_low 178.74M,
     :ex_dividend 0.00M,
     :close 179.00M,
     :volume 19377310.00M,
     :high 182.28M,
     :adj_high 182.28M,
     :split_ratio 1.00M,
     :low 178.74M,
     :adj_open 181.53M,
     :dataset "WIKI"}
    {:open 175.80M,
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :adj_volume 19588217.00M,
     :adj_close 180.14M,
     :ticker "FB",
     :adj_low 175.80M,
     :ex_dividend 0.00M,
     :close 180.14M,
     :volume 19588217.00M,
     :high 180.39M,
     :adj_high 180.39M,
     :split_ratio 1.00M,
     :low 175.80M,
     :adj_open 175.80M,
     :dataset "MSTAR"}
    {:open 175.80M,
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :adj_volume 19588217.00M,
     :adj_close 180.14M,
     :ticker "FB",
     :adj_low 175.80M,
     :ex_dividend 0.00M,
     :close 180.14M,
     :volume 19588217.00M,
     :high 180.39M,
     :adj_high 180.39M,
     :split_ratio 1.00M,
     :low 175.80M,
     :adj_open 175.80M,
     :dataset "WIKI"}
    {:open 172.50M,
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :adj_volume 20059293.00M,
     :adj_close 176.06M,
     :ticker "FB",
     :adj_low 171.90M,
     :ex_dividend 0.00M,
     :close 176.06M,
     :volume 20059293.00M,
     :high 176.77M,
     :adj_high 176.77M,
     :split_ratio 1.00M,
     :low 171.90M,
     :adj_open 172.50M,
     :dataset "WIKI"}
    {:open 170.45M,
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :adj_volume 20028656.00M,
     :adj_close 172.83M,
     :ticker "FB",
     :adj_low 169.01M,
     :ex_dividend 0.00M,
     :close 172.83M,
     :volume 20028656.00M,
     :high 175.38M,
     :adj_high 175.38M,
     :split_ratio 1.00M,
     :low 169.01M,
     :adj_open 170.45M,
     :dataset "WIKI"}
    {:open 176.29M,
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :adj_volume 24184006.00M,
     :adj_close 171.47M,
     :ticker "FB",
     :adj_low 170.79M,
     :ex_dividend 0.00M,
     :close 171.47M,
     :volume 24184006.00M,
     :high 176.57M,
     :adj_high 176.57M,
     :split_ratio 1.00M,
     :low 170.79M,
     :adj_open 176.29M,
     :dataset "WIKI"}
    {:open 1170.40M,
     :date #inst "2017-12-08T00:00:00.000-00:00",
     :adj_volume 2957785.00M,
     :adj_close 1162.00M,
     :ticker "AMZN",
     :adj_low 1157.10M,
     :ex_dividend 0.00M,
     :close 1162.00M,
     :volume 2957785.00M,
     :high 1172.79M,
     :adj_high 1172.79M,
     :split_ratio 1.00M,
     :low 1157.10M,
     :adj_open 1170.40M,
     :dataset "WIKI"}
    {:open 1156.59M,
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :adj_volume 2408323.00M,
     :adj_close 1159.79M,
     :ticker "AMZN",
     :adj_low 1151.00M,
     :ex_dividend 0.00M,
     :close 1159.79M,
     :volume 2408323.00M,
     :high 1163.19M,
     :adj_high 1163.19M,
     :split_ratio 1.00M,
     :low 1151.00M,
     :adj_open 1156.59M,
     :dataset "WIKI"}
    {:open 1137.99M,
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :adj_volume 2756730.00M,
     :adj_close 1152.35M,
     :ticker "AMZN",
     :adj_low 1136.08M,
     :ex_dividend 0.00M,
     :close 1152.35M,
     :volume 2756730.00M,
     :high 1155.89M,
     :adj_high 1155.89M,
     :split_ratio 1.00M,
     :low 1136.08M,
     :adj_open 1137.99M,
     :dataset "WIKI"}
    {:open 1128.26M,
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :adj_volume 4033184.00M,
     :adj_close 1141.57M,
     :ticker "AMZN",
     :adj_low 1124.74M,
     :ex_dividend 0.00M,
     :close 1141.57M,
     :volume 4033184.00M,
     :high 1159.27M,
     :adj_high 1159.27M,
     :split_ratio 1.00M,
     :low 1124.74M,
     :adj_open 1128.26M,
     :dataset "WIKI"}
    {:open 1173.85M,
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :adj_volume 5872358.00M,
     :adj_close 1133.95M,
     :ticker "AMZN",
     :adj_low 1128.00M,
     :ex_dividend 0.00M,
     :close 1133.95M,
     :volume 5872358.00M, :high 1175.20M,
     :adj_high 1175.20M,
     :split_ratio 1.00M,
     :low 1128.00M,
     :adj_open 1173.85M,
     :dataset "WIKI"}
    {:open 1037.49M,
     :date #inst "2017-12-08T00:00:00.000-00:00",
     :adj_volume 1277894.00M,
     :adj_close 1037.05M,
     :ticker "GOOG",
     :adj_low 1032.52M,
     :ex_dividend 0.00M,
     :close 1037.05M,
     :volume 1277894.00M,
     :high 1042.05M,
     :adj_high 1042.05M,
     :split_ratio 1.00M,
     :low 1032.52M,
     :adj_open 1037.49M,
     :dataset "WIKI"}
    {:open 1020.43M,
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :adj_volume 1387107.00M,
     :adj_close 1030.93M,
     :ticker "GOOG",
     :adj_low 1018.07M,
     :ex_dividend 0.00M,
     :close 1030.93M,
     :volume 1387107.00M,
     :high 1034.24M,
     :adj_high 1034.24M,
     :split_ratio 1.00M,
     :low 1018.07M,
     :adj_open 1020.43M,
     :dataset "WIKI"}
    {:open 1001.50M,
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :adj_volume 1254837.00M,
     :adj_close 1018.38M,
     :ticker "GOOG",
     :adj_low 1001.14M,
     :ex_dividend 0.00M,
     :close 1018.38M,
     :volume 1254837.00M,
     :high 1024.97M,
     :adj_high 1024.97M,
     :split_ratio 1.00M,
     :low 1001.14M,
     :adj_open 1001.50M,
     :dataset "WIKI"}
    {:open 995.94M,
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :adj_volume 2023376.00M,
     :adj_close 1005.15M,
     :ticker "GOOG",
     :adj_low 988.28M,
     :ex_dividend 0.00M,
     :close 1005.15M,
     :volume 2023376.00M,
     :high 1020.61M,
     :adj_high 1020.61M,
     :split_ratio 1.00M,
     :low 988.28M,
     :adj_open 995.94M,
     :dataset "WIKI"}
    {:open 1012.66M,
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :adj_volume 1891408.00M,
     :adj_close 998.68M,
     :ticker "GOOG",
     :adj_low 995.57M,
     :ex_dividend 0.00M,
     :close 998.68M,
     :volume 1891408.00M,
     :high 1016.10M,
     :adj_high 1016.10M,
     :split_ratio 1.00M,
     :low 995.57M,
     :adj_open 1012.66M,
     :dataset "WIKI"}
    {:open 194.01M,
     :date #inst "2017-12-08T00:00:00.000-00:00",
     :adj_volume 11483868.00M,
     :adj_close 191.49M,
     :ticker "NVDA",
     :adj_low 191.15M,
     :ex_dividend 0.00M,
     :close 191.49M,
     :volume 11483868.00M,
     :high 194.78M,
     :adj_high 194.78M,
     :split_ratio 1.00M,
     :low 191.15M,
     :adj_open 194.01M,
     :dataset "WIKI"}
    {:open 191.96M,
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :adj_volume 13190983.00M,
     :adj_close 191.99M,
     :ticker "NVDA",
     :adj_low 190.12M,
     :ex_dividend 0.00M,
     :close 191.99M,
     :volume 13190983.00M,
     :high 193.60M,
     :adj_high 193.60M,
     :split_ratio 1.00M,
     :low 190.12M,
     :adj_open 191.96M,
     :dataset "WIKI"}
    {:open 185.70M,
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :adj_volume 11464458.00M,
     :adj_close 189.26M,
     :ticker "NVDA",
     :adj_low 184.84M,
     :ex_dividend 0.00M,
     :close 189.26M,
     :volume 11464458.00M,
     :high 190.14M,
     :adj_high 190.14M,
     :split_ratio 1.00M,
     :low 184.84M,
     :adj_open 185.70M,
     :dataset "WIKI"}
    {:open 182.40M,
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :adj_volume 24480558.00M,
     :adj_close 187.74M,
     :ticker "NVDA",
     :adj_low 180.58M,
     :ex_dividend 0.00M,
     :close 187.74M,
     :volume 24480558.00M,
     :high 192.70M,
     :adj_high 192.70M,
     :split_ratio 1.00M,
     :low 180.58M,
     :adj_open 182.40M,
     :dataset "WIKI"}
    {:open 200.05M,
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :adj_volume 27080405.00M,
     :adj_close 186.66M,
     :ticker "NVDA",
     :adj_low 184.50M,
     :ex_dividend 0.00M,
     :close 186.66M,
     :volume 27080405.00M,
     :high 200.30M,
     :adj_high 200.30M,
     :split_ratio 1.00M,
     :low 184.50M,
     :adj_open 200.05M,
     :dataset "WIKI"}
    {:open 182.04M,
     :date #inst "2018-08-10T00:00:00.000-00:00",
     :adj_volume 21282584.00M,
     :adj_close 180.26M,
     :ticker "FB",
     :adj_low 179.42M,
     :ex_dividend 0.00M,
     :close 180.26M,
     :volume 21282584.00M,
     :high 182.10M,
     :adj_high 182.10M,
     :split_ratio 1.00M,
     :low 179.42M,
     :adj_open 182.04M,
     :dataset "WIKI"}
    {:open 185.85M,
     :date #inst "2018-08-09T00:00:00.000-00:00",
     :adj_volume 19732120.00M,
     :adj_close 183.09M,
     :ticker "FB",
     :adj_low 182.48M,
     :ex_dividend 0.00M,
     :close 183.09M,
     :volume 19732120.00M,
     :high 186.57M,
     :adj_high 186.57M,
     :split_ratio 1.00M,
     :low 182.48M,
     :adj_open 185.85M,
     :dataset "WIKI"}))

(def result'
  '({:open 182.04M,
     :date #inst "2018-08-10T00:00:00.000-00:00",
     :adj_volume 21282584.00M,
     :adj_close 180.26M,
     :ticker "FB",
     :adj_low 179.42M,
     :ex_dividend 0.00M,
     :close 180.26M,
     :volume 21282584.00M,
     :high 182.10M,
     :adj_high 182.10M,
     :split_ratio 1.00M,
     :low 179.42M,
     :adj_open 182.04M,
     :dataset "WIKI"}
    {:open 185.85M,
     :date #inst "2018-08-09T00:00:00.000-00:00",
     :adj_volume 19732120.00M,
     :adj_close 183.09M,
     :ticker "FB",
     :adj_low 182.48M,
     :ex_dividend 0.00M,
     :close 183.09M,
     :volume 19732120.00M,
     :high 186.57M,
     :adj_high 186.57M,
     :split_ratio 1.00M,
     :low 182.48M,
     :adj_open 185.85M,
     :dataset "WIKI"}
    {:open 194.01M,
     :date #inst "2017-12-08T00:00:00.000-00:00",
     :adj_volume 11483868.00M,
     :adj_close 191.49M,
     :ticker "NVDA",
     :adj_low 191.15M,
     :ex_dividend 0.00M,
     :close 191.49M,
     :volume 11483868.00M,
     :high 194.78M,
     :adj_high 194.78M,
     :split_ratio 1.00M,
     :low 191.15M,
     :adj_open 194.01M,
     :dataset "WIKI"}
    {:open 191.96M,
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :adj_volume 13190983.00M,
     :adj_close 191.99M,
     :ticker "NVDA",
     :adj_low 190.12M,
     :ex_dividend 0.00M,
     :close 191.99M,
     :volume 13190983.00M,
     :high 193.60M,
     :adj_high 193.60M,
     :split_ratio 1.00M,
     :low 190.12M,
     :adj_open 191.96M,
     :dataset "WIKI"}
    {:open 185.70M,
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :adj_volume 11464458.00M,
     :adj_close 189.26M,
     :ticker "NVDA",
     :adj_low 184.84M,
     :ex_dividend 0.00M,
     :close 189.26M,
     :volume 11464458.00M,
     :high 190.14M,
     :adj_high 190.14M,
     :split_ratio 1.00M,
     :low 184.84M,
     :adj_open 185.70M,
     :dataset "WIKI"}
    {:open 182.40M,
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :adj_volume 24480558.00M,
     :adj_close 187.74M,
     :ticker "NVDA",
     :adj_low 180.58M,
     :ex_dividend 0.00M,
     :close 187.74M,
     :volume 24480558.00M,
     :high 192.70M,
     :adj_high 192.70M,
     :split_ratio 1.00M,
     :low 180.58M,
     :adj_open 182.40M,
     :dataset "WIKI"}
    {:open 200.05M,
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :adj_volume 27080405.00M,
     :adj_close 186.66M,
     :ticker "NVDA",
     :adj_low 184.50M,
     :ex_dividend 0.00M,
     :close 186.66M,
     :volume 27080405.00M,
     :high 200.30M,
     :adj_high 200.30M,
     :split_ratio 1.00M,
     :low 184.50M,
     :adj_open 200.05M,
     :dataset "WIKI"}
    {:open 1037.49M,
     :date #inst "2017-12-08T00:00:00.000-00:00",
     :adj_volume 1277894.00M,
     :adj_close 1037.05M,
     :ticker "GOOG",
     :adj_low 1032.52M,
     :ex_dividend 0.00M,
     :close 1037.05M,
     :volume 1277894.00M,
     :high 1042.05M,
     :adj_high 1042.05M,
     :split_ratio 1.00M,
     :low 1032.52M,
     :adj_open 1037.49M,
     :dataset "WIKI"}
    {:open 1020.43M,
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :adj_volume 1387107.00M,
     :adj_close 1030.93M,
     :ticker "GOOG",
     :adj_low 1018.07M,
     :ex_dividend 0.00M,
     :close 1030.93M,
     :volume 1387107.00M,
     :high 1034.24M,
     :adj_high 1034.24M,
     :split_ratio 1.00M,
     :low 1018.07M,
     :adj_open 1020.43M,
     :dataset "WIKI"}
    {:open 1001.50M,
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :adj_volume 1254837.00M,
     :adj_close 1018.38M,
     :ticker "GOOG",
     :adj_low 1001.14M,
     :ex_dividend 0.00M,
     :close 1018.38M,
     :volume 1254837.00M,
     :high 1024.97M,
     :adj_high 1024.97M,
     :split_ratio 1.00M,
     :low 1001.14M,
     :adj_open 1001.50M,
     :dataset "WIKI"}
    {:open 995.94M,
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :adj_volume 2023376.00M,
     :adj_close 1005.15M,
     :ticker "GOOG",
     :adj_low 988.28M,
     :ex_dividend 0.00M,
     :close 1005.15M,
     :volume 2023376.00M,
     :high 1020.61M,
     :adj_high 1020.61M,
     :split_ratio 1.00M,
     :low 988.28M,
     :adj_open 995.94M,
     :dataset "WIKI"}
    {:open 1012.66M,
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :adj_volume 1891408.00M,
     :adj_close 998.68M,
     :ticker "GOOG",
     :adj_low 995.57M,
     :ex_dividend 0.00M,
     :close 998.68M,
     :volume 1891408.00M,
     :high 1016.10M,
     :adj_high 1016.10M,
     :split_ratio 1.00M,
     :low 995.57M,
     :adj_open 1012.66M,
     :dataset "WIKI"}
    {:open 1170.40M,
     :date #inst "2017-12-08T00:00:00.000-00:00",
     :adj_volume 2957785.00M,
     :adj_close 1162.00M,
     :ticker "AMZN",
     :adj_low 1157.10M,
     :ex_dividend 0.00M,
     :close 1162.00M,
     :volume 2957785.00M,
     :high 1172.79M,
     :adj_high 1172.79M,
     :split_ratio 1.00M,
     :low 1157.10M,
     :adj_open 1170.40M,
     :dataset "WIKI"}
    {:open 1156.59M,
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :adj_volume 2408323.00M,
     :adj_close 1159.79M,
     :ticker "AMZN",
     :adj_low 1151.00M,
     :ex_dividend 0.00M,
     :close 1159.79M,
     :volume 2408323.00M,
     :high 1163.19M,
     :adj_high 1163.19M,
     :split_ratio 1.00M,
     :low 1151.00M,
     :adj_open 1156.59M,
     :dataset "WIKI"}
    {:open 1137.99M,
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :adj_volume 2756730.00M,
     :adj_close 1152.35M, :ticker "AMZN",
     :adj_low 1136.08M,
     :ex_dividend 0.00M,
     :close 1152.35M,
     :volume 2756730.00M,
     :high 1155.89M,
     :adj_high 1155.89M,
     :split_ratio 1.00M,
     :low 1136.08M,
     :adj_open 1137.99M,
     :dataset "WIKI"}
    {:open 1128.26M,
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :adj_volume 4033184.00M,
     :adj_close 1141.57M,
     :ticker "AMZN",
     :adj_low 1124.74M,
     :ex_dividend 0.00M,
     :close 1141.57M,
     :volume 4033184.00M,
     :high 1159.27M,
     :adj_high 1159.27M,
     :split_ratio 1.00M,
     :low 1124.74M,
     :adj_open 1128.26M,
     :dataset "WIKI"}
    {:open nil,
     :date #inst "2017-12-09T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "FB",
     :adj_low nil,
     :ex_dividend nil,
     :close 165.26M,
     :volume nil,
     :high nil,
     :adj_high nil,
     :split_ratio nil,
     :low nil,
     :adj_open nil,
     :dataset "MSTAR"}
    {:open 1173.85M,
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :adj_volume 5872358.00M,
     :adj_close 1133.95M,
     :ticker "AMZN",
     :adj_low 1128.00M,
     :ex_dividend 0.00M,
     :close 1133.95M,
     :volume 5872358.00M,
     :high 1175.20M,
     :adj_high 1175.20M,
     :split_ratio 1.00M,
     :low 1128.00M,
     :adj_open 1173.85M,
     :dataset "WIKI"}
    {:open 181.53M,
     :date #inst "2017-12-08T00:00:00.000-00:00",
     :adj_volume 19377310.00M,
     :adj_close 179.00M,
     :ticker "FB",
     :adj_low 178.74M,
     :ex_dividend 0.00M,
     :close 179.00M,
     :volume 19377310.00M,
     :high 182.28M,
     :adj_high 182.28M,
     :split_ratio 1.00M,
     :low 178.74M,
     :adj_open 181.53M,
     :dataset "WIKI"}
    {:open 175.80M,
     :date #inst "2017-12-07T00:00:00.000-00:00",
     :adj_volume 19588217.00M,
     :adj_close 180.14M,
     :ticker "FB",
     :adj_low 175.80M,
     :ex_dividend 0.00M,
     :close 180.14M,
     :volume 19588217.00M,
     :high 180.39M,
     :adj_high 180.39M,
     :split_ratio 1.00M,
     :low 175.80M,
     :adj_open 175.80M,
     :dataset "WIKI"}
    {:open 172.50M,
     :date #inst "2017-12-06T00:00:00.000-00:00",
     :adj_volume 20059293.00M,
     :adj_close 176.06M,
     :ticker "FB",
     :adj_low 171.90M,
     :ex_dividend 0.00M,
     :close 176.06M,
     :volume 20059293.00M,
     :high 176.77M,
     :adj_high 176.77M,
     :split_ratio 1.00M,
     :low 171.90M,
     :adj_open 172.50M,
     :dataset "WIKI"}
    {:open 170.45M,
     :date #inst "2017-12-05T00:00:00.000-00:00",
     :adj_volume 20028656.00M,
     :adj_close 172.83M,
     :ticker "FB",
     :adj_low 169.01M,
     :ex_dividend 0.00M,
     :close 172.83M,
     :volume 20028656.00M,
     :high 175.38M,
     :adj_high 175.38M,
     :split_ratio 1.00M,
     :low 169.01M,
     :adj_open 170.45M,
     :dataset "WIKI"}
    {:open 176.29M,
     :date #inst "2017-12-04T00:00:00.000-00:00",
     :adj_volume 24184006.00M,
     :adj_close 171.47M,
     :ticker "FB",
     :adj_low 170.79M,
     :ex_dividend 0.00M,
     :close 171.47M,
     :volume 24184006.00M,
     :high 176.57M,
     :adj_high 176.57M,
     :split_ratio 1.00M,
     :low 170.79M,
     :adj_open 176.29M,
     :dataset "WIKI"}
    {:open nil,
     :date #inst "2018-02-23T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "VEMAX",
     :adj_low nil,
     :ex_dividend nil,
     :close 40.47M,
     :volume nil,
     :high nil,
     :adj_high nil,
     :split_ratio nil,
     :low nil,
     :adj_open nil,
     :dataset "MSTAR"}
    {:open nil,
     :date #inst "2018-02-26T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "VEMAX",
     :adj_low nil,
     :ex_dividend nil,
     :close 40.68M,
     :volume nil,
     :high nil,
     :adj_high nil,
     :split_ratio nil,
     :low nil,
     :adj_open nil,
     :dataset "MSTAR"}
    {:open nil,
     :date #inst "2018-02-27T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "VEMAX",
     :adj_low nil,
     :ex_dividend nil,
     :close 39.94M,
     :volume nil,
     :high nil,
     :adj_high nil,
     :split_ratio nil,
     :low nil, :adj_open nil,
     :dataset "MSTAR"}
    {:open nil,
     :date #inst "2018-02-28T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "VEMAX",
     :adj_low nil,
     :ex_dividend nil,
     :close 39.44M,
     :volume nil,
     :high nil,
     :adj_high nil,
     :split_ratio nil,
     :low nil,
     :adj_open nil,
     :dataset "MSTAR"}
    {:open nil,
     :date #inst "2018-03-01T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "VEMAX",
     :adj_low nil,
     :ex_dividend nil,
     :close 39.42M,
     :volume nil,
     :high nil,
     :adj_high nil,
     :split_ratio nil,
     :low nil,
     :adj_open nil,
     :dataset "MSTAR"}
    {:open 179.87M,
     :date #inst "2018-07-27T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "FB",
     :adj_low nil,
     :ex_dividend nil,
     :close 174.89M,
     :volume 60073749.00M,
     :high 179.93M, :adj_high nil,
     :split_ratio nil,
     :low 173.00M,
     :adj_open nil,
     :dataset "TIINGO"}
    {:open 174.89M,
     :date #inst "2018-07-26T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "FB",
     :adj_low nil,
     :ex_dividend nil,
     :close 176.26M,
     :volume 169803668.00M,
     :high 180.13M,
     :adj_high nil,
     :split_ratio nil,
     :low 173.75M,
     :adj_open nil,
     :dataset "TIINGO"}
    {:open 215.72M,
     :date #inst "2018-07-25T00:00:00.000-00:00",
     :adj_volume nil,
     :adj_close nil,
     :ticker "FB",
     :adj_low nil,
     :ex_dividend nil,
     :close 217.50M,
     :volume 64592585.00M,
     :high 218.62M,
     :adj_high nil,
     :split_ratio nil,
     :low 214.27M,
     :adj_open nil,
     :dataset "TIINGO"}))
