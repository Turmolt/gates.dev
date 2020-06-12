(ns website.pages.projects.lbxcon
 (:require 
  [reagent.core :as r]
  [reagent.dom :as rdom]
  [quil.core :as q]
  [quil.middleware :as m]
  ["prismjs/prism.js" :as prism]
  [website.pages.common :refer [link canvas]]))


(def title "LBX Link Belt Excavators")
(def body-preview "Two tablet applications for customizing t-shirts and viewing excavators in AR")
(def date "June 2019")
(def body 
  [:div {:style {:font-size 18 :line-height 1.4}}
   "BODY"])

(def tag :lbx)
(def route "lbxcon")

(defonce icon-art (atom nil))

(defn icon-circle [id]
  (* (/ 3.1415927 3.0) id))

(defn icon-sketch-setup []
  (apply q/background [255 255 255])
  (map icon-circle (range 6)))

(defn icon-sketch-update [circles]
  (map #(+ 0.05 %) circles))

(defn icon-center [t]
  [(+ 30 (* 20 (Math/cos t))) (+ 30 (* 20 (Math/sin t)))])

(defn icon-draw [circles]
  (apply q/background [225 255 225])
  (apply q/fill [20 200 20])
  (q/ellipse 30 30 40 40)
  (q/no-fill)
  (q/stroke 255)
  (let [coords [[[10 10] [10 30] [40 13] [10 10]] 
                [[50 50] [25 49] [49 28] [50 45]]]]
    (q/curve 10 10 10 30 40 13 10 10)
    (q/curve 50 50 25 49 49 28 50 45)))

(defn icon []
  [:img {:src "/turmolt.github.io/public/resources/LBX.png" :style {:width 60 :height 60 :margin 10 :margin-top 20 :float :left}}])

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
           :route (str "https://heliosinteractive.com/project/lbx/")
           :icon icon
           :title title
           :preview body-preview
           :panel panel
           :tag tag})