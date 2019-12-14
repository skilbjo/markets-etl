(defproject markets-etl "0.1.0"
  :uberjar-name "app.jar"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [amazonica "0.3.151" :exclusions [com.google.protobuf/protobuf-java commons-logging com.fasterxml.jackson.core/jackson-databind com.amazonaws/aws-java-sdk com.fasterxml.jackson.core/jackson-core]]
                 [com.amazonaws/aws-lambda-java-core "1.2.0"]
                 [clj-http "3.10.0"]
                 [clj-time "0.15.2"]
                 [com.amazonaws/aws-java-sdk-kms "1.11.693" :exclusions [commons-logging org.apache.httpcomponents/httpclient com.fasterxml.jackson.core/jackson-databind com.fasterxml.jackson.core/jackson-core]]
                 [environ "1.1.0"]
                 [org.apache.orc/orc-core "1.4.0"]                      ; leave this pinned @ 1.4.0 due to orca
                 [org.apache.hadoop/hadoop-core "0.20.2-dev"]           ; for orca
                 [com.fasterxml.jackson.core/jackson-databind "2.5.5"]  ; for orca
                 [org.clojure/data.csv "0.1.4"]
                 [org.clojure/data.json "0.2.7"]
                 [org.clojure/java.jdbc "0.7.10"]
                 [org.clojure/tools.cli "0.4.2"]
                 [org.clojure/tools.logging "0.5.0"]
                 [org.slf4j/slf4j-log4j12 "1.7.29" :exclusions [log4j org.slf4j/slf4j-api]]
                 [org.postgresql/postgresql "42.2.9"]]
  :plugins [[lein-cloverage "1.0.10"]]
  :profiles {:dev {:dependencies [[criterium "0.4.5"]
                                  [org.clojure/tools.namespace "0.3.1"]]
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
