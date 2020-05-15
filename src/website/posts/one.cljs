(ns website.posts.one
 (:require 
  [reagent.core :as r]
  [reagent.dom :as rdom]
  [re-frame.core :as rf]
  [website.events :as events]
  [quil.core :as q]
  [quil.middleware :as m]
  [website.posts.common :refer [link]]
  ["prismjs/prism.js" :as prism]))

(defn code [div]
  (r/create-class
   {:component-did-mount
    (fn [component]
      (let [node (rdom/dom-node component)]
        (prism/highlightElement node)))
    :render (fn [] div)}))

(def title "Creating and using custom reagent classes")
(def body-preview "The basics of creating a class in reagent")
(def body  [:div 
            "When creating a website, it is important for the developer to have control over the components and their behavior throughout their life cycle. "[link "http://reagent-project.github.io/" "Reagent"]" is a powerful library that allows a ClojureScript developer to define " [link "https://reactjs.org/" "React"] " components using just functions and data. Using a custom reagent class, we are able to easily change the functionality of our component as we see fit."[:br][:br] "Lets say we want to have syntax highlighting using the popular " [link "https://prismjs.com/" "Prism.js"] ". Since the elements that we create are not found in the DOM when it is initially loaded, they will not be correctly highlighted unless we invoke prism's " [code [:code "highlightElement"]] " function on the node."
            [:br][:br]
            [:pre [code [:code {:class "language-clojure line-numbers"}
                              "(defn code [div]\n"
                               "  {:component-did-mount\n"
                                "   (fn [component]\n"
                               "     (let [node (rdom/dom-node component)]\n"
                               "      (prism/highlightElement node)))\n"
                               "   :render (fn [] div)})"]]]])

(def tag :one)
(def route "one")

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
   [:p {:style {:padding 0 :font-size 10 :margin-top -15}} "05/15/2020"]
   [:div body]])


(def post {:name route
           :preview preview
           :panel panel
           :tag tag})
