(ns fixtures.commodities)

(def crypto
  '({:dataset_data
     {:transform nil,
      :limit 5,
      :frequency "daily",
      :column_index nil,
      :end_date "2020-05-09",
      :start_date "2020-05-03",
      :column_names
      ["Date" "24h Average" "12h Average" "Last" "Volume (BTC)"],
      :order nil,
      :collapse nil,
      :data
      [["2020-05-09" 10350.16 10361.06 10620.49 44.31460942]
       ["2020-05-08" 10585.72 10694.59 12492.54 76.59615332]
       ["2020-05-07" 10072.49 10086.27 9663.88 70.71815037]
       ["2020-05-06" 9475.56 9576.07 9998.68 75.97538965]
       ["2020-05-03" 9670.44 9611.19 9425.39 29.51375144]]},
     :dataset "LOCALBTC",
     :ticker "USD"}))

(def precious-metals
  '({:dataset_data
     {:transform nil,
      :limit 5,
      :frequency "daily",
      :column_index nil,
      :end_date "2020-04-29",
      :start_date "2020-05-03",
      :column_names
      ["Date"
       "Gold AM Fix"
       "Gold PM Fix"
       "Silver Fix"
       "Platinum AM Fix"
       "Platinum PM Fix"
       "Palladium AM Fix"
       "Palladium PM Fix"
       "6 Month Gold Lease Rates (%PA)"],
      :order nil,
      :collapse nil,
      :data
      [["2020-04-29" 1706.0 1703.35 15.165 780.0 780.0 1958.0 1932.0 nil]
       ["2020-04-28" 1708.1 1691.55 15.145 765.0 775.0 1913.0 1888.0 nil]
       ["2020-04-27" 1717.25 1714.95 15.205 765.0 764.0 1987.0 1943.0 nil]
       ["2020-04-24" 1727.25 1715.9 15.315 766.0 763.0 2040.0 2005.0 nil]
       ["2020-04-23"
        1727.55
        1736.25
        15.305
        766.0
        765.0
        1950.0
        1990.0
        nil]]},
     :dataset "PERTH",
     :ticker "LONMETALS"}
    ))

(def gold
  '({:dataset_data
     {:transform nil,
      :limit 5,
      :frequency "daily",
      :column_index nil,
      :end_date "2020-05-07",
      :start_date "2020-04-10",
      :column_names
      ["Date"
       "USD (AM)"
       "USD (PM)"
       "GBP (AM)"
       "GBP (PM)"
       "EURO (AM)"
       "EURO (PM)"],
      :order nil,
      :collapse nil,
      :data
      [["2020-05-07" 1688.65 1704.05 1366.29 1387.78 1565.21 1582.38]
       ["2020-05-06" 1698.9 1691.5 1373.56 1366.73 1574.71 1564.13]
       ["2020-05-05" 1696.3 1699.55 1363.83 1363.72 1566.36 1562.91]
       ["2020-05-04" 1703.7 1709.1 1371.14 1374.63 1558.72 1563.83]
       ["2020-05-01" 1673.05 1686.25 1332.08 1347.15 1523.14 1536.68]]},
     :dataset "LBMA",
     :ticker "GOLD"}))

(def oil
  '({:dataset_data
     {:transform nil,
      :limit 5,
      :frequency "daily",
      :column_index nil,
      :end_date "2020-05-07",
      :start_date "2020-05-03",
      :column_names ["Date" "Value"],
      :order nil,
      :collapse nil,
      :data
      [["2020-05-07" 22.91]
       ["2020-05-06" 22.4]
       ["2020-05-05" 21.43]
       ["2020-05-04" 18.36]]},
     :dataset "OPEC",
     :ticker "ORB"}))

(def result
  '())
