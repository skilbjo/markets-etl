(defproject market-etl "0.1.0-SNAPSHOT"
  :uberjar-name "market-etl.jar"
  :repositories [["atlassian"   {:url "https://maven.atlassian.com/content/repositories/atlassian-3rdparty/"}]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clojure.java-time "0.2.2"]
                 [clj-time "0.12.2"]
                 [org.clojure/java.jdbc "0.5.8"]
                 [org.postgresql/postgresql "9.4.1209"]
                 [environ "1.1.0"]]
  :plugins [[lein-environ "1.1.0"]]
  :target-path "target/%s"
  :jvm-opts ["-Xmx1g" "-server" "-Duser.timezone=UTC"])
