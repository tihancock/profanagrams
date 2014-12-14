(ns profanagrams.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:#container {:height "100%"
                :width "100%"}]

  [:#search-box {:position :absolute
                 :width "40%"
                 :height "100px" ;; FIXME
                 :font-size "75px"
                 :top "10%"
                 :left "30%"
                 :right "30%"}]

  [:#anagram-container {:position :absolute
                        :width "40%"
                        :top "calc(10% + 150px)";; FIXME
                        :left "30%"
                        :right "30%"
                        :font-size "30px"}]

  [:.anagram {:text-align :center
              :list-style-type :none}])
