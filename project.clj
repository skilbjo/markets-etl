(defproject markets-etl "0.1.0-SNAPSHOT"
  :uberjar-name "markets-etl.jar"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "2.3.0"]
                 [clj-time "0.12.2"]
                 [environ "1.1.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/java.jdbc "0.5.8"]
                 [org.postgresql/postgresql "42.0.0"]]
  :profiles {:dev {:plugins [[lein-environ "1.1.0"]
                             [lein-cljfmt "0.5.6"]]}
             :uberjar {:aot :all}}
  :target-path "target/%s"
  :jvm-opts ["-Xms256m" "-Xmx256m" "-XX:MaxMetaspaceSize=128m"
             "-client" "-Duser.timezone=PST8PDT"
             "-Dclojure.compiler.direct-linking=true"
             "-XX:-OmitStackTraceInFastThrow"])
