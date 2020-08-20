(ns website.pages.agingnav
 (:require 
  [reagent.core :as r]
  [reagent.dom :as rdom]
  [quil.core :as q]
  [quil.middleware :as m]
  ["prismjs/prism.js" :as prism]
  [website.pages.common :refer [link canvas]]))


(def title "Aging Indoor Navigation")
(def body-preview "A non-invasive tracking system designed to empower older adults to live independently")
(def date "August 2014")
(def body 
  [:div {:style {:font-size 18 :line-height 1.4}}
   "BODY"])

(def tag :blank)
(def route "blank")

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
  (apply q/background [255 255 255])
  (q/ellipse 30 30 10 10)
  (doseq [t circles]
    (let [[x y] (icon-center t)]
      (q/line [x y] [(+ 15 (/ x 2)) (- 30 (/ y 2))]))))

(defn icon []
  [:div {:style {:width 60 :height 60 :margin 10 :margin-top 20 :float :left}}
   [canvas {:id "icon-one"
            :setup icon-sketch-setup
            :size [60 60]
            :update icon-sketch-update
            :draw icon-draw
            :atom icon-art}]])

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
           :route (str "#/" route)
           :icon icon
           :title title
           :preview body-preview
           :panel panel
           :tag tag})