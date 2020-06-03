(ns website.pages.projects.vvc
 (:require 
  [reagent.core :as r]
  [reagent.dom :as rdom]
  [quil.core :as q]
  [quil.middleware :as m]
  ["prismjs/prism.js" :as prism]
  [website.pages.common :refer [link canvas]]))


(def title "Virtual Vehicle Challenge")
(def body-preview "A Virtual Reality Car Mechanic game")
(def date "2018, 2019")
(def body 
  [:div {:style {:font-size 18 :line-height 1.4}}
   "BODY"])

(def tag :vvc)
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
  (q/no-stroke)
  (let [d (Math/sin (first circles))
        c (Math/cos (second circles))]
    (apply q/fill [255 0 0])
    (q/rect (+ d 2) (+ (/ c 2.0) 30) 52 15)
    (q/quad (+ d 10) (+ (/ c 2.0) 17) (+ d 10) (+ (/ c 2.0) 42) (+ d 45) (+ (/ c 2.0) 42) (+ d 35) (+ (/ c 2.0) 17))
    (apply q/fill [100 100 100])
    (q/quad (+ d 15) (+ (/ c 2.0) 19) (+ d 15) (+ (/ c 2.0) 30) (+ d 38) (+ (/ c 2.0) 30) (+ d 34) (+ (/ c 2.0) 19))
 ; (q/rect 7 20 41 25 30 30 1 1)
    (apply q/fill [0 0 0])
    (q/ellipse (+ d 10) 45 10 10)
    (q/ellipse (+ d 45) 45 10 10)))

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


(def post {:name title
           :route (str "https://heliosinteractive.com/project/aapex/")
           :icon icon
           :title title
           :preview body-preview
           :panel panel
           :tag tag})