(ns profanagrams.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [clojure.string :refer [lower-case join]]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [cljs.reader :refer [read-string]]))

(def ENTER 13)

(def anagrams (r/atom []))

(def empty-anagrams
  ["Sorry, there are no results"])

(defn parse-anagrams
  [response]
  (let [new-anagrams (read-string (:body response))]
    (if (empty? new-anagrams)
      empty-anagrams
      new-anagrams)))

(defn search [e]
  (let [keycode       (.-keyCode e)
        search-text   (-> js/document
                          (.getElementById "search-box")
                          .-value)
        filtered-text (->> search-text
                           lower-case
                           (filter #(<= \a % \z))
                           (apply str))]
    (if (= ENTER keycode)
      (go (let [request (http/get (str "anagrams?input=" filtered-text) {:with-credentials? false})]
            (swap! anagrams (constantly []))
            (swap! anagrams (constantly (parse-anagrams (<! request)))))))))

(defn page
  []
  [:div {:id :container}
   [:input {:id   :search-box
            :name :input}]
   [:div {:id :anagram-container}
    [:ul
     (for [a @anagrams]
       [:li {:class :anagram} (join " " a)])]]])

(r/render-component
 [page]
 (.getElementById js/document "root"))

(set! (.-onkeydown (.getElementById js/document "search-box")) search)
