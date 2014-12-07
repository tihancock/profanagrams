(ns profanagrams.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [clojure.string :refer [split]]
            [profanagrams.trie :refer [build-trie]]
            [profanagrams.anagrams :refer [get-anagrams]]
            [hiccup.page :refer [html5 include-js]]))

(def dirty-trie (delay (build-trie (-> "resources/badwords.dict" slurp (split #"\n")))))
(def clean-trie (delay (build-trie (-> "resources/google-10000-english.txt" slurp (split #"\n")))))

(defn load-tries
  []
  @dirty-trie
  @clean-trie)

(defroutes handler
  (GET "/" [] (html5 [:body
                      [:div {:id :root}]
                      (include-js "http://fb.me/react-0.11.1.js")
                      (include-js "js/app.js")]))
  (GET "/anagrams" [input] 
       (when input (pr-str (get-anagrams @dirty-trie @clean-trie input)))))

(def app (-> handler
             (wrap-resource "public")
             (wrap-file-info)
             (wrap-params)))