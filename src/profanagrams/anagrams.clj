(ns profanagrams.anagrams
  (:require [profanagrams.trie :refer [EOW]]))

(defn search
  [trie input-frequencies results]
  (for [[c subtrie] trie]
    (let [frequency   (get input-frequencies c)
          terminal    (and frequency (get subtrie EOW))
          new-freqs   (cond
                       (not frequency) input-frequencies
                       (= frequency 1) (dissoc input-frequencies c)
                       :else           (assoc input-frequencies c (dec frequency)))
          new-results (if terminal
                        (conj results [terminal new-freqs])
                        results)]
      (if frequency
        (search subtrie new-freqs new-results)
        new-results))))

(defn get-anagrams
  [trie input-characters]
  (let [input-frequencies (frequencies input-characters)]
    ))
