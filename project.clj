(defproject profanagrams "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [ring "1.3.2"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler profanagrams.core/app
         :init profanagrams.core/load-tries}
  :profiles {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]})
