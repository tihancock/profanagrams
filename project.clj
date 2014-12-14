(defproject profanagrams "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [compojure "1.3.1"]
                 [ring "1.3.2"]
                 [hiccup "1.0.5"]
                 [reagent "0.4.2"]
                 [cljs-http "0.1.17"]
                 [garden "1.2.1"]]
  :plugins [[lein-ring "0.8.13"]
            [lein-garden "0.2.0"]
            [lein-cljsbuild "1.0.3"]]
  :source-paths ["src/clj"]
  :garden {:builds [{:stylesheet profanagrams.css/screen
                     :compiler {:output-to "resources/public/css/screen.css"
                                :pretty-print? true}}]}
  :cljsbuild {:builds [{:source-paths ["src/cljs"]
                        :compiler {:output-to "resources/public/js/app.js"
                                   :output-dir "resources/public/js/out"
                                   :source-map "resources/public/js/app.js.map"
                                   :optimizations :advanced}}]}
  :main profanagrams.core
  :ring {:handler profanagrams.core/app
         :init profanagrams.core/load-tries}
  :profiles {:dependencies [[javax.servlet/servlet-api "2.5"]
                            [ring-mock "0.1.5"]]}
  :uberjar {:aot :all}
  :min-lein-version "2.0.0")
