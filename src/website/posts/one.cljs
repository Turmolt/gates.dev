(ns website.posts.one
 (:require 
  [reagent.core :as r]
  [reagent.dom :as rdom]
  [quil.core :as q]
  [quil.middleware :as m]
  [website.posts.common :refer [link code]]))

(def title "Creating and using custom reagent classes")
(def body-preview "The basics of creating a react component using reagent")
(def body  [:div {:style {:font-size 18
                          :line-height 1.4}}
            "When creating a website, it is important for the developer to have control over the components and their behavior throughout their life cycle. "[link "http://reagent-project.github.io/" "Reagent"]" is a powerful library that allows a ClojureScript developer to define " [link "https://reactjs.org/" "React"] " components using just functions and data. Using a custom reagent class, we are able to easily change the functionality of our component as we see fit."[:br][:br] "Lets say we want to have syntax highlighting using the popular " [link "https://prismjs.com/" "Prism.js"] ". Since the elements that we create are not found in the DOM when it is initially loaded, they will not be correctly highlighted unless we invoke prism's " [code "language-clojure" "highlightElement"] " function on each node.  It is worth noting that we could accomplish this by subscribing to the " [link "https://reactjs.org/docs/refs-and-the-dom.html" "ref"] " hook and setting it up to invoke prism there."
            [:br][:br]
            [:pre [code "language-clojure line-numbers"
                   "[:code {:class \"language-clojure\"\n"
                   "        :ref #(when % (prism/highlightElement %)))}\n"
                   "\"(+ 1 1)\"]"]]
            [:div {:style {:text-align :center}}
             [code "language-clojure" "(+ 1 1)"]]
            
            [:br]
            "In this approach we are checking that the element is not null, and then invoking prism on it once we know that we have a node. This is a fine way to do this, but we are subscribing to both the mounting and unmounting of our component and have to catch the unmounting callback and ignore it. In order to avoid the extra subscription, we can use reagent's " [code "language-clojure" "create-class"] " function and just hook into what we need; the "[code "language-clojure" ":component-did-mount"] " and " [code "language-clojure" ":render"]" callbacks."
            [:br][:br]
            
            [:pre [code "language-clojure line-numbers"
                   "(defn code [features & text]\n"
                   " (r/create-class\n"
                   "  {:component-did-mount\n"
                   "   (fn [component]\n"
                   "     (let [node (rdom/dom-node component)]\n"
                   "      (prism/highlightElement node)))\n"
                   "   :render (fn [] [:code {:class features} text])}))"]]
            [:br]
            "We can now render the component by passing the prism features and some code to our custom reagent class. We need to include the language tags and the styling features that we want enabled when prism acts on it."
            [:br][:br]
            [:div {:style {:text-align :center}} [code "language-clojure" "[code \"language-clojure\" \"(+ 1 1)\"]"]]
            [:br]
            "This allows us to add syntax highlighting on the fly in an easily reusable way, while avoiding uneccessary subscriptions and bloat code that catches unwanted behavior."
            [:br]
            [:br]
                        
            ])


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
