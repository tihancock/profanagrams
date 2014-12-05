(ns profanagrams.trie
  (:require [clojure.string :refer [lower-case split]]))

(def EOW "~")

(defn add-word-to-trie
  [trie word]
  (let [characters (seq word)
        existing (get-in trie characters)]
    (assoc-in trie characters (assoc existing EOW word))))

(defn build-trie
  [words]
  (let [lower-words (map lower-case words)]
    (reduce add-word-to-trie {} words)))
