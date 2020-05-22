(ns website.pages.projects.gardener
  (:require
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [quil.core :as q]
   [quil.middleware :as m]
   ["prismjs/prism.js" :as prism]
   [website.pages.common :refer [link iframe mobile]]))


(def title "The Gardener")
(def body-preview "A Stardew Valley clone created for Ludum Dare 44")
(def date "April 2019")

(defn icon []
  [:img {:src "/turmolt.github.io/public/resources/The Gardener/Gardener.png" :style {:width 60 :height 60 :margin 10 :margin-top 20 :float :left}}])

(def body
  [:div {:style {:font-size 18 :line-height 1.4}}
   (if mobile 
     [:div [:br]"Come back on a computer to play it!" [:br][:br]]
     [iframe {:src "/turmolt.github.io/public/resources/The Gardener/index.html"
              :name "The Gardener"}])
   [:br]
   "The Gardener is a game that I made in April 2019 for" [link "https://ldjam.com/events/ludum-dare/44/the-gardener" "Ludum Dare 44"] ". The theme of the jam was 'your life is currency' so I made a Stardew Valley clone where the farmer uses his life force to grow and sell body parts." [:br] [:br] "You can find the source code on my " [link "https://github.com/Turmolt/TheGardener" "GitHub repository"] "."])

(def tag :gardener)
(def route "gardener")

(defn panel []
  (set! (. js/document -title) title)
  [:div
   {:class "f400"
    :style {:max-width 700 :margin :auto
            :padding 15}}
   [:h2 title]
   [:p {:style {:padding 0 :font-size 10 :margin-top -15}} date]
   [:div body]])

(def post {:name route
           :icon icon
           :title title
           :preview body-preview
           :panel panel
           :tag tag})
