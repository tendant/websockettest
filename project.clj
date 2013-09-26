(defproject camera2 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [aleph "0.3.0"]
                 [lamina "0.5.0"]
                 [ring/ring-core "1.1.8"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler camera2.handler/app}
  :main camera2.handler
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
