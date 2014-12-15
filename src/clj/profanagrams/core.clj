(ns profanagrams.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.adapter.jetty :as jetty]
            [clojure.string :refer [split]]
            [profanagrams.trie :refer [build-trie]]
            [profanagrams.anagrams :refer [get-anagrams]]
            [hiccup.page :refer [html5 include-js include-css]]))

(def dirty-trie (delay (build-trie (-> "resources/badwords.dict" slurp (split #"\n")))))
(def clean-trie (delay (build-trie (-> "resources/words.dict" slurp (split #"\n")))))

(defn load-tries
  []
  @dirty-trie
  @clean-trie)

(defroutes handler
  (GET "/" [] (html5 [:head (include-css "css/screen.css")]
                     [:body
                      [:div {:id :root}]
                      (include-js "//cdnjs.cloudflare.com/ajax/libs/react/0.12.1/react.min.js")
                      (include-js "js/app.js")]))
  (GET "/anagrams" [input] 
       (when input (pr-str (get-anagrams @dirty-trie @clean-trie input)))))

(def app (-> handler
             (wrap-resource "public")
             (wrap-file-info)
             (wrap-params)))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "5000"))]
    (jetty/run-jetty app {:port port})))
