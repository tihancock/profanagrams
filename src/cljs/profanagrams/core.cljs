(ns profanagrams.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [clojure.string :refer [lower-case join]]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [cljs.reader :refer [read-string]]))

(def ENTER 13)

(def anagrams (r/atom []))

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
      (go (let [result (<! (http/get (str "anagrams?input=" filtered-text) {:with-credentials? false}))
                new-anagrams (read-string (:body result))]
            (swap! anagrams (constantly new-anagrams)))))))

(defn page
  []
  [:div
   [:input {:id   :search-box
            :type :search
            :name :input}]
   [:ul
    (for [a @anagrams]
      [:li (join " " a)])]])

(r/render-component
 [page]
 (.getElementById js/document "root"))

(set! (.-onkeydown (.getElementById js/document "search-box")) search)
