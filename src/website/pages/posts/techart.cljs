(ns website.pages.posts.techart
  (:require
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [quil.core :as q]
   [quil.middleware :as m]
   ["prismjs/prism.js" :as prism]
   [website.pages.common :refer [link canvas]]))


(def title "Tech Art Reel")
(def body-preview "A collection of my technical art and game development projects")
(def date "Ongoing")

(def body
  [:div {:style {:font-size 18 :line-height 1.4}}
   "BODY"])

(def tag :techart)
(def route)

(defn icon []
  [:img {:src "/turmolt.github.io/public/resources/Tech Art.png" :style {:width 60 :height 60 :margin 10 :margin-top 20 :float :left}}])

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
  (.scrollTo js/window 0 0)
  [:div
   {:class "f400"
    :style {:width 700 :margin :auto}}
   [:h2 title]
   [:p {:style {:padding 0 :font-size 10 :margin-top -15}} date]
   [:div body]])


(def post {:name "techart"
           :route (str "https://www.youtube.com/watch?v=SGoObzDR_Mg")
           :icon icon
           :title title
           :preview body-preview
           :panel panel
           :tag tag})
