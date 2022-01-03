(ns website.pages.projects.mercedestennis
 (:require 
  [reagent.core :as r]
  [reagent.dom :as rdom]
  [quil.core :as q]
  [quil.middleware :as m]
  ["prismjs/prism.js" :as prism]
  [website.pages.common :refer [link canvas]]))


(def title "US Open Mixed Reality Tennis")
(def body-preview "A mixed reality multiplayer tennis game that was showcased at the US Open")
(def date "June 2019")
(def body 
  [:div {:style {:font-size 18 :line-height 1.4}}
   "BODY"])

(def tag :mercedes)
(def route "Mercedes Tennis")

(defn icon []
  [:img {:src "/turmolt.github.io/public/resources/US_Open.png" :style {:width 60 :height 60 :margin 10 :margin-top 20 :float :left}}])

(defn preview []
  [:div {:class "f400"
         :style {:border-top "1px solid black"
                 :width "100%"
                 :height 100}}
   [:h3
   {:style {:margin "15px 0px 0px 0px"}} 
    title]
   [:p {:style {:font-size 15
                :margin "10px 0 0 0"
                :font-style "italic"}}
    body-preview]])


(defn panel []
  (set! (. js/document -title) title)
  [:div
   {:class "f400"
    :style {:width 700 :margin :auto}}
   [:h2 title]
   [:p {:style {:padding 0 :font-size 10 :margin-top -15}} date]
   [:div body]])


(def post {:name route
           :route (str "https://heliosinteractive.com/project/eq-tennis/")
           :icon icon
           :title title
           :preview body-preview
           :panel panel
           :tag tag})