(ns website.pages.projects.ohcrap
  (:require
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [quil.core :as q]
   [quil.middleware :as m]
   ["prismjs/prism.js" :as prism]
   [website.pages.common :refer [link iframe mobile]]))


(def title "Oh Crap!")
(def body-preview "A set of minigames created for Ludum Dare 46")
(def date "April 2020")

(defn icon []
  [:img {:src "/turmolt.github.io/public/resources/Oh Crap!/Oh Crap Logo.png" :style {:width 60 :height 60 :margin 10 :margin-top 20 :float :left}}])

(def body
  [:div {:style {:font-size 18 :line-height 1.4}}
   (if mobile
     [:div [:br] "Come back on a computer to play it!" [:br] [:br]] 
     [iframe {:src "/turmolt.github.io/public/resources/Oh Crap!/index.html"
              :name "Oh Crap!"}])
   [:br]
   "In “Oh Crap!” you will race against the clock to keep everyone alive! You are a top surgeon operating on your wife’s mother’s best friend and you can’t afford to slip up! Your uncle’s baby has gotten loose in his house and you have to make sure it doesn’t get into dangerous things! You are going for the sickest grind and everybody’s watching, you better not fall!"
   [:br]
   [:br]
   "Oh Crap! is a game made by myself and " [link "https://jons.website" "Jon Cole"] " in April 2020 for " [link "https://ldjam.com/events/ludum-dare/46/oh-crap" "Ludum Dare 46"] ". The theme of the jam was 'keep it alive' so we decided to make a series of minigames that the player would play and attempt to keep their streak alive." [:br] [:br] "You can find the source code on my " [link "https://github.com/Turmolt/ludum-dare-46" "GitHub repository"] "."])

(def tag :ohcrap)
(def route "oh-crap")

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
           :route (str "#/" route)
           :icon icon
           :title title
           :preview body-preview
           :panel panel
           :tag tag})
