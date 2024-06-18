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
  (apply q/background [200 200 200])
  (q/no-stroke)
  (apply q/fill [100 100 100])
  (q/rect 0 40 60 20)
  (apply q/fill [200 200 0])
  (let [f (first circles)
        d (Math/sin f)
        c (Math/cos (second circles))]
    (q/rect (+ -10 (mod (* -25 f) 75)) 50 10 3)
    (q/rect (+ -10 (mod (* -25 (+ 1 f)) 75)) 50 10 3)
    (q/rect (+ -10 (mod (* -25 (+ 2 f)) 75)) 50 10 3)
    (apply q/fill [255 0 0])
    (q/rect (+ d 4) (+ (/ c 1.5) 30) 52 15)
    (q/quad (+ d 14) (+ (/ c 2.0) 17) (+ d 12) (+ (/ c 2.0) 42) (+ d 47) (+ (/ c 2.0) 42) (+ d 37) (+ (/ c 2.0) 17))
    (apply q/fill [100 100 100])
    (q/quad (+ d 16) (+ (/ c 2.0) 19) (+ d 15) (+ (/ c 2.0) 30) (+ d 40) (+ (/ c 2.0) 30) (+ d 36) (+ (/ c 2.0) 19))
    (apply q/fill [0 0 0])
    (q/ellipse (+ d 12) 45 10 10)
    (q/ellipse (+ d 47) 45 10 10)))

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
           :route (str "https://www.youtube.com/embed/7KjxD6miMPY")
           :icon icon
           :title title
           :preview body-preview
           :panel panel
           :tag tag})