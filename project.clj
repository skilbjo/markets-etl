(defproject markets-etl "0.1.0"
  :uberjar-name "app.jar"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clj-http "3.9.1"]
                 [clj-time "0.15.1"]
                 [com.climate/claypoole "1.1.4"]
                 [environ "1.1.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [org.clojure/tools.cli "0.4.1"]
                 [org.clojure/tools.logging "0.4.1"]
                 [org.slf4j/slf4j-log4j12 "1.7.26"]
                 [org.postgresql/postgresql "42.2.5"]]
  :plugins [[lein-cloverage "1.0.10"]]
  :profiles {:dev {:dependencies [[criterium "0.4.4"]
                                  [org.clojure/tools.namespace "0.2.11"]]
                   :plugins [[lein-environ "1.1.0"]
                             [lein-cljfmt "0.5.7"]]}
             :uberjar {:aot :all}}
  :target-path "target/%s"
  :jvm-opts ["-Duser.timezone=UTC"
             ; Same JVM options as deploy/bin/run-job uses in production
             "-Xms256m"
             "-Xmx2g"
             "-XX:MaxMetaspaceSize=128m"
             ; https://clojure.org/reference/compilation
             "-Dclojure.compiler.direct-linking=true"
             ; https://stackoverflow.com/questions/4659151/recurring-exception-without-a-stack-trace-how-to-reset
             "-XX:-OmitStackTraceInFastThrow"])
