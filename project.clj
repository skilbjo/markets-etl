(defproject markets-etl "0.1.0"
  :uberjar-name "app.jar"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.7.0"]
                 [clj-time "0.14.2"]
                 [environ "1.1.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/java.jdbc "0.7.3"]
                 [org.postgresql/postgresql "42.1.4"]]
  :plugins [[lein-cloverage "1.0.10"]]
  :profiles {:dev {:plugins [[com.holychao/parallel-test "0.3.1"]
                             [lein-environ "1.1.0"]
                             [lein-cljfmt "0.5.7"]]}
             :uberjar {:aot :all}}
  :target-path "target/%s"
  :jvm-opts ["-Duser.timezone=PST8PDT"
             ; Same JVM options as deploy/bin/run-job uses in production
             "-Xms256m"
             "-Xmx2g"
             "-XX:MaxMetaspaceSize=128m"
             ; https://clojure.org/reference/compilation
             "-Dclojure.compiler.direct-linking=true"
             ; https://stackoverflow.com/questions/4659151/recurring-exception-without-a-stack-trace-how-to-reset
             "-XX:-OmitStackTraceInFastThrow"])
