(ns profanagrams.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [ring.middleware.params :refer [wrap-params]]
            [clojure.string :refer [split]]
            [profanagrams.trie :refer [build-trie]]
            [profanagrams.anagrams :refer [get-anagrams]]))

(def dirty-trie (delay (build-trie (-> "resources/badwords.dict" slurp (split #"\n")))))
(def clean-trie (delay (build-trie (-> "resources/google-10000-english.txt" slurp (split #"\n")))))

(defn load-tries
  []
  @dirty-trie
  @clean-trie)

(defroutes r
  (GET "/" [] "<h1>Hello World</h1>")
  (GET "/anagrams" [input] (when input (str (get-anagrams @dirty-trie @clean-trie input)))))

(def app (wrap-params r))
