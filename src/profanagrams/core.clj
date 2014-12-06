(ns profanagrams.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [ring.middleware.params :refer [wrap-params]]
            [clojure.string :refer [split]]
            [profanagrams.trie :refer [build-trie]]
            [profanagrams.anagrams :refer [get-anagrams]]
            [hiccup.page :refer [html5]]))

(def dirty-trie (delay (build-trie (-> "resources/badwords.dict" slurp (split #"\n")))))
(def clean-trie (delay (build-trie (-> "resources/google-10000-english.txt" slurp (split #"\n")))))

(defn load-tries
  []
  @dirty-trie
  @clean-trie)

(defroutes r
  (GET "/" [] (html5 [:form {:action :anagrams
                             :method :get}
                      [:input {:type  :search
                               :name  :input}]]))
  (GET "/anagrams" [input] (when input (str (get-anagrams @dirty-trie @clean-trie input)))))

(def app (wrap-params r))
