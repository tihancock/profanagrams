(ns profanagrams.anagrams
  (:require [profanagrams.trie :refer [EOW]]))

(defn get-dirty-words
  [dirty-trie input-frequencies results]
  (for [[c subtrie] dirty-trie]
    (let [frequency   (get input-frequencies c)
          terminal    (and frequency (get subtrie EOW))
          new-freqs   (cond
                       (not frequency) input-frequencies
                       (= frequency 1) (dissoc input-frequencies c)
                       :else           (assoc input-frequencies c (dec frequency)))
          new-results (if terminal
                        (conj results [new-freqs terminal])
                        results)]
      (if frequency 
        (get-dirty-words subtrie
                         new-freqs 
                         new-results)
        new-results))))

(defn search
  [trie-root input-frequencies partial-result]
  (let [results (atom #{})
        helper (fn f [trie freqs partial]
                 (doseq [[c subtrie] trie]
                   (let [frequency   (get freqs c)
                         terminal    (and frequency (get subtrie EOW))
                         new-freqs   (cond
                                      (not frequency) freqs
                                      (= frequency 1) (dissoc freqs c)
                                      :else           (assoc freqs c (dec frequency)))]
                     (cond
                      (and terminal (empty? new-freqs)) (swap! results conj [:full (conj partial terminal)])
                      terminal                          (f trie-root new-freqs (conj partial terminal))
                      frequency                         (f subtrie new-freqs partial)
                      :else                             (when (not-empty partial) ;; FIXME - not very efficient
                                                          (swap! results conj [:partial partial]))))))]
    (helper trie-root input-frequencies #{partial-result})
    @results))

(defn get-anagrams
  [dirty-trie clean-trie input-characters]
  (let [input-frequencies (frequencies input-characters)
        dirty-results (->> (get-dirty-words dirty-trie input-frequencies [])
                           flatten
                           (partition 2))
        full-results (for [[freqs partial-result] dirty-results]
                       (search clean-trie freqs partial-result))]
    (apply clojure.set/union full-results)))
