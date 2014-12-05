(ns profanagrams.anagrams
  (:require [profanagrams.trie :refer [EOW]]))

(defn search
  [trie-root input-frequencies]
  (let [results (atom [])
        helper (fn f [trie freqs]
                 (doseq [[c subtrie] trie]
                   (let [frequency   (get freqs c)
                         terminal    (and frequency (get subtrie EOW))
                         new-freqs   (cond
                                      (not frequency) freqs
                                      (= frequency 1) (dissoc freqs c)
                                      :else           (assoc freqs c (dec frequency)))]
                     (when terminal (swap! results conj [terminal new-freqs]))
                     (when frequency (f subtrie new-freqs)))))]
    (helper trie-root input-frequencies)
    @results))

(defn get-anagrams
  [trie input-characters]
  (let [input-frequencies (frequencies input-characters)]
    ))
