#!/usr/bin/env clj

@mbp2:dev-resources $ clj
Clojure 1.10.0
user=> (def mstar [{:dataset "mstar" :ticker "abc"} {:dataset "mstar" :ticker "ghf"}])
#'user/mstar
user=> (def tingo [{:dataset "tingo" :ticker "xyz"} {:dataset "tingo" :ticker "abc"}])
#'user/tingo
user=> (map vector mstar tingo)
([{:dataset "mstar", :ticker "abc"} {:dataset "tingo", :ticker "xyz"}] [{:dataset "mstar", :ticker "ghf"} {:dataset "tingo", :ticker "abc"}])
