(ns profanagrams.trie
  (:require [clojure.string :refer [lower-case split]]))

(def min-word-len 3)

(def EOW "~")

(defn add-word-to-trie
  [trie word]
  (let [characters (seq word)
        existing (get-in trie characters)]
    (assoc-in trie characters (assoc existing EOW word))))

(defn build-trie
  [words]
  (let [lower-words (->> words
                        (filter #(>= (count %) min-word-len))
                        (map lower-case))]
    (reduce add-word-to-trie {} lower-words)))
