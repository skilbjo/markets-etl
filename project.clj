(defproject markets-etl "0.1.0"
  :uberjar-name "app.jar"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [clj-http "3.8.0"]
                 [clj-time "0.14.2"]
                 [environ "1.1.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/java.jdbc "0.7.5"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.slf4j/slf4j-log4j12 "1.7.25"]
                 [org.postgresql/postgresql "42.2.1"]]
  :plugins [[lein-cloverage "1.0.10"]]
  :profiles {:dev {:dependencies [[criterium "0.4.4"]]
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
