(ns profanagrams.trie-test
  (require [clojure.test :refer :all]
           [profanagrams.trie :refer :all]))

(deftest add-word-to-trie-test
  (testing "can add a word to the trie"
    (is (= (add-word-to-trie {} "hello")
           {\h {\e {\l {\l {\o {"~" nil}}}}}})))
  
  (testing "don't overwrite existing terms"
    (is (= (add-word-to-trie {\h {\e {\l {\l {\o {"~" nil}}}}}} "hell")
           {\h {\e {\l {\l {"~" nil \o {"~" nil}}}}}}))))
