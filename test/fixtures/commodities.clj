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
     :ticker "LONMETALS"}))

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
  '({:dataset "LOCALBTC",
     :ticker "BTCUSD",
     :date #inst "2020-05-09T00:00:00.000-00:00",
     :open nil,
     :average 10350.16M,
     :close 10620.49M,
     :volume 44.31M}
    {:dataset "LOCALBTC",
     :ticker "BTCUSD",
     :date #inst "2020-05-08T00:00:00.000-00:00",
     :open nil,
     :average 10585.72M,
     :close 12492.54M,
     :volume 76.6M}
    {:dataset "LOCALBTC",
     :ticker "BTCUSD",
     :date #inst "2020-05-07T00:00:00.000-00:00",
     :open nil,
     :average 10072.49M,
     :close 9663.88M,
     :volume 70.72M}
    {:dataset "LOCALBTC",
     :ticker "BTCUSD",
     :date #inst "2020-05-06T00:00:00.000-00:00",
     :open nil,
     :average 9475.56M,
     :close 9998.68M,
     :volume 75.98M}
    {:dataset "LOCALBTC",
     :ticker "BTCUSD",
     :date #inst "2020-05-03T00:00:00.000-00:00",
     :open nil,
     :average 9670.44M,
     :close 9425.39M,
     :volume 29.51M}
    {:dataset "PERTH",
     :ticker "GOLD",
     :date #inst "2020-04-29T00:00:00.000-00:00",
     :open 1706M,
     :average 1704.68M,
     :close 1703.35M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "GOLD",
     :date #inst "2020-04-28T00:00:00.000-00:00",
     :open 1708.1M,
     :average 1699.83M,
     :close 1691.55M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "GOLD",
     :date #inst "2020-04-27T00:00:00.000-00:00",
     :open 1717.25M,
     :average 1716.1M,
     :close 1714.95M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "GOLD",
     :date #inst "2020-04-24T00:00:00.000-00:00",
     :open 1727.25M,
     :average 1721.58M,
     :close 1715.9M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "GOLD",
     :date #inst "2020-04-23T00:00:00.000-00:00",
     :open 1727.55M,
     :average 1731.9M,
     :close 1736.25M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "SILVER",
     :date #inst "2020-04-29T00:00:00.000-00:00",
     :open nil,
     :average nil,
     :close 15.17M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "SILVER",
     :date #inst "2020-04-28T00:00:00.000-00:00",
     :open nil,
     :average nil,
     :close 15.15M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "SILVER",
     :date #inst "2020-04-27T00:00:00.000-00:00",
     :open nil,
     :average nil,
     :close 15.21M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "SILVER",
     :date #inst "2020-04-24T00:00:00.000-00:00",
     :open nil,
     :average nil,
     :close 15.32M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "SILVER",
     :date #inst "2020-04-23T00:00:00.000-00:00",
     :open nil,
     :average nil,
     :close 15.31M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "PLATINUM",
     :date #inst "2020-04-29T00:00:00.000-00:00",
     :open 780M,
     :average 780M,
     :close 780M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "PLATINUM",
     :date #inst "2020-04-28T00:00:00.000-00:00",
     :open 765M,
     :average 770M,
     :close 775M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "PLATINUM",
     :date #inst "2020-04-27T00:00:00.000-00:00",
     :open 765M,
     :average 764.5M,
     :close 764M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "PLATINUM",
     :date #inst "2020-04-24T00:00:00.000-00:00",
     :open 766M,
     :average 764.5M,
     :close 763M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "PLATINUM",
     :date #inst "2020-04-23T00:00:00.000-00:00",
     :open 766M,
     :average 765.5M,
     :close 765M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "PALLADIUM",
     :date #inst "2020-04-29T00:00:00.000-00:00",
     :open 780M,
     :average 780M,
     :close 780M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "PALLADIUM",
     :date #inst "2020-04-28T00:00:00.000-00:00",
     :open 765M,
     :average 770M,
     :close 775M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "PALLADIUM",
     :date #inst "2020-04-27T00:00:00.000-00:00",
     :open 765M,
     :average 764.5M,
     :close 764M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "PALLADIUM",
     :date #inst "2020-04-24T00:00:00.000-00:00",
     :open 766M,
     :average 764.5M,
     :close 763M,
     :volume nil}
    {:dataset "PERTH",
     :ticker "PALLADIUM",
     :date #inst "2020-04-23T00:00:00.000-00:00",
     :open 766M,
     :average 765.5M,
     :close 765M,
     :volume nil}
    {:dataset "LBMA",
     :ticker "GOLD",
     :date #inst "2020-05-07T00:00:00.000-00:00",
     :open nil,
     :average 1696.35M,
     :close 1704.05M,
     :volume nil}
    {:dataset "LBMA",
     :ticker "GOLD",
     :date #inst "2020-05-06T00:00:00.000-00:00",
     :open nil,
     :average 1695.2M,
     :close 1691.5M,
     :volume nil}
    {:dataset "LBMA",
     :ticker "GOLD",
     :date #inst "2020-05-05T00:00:00.000-00:00",
     :open nil,
     :average 1697.93M,
     :close 1699.55M,
     :volume nil}
    {:dataset "LBMA",
     :ticker "GOLD",
     :date #inst "2020-05-04T00:00:00.000-00:00",
     :open nil,
     :average 1706.4M,
     :close 1709.1M,
     :volume nil}
    {:dataset "LBMA",
     :ticker "GOLD",
     :date #inst "2020-05-01T00:00:00.000-00:00",
     :open nil,
     :average 1679.65M,
     :close 1686.25M,
     :volume nil}
    {:dataset "OPEC",
     :ticker "ORB-OIL",
     :date #inst "2020-05-07T00:00:00.000-00:00",
     :open nil,
     :average nil,
     :close 22.91M,
     :volume nil}
    {:dataset "OPEC",
     :ticker "ORB-OIL",
     :date #inst "2020-05-06T00:00:00.000-00:00",
     :open nil,
     :average nil,
     :close 22.4M,
     :volume nil}
    {:dataset "OPEC",
     :ticker "ORB-OIL",
     :date #inst "2020-05-05T00:00:00.000-00:00",
     :open nil,
     :average nil,
     :close 21.43M,
     :volume nil}
    {:dataset "OPEC",
     :ticker "ORB-OIL",
     :date #inst "2020-05-04T00:00:00.000-00:00",
     :open nil,
     :average nil,
     :close 18.36M,
     :volume nil}))
