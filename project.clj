(defproject live-math-pet "1"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [helpers "1"]
                 [quil "2.6.0"]
                 [clj-time "0.14.0"]
                 [org.clojure/core.async "0.3.443"]
                 [seesaw "1.4.5"]]

  :main live-math-pet.main

  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}})
