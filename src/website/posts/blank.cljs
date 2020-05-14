(ns website.posts.blank
 (:require 
  [reagent.core :as r]
  [reagent.dom :as rdom]
  [re-frame.core :as rf]
  [website.events :as events]
  [quil.core :as q]
  [quil.middleware :as m]
  ["prismjs/prism.js" :as prism]))


(def title "This is the title of the fancy first blog post")
(def body-preview "The body is very interesting")
(def body [:code {:class "language-clojure"
                 :ref (fn [n] (when n (prism/highlightElement n)))}
           "(+ 1 1) ;=> "
             "(str \"hello world\")"])

(def tag :blank)
(def route "blank")

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


(defn update-sketch
  [state]
  state)

(defn circle
  [id]
  {:id id
   :time 0
   :color [0 0 0]})

(defn setup-sketch
  []
  (apply q/background [255 255 255])
  (circle 0))

(defn center-scale
  [t]
  (+ 50 (* 20 t)))

(defn draw [state]
  (apply q/background [255 255 255])
  (apply q/fill [0 0 0])
  (let [time (:time state)]
    (q/ellipse (center-scale (Math/cos (/ time 2.0))) (center-scale (Math/sin time)) 10 10)))

(defn sketch-update
  [state]
  (assoc state :time (+ 0.05 (:time state))))




(defn canvas []
  (r/create-class
   {:component-did-mount
    (fn [component]
      (let [node (rdom/dom-node component)]
        (q/sketch
         :host node
         :setup #'setup-sketch
         :update #'sketch-update
         :size [100 100]
         :draw #'draw
         :middleware [m/fun-mode])))
    :render (fn [] [:div])}))

(defn panel []
  (set! (. js/document -title) title)
  [:div
   {:class "f400"
    :style {:width 700 :margin :auto}}
   [:h2 title]
   [:div body]
   [canvas]])


(def post {:name "blank"
           :preview preview
           :panel panel
           :tag tag
           :route route})
