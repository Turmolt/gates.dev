(ns website.posts.one
 (:require 
  [reagent.core :as r]
  [reagent.dom :as rdom]
  [quil.core :as q]
  [quil.middleware :as m]
  [website.posts.common :refer [link code canvas]]))

(def title "Creating and using custom reagent classes")
(def body-preview "The basics of creating a react component using reagent")
(def tag :one)
(def route "reagent-custom-classes")
(def date "05/19/2020")

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


;===============================================================================================

(def w 700)
(def h 200)

(defn circle [id]
  {:time (* 0.5 id)
   :radius (* 3.0 id)})

(defonce art (atom nil))

(defn sketch-setup []
  (apply q/background [255 255 255])
  (map circle (range 1 7)))

(defn sketch-update [state]
  (map #(assoc % :time (+ 0.05 (:time %))) state))

(defn get-coords [t scale]
  [(+ (/ w 2.0) (* scale (Math/cos t)))
   (+ (/ h 2.0) (* scale (Math/sin t)))])

(defn draw-circle
  [{t :time radius :radius}]
  (let [[x y] (get-coords t 50)]
    (q/ellipse x y radius radius)))

(defn draw [state]
  (apply q/background [255 255 255])
  (apply q/fill [0 0 0])
  (doseq [c state]
    (draw-circle c)))

;===============================================================================================

(def body  [:div {:style {:font-size 18
                          :line-height 1.4}}
            "When creating a website, it is important for the developer to have control over the components and their behavior throughout their life cycle. " [link "http://reagent-project.github.io/" "Reagent"] " is a powerful library that allows a ClojureScript developer to define " [link "https://reactjs.org/" "React"] " components using just functions and data. Using a custom reagent class, we are able to easily change the functionality of our component as we see fit." [:br] [:br] "Lets say we want to have syntax highlighting using the popular " [link "https://prismjs.com/" "Prism.js"] ". Since the elements that we create with Reagent are not found in the DOM when it is initially loaded, they will not be correctly highlighted unless we invoke prism's " [code "language-clojure" "highlightElement"] " function on each node.  It is worth noting that we could accomplish this by subscribing to the " [link "https://reactjs.org/docs/refs-and-the-dom.html" "ref"] " hook and setting it up to invoke prism there."
            [:br] [:br]
            [:pre [code "language-clojure line-numbers"
                   "[:code {:class \"language-clojure\"\n"
                   "        :ref #(when % (prism/highlightElement %)))}\n"
                   "\"(+ 1 1)\"]"]]
            [:div {:style {:text-align :center}}
             [code "language-clojure" "(+ 1 1)"]]

            [:br]
            "In this approach we are checking that the element is not null, and then invoking prism on it once we know that we have a node. This is a fine way to do this, but we are subscribing to both the mounting and unmounting of our component and have to catch the unmounting callback and ignore it. In order to avoid the extra subscription, we can use reagent's " [code "language-clojure" "create-class"] " function and just hook into what we need; the " [code "language-clojure" ":component-did-mount"] " and " [code "language-clojure" ":render"] " callbacks."
            [:br] [:br]

            [:pre [code "language-clojure line-numbers"
                   "(defn code [features & text]\n"
                   "  (r/create-class\n"
                   "   {:component-did-mount\n"
                   "    (fn [component]\n"
                   "      (let [node (rdom/dom-node component)]\n"
                   "       (prism/highlightElement node)))\n"
                   "    :render (fn [] [:code {:class features} text])}))"]]
            [:br]
            "When the component is created and mounted, our code will be executed and will apply the correct styling to our code block. We can now render the component by passing the prism features and some code to our custom reagent class. We need to include the language tags and the styling features that we want enabled when prism acts on it."
            [:br] [:br]
            [:div {:style {:text-align :center}} [:pre [code "language-clojure line-numbers" "[code \"language-clojure\" \"(+ 1 1)\"]"]]]
            [:br]
            "This allows us to add syntax highlighting on the fly in an easily reusable way, while avoiding uneccessary subscriptions and bloat code that catches unwanted behavior."
            [:br] [:br]
            "Great! Lets see what else we can do with our newfound powers. Lets render " [link "http://quil.info" "Quil"] " on a reagent canvas when we mount, and clean up after ourselves when we unmount. To do this we can use another one of the " [link "https://reactjs.org/docs/react-component.html" "react lifecycle methods"] ", " [code "language-clojure" ":component-will-unmount"] "."

            [:br] [:br]
            [:pre [code "language-clojure line-numbers"
                   "(defn canvas\n"
                   "  [{id :id setup :setup update :update size :size draw :draw art :atom}]\n"
                   "  (r/create-class\n"
                   "   {:component-did-mount\n"
                   "    (fn [component]\n"
                   "     (let [node (rdom/dom-node component)]\n"
                   "      (set! art (q/sketch\n"
                   "                 :id id\n"
                   "                 :host node\n"
                   "                 :setup setup\n"
                   "                 :update update\n"
                   "                 :size size\n"
                   "                 :draw draw\n"
                   "                 :middleware [m/fun-mode]))))\n"
                   "    :component-will-unmount\n"
                   "    #(q/with-sketch @art (q/exit))\n"
                   "    :render (fn [] [:div])}))"]]
            [:br]
            "In this example, we are telling Quil to draw a sketch on the node that we are creating. We supply the functions that Quil will use to create our image, and we also pass an atom that will store a reference to the sketch that we create so we can clean it up when it unmounts. If we do not exit the sketch then it will continue to eat up processing power until the user reloads or navigates off of our web page. By storing the reference in an atom we can make sure to exit all unused sketches, ensuring that the user will have the best possible experience while navigating your website."
            [:br] [:br]
            "Awesome! Lets use our new function and draw some moving circles. To do this we will need to set up the functions that Quil will use to draw our art piece. We can start by defining some of the parameters and data containers that we will use for our sketch."
            [:br] [:br]
            [:pre [code "language-clojure line-numbers"
                   "(def w 200)\n(def h 200)\n\n"
                   "(defonce art (atom nil))\n\n"
                   "(def circle [id]\n"
                   "  {:time   (* 0.5 id)\n"
                   "   :radius (* 3.0 id)})\n"]]
            [:br]
            "We define the width, height and an atom to use for our canvas. The starting time and radius for each circle is calculated based on its " [code "language-clojure" "id"] ". Next we will define the setup and update functions."
            [:br] [:br]
            [:pre [code "language-clojure line-numbers"
                   "(defn sketch-setup []\n"
                   "  (apply q/background [255 255 255])\n"
                   "  (map circle (range 1 7)))\n\n"

                   "(defn sketch-update [state]\n"
                   " (map #(assoc % :time (+ 0.05 (:time %))) state))"]]
            [:br]
            "In the setup we are painting the background white and creating our circles, then in the update function we are increasing the time value that each circle contains. The circles position will be calculated based on its time, so lets create a function that will return an x and y coordinate when passed a time value, and we will add an extra parameter to scale things up."
            [:br] [:br]
            [:pre [code "language-clojure line-numbers"
                   "(defn get-coords [t scale]\n"
                   "   [(+ (/ w 2.0) (* scale (Math/cos t)))\n"
                   "    (+ (/ h 2.0) (* scale (Math/sin t)))])"]]
            [:br]
            "Using some math we calculate our x and y coordinates and make sure that they are scaled and centered to our liking. Finally we need to create the functions that will consume the data we have created and draw the circles on our canvas."
            [:br] [:br]
            [:pre [code "language-clojure line-numbers"
                   "(defn draw-circle\n"
                   "  [{t :time radius :radius}]\n"
                   "  (let [[x y] (get-coords t 50)]\n"
                   "    (q/ellipse x y radius radius)))\n\n"
                   "(defn draw [state]\n"
                   "  (apply q/background [255 255 255])\n"
                   "  (apply q/fill [0 0 0])\n"
                   "  (doseq [c state]\n"
                   "    (draw-circle c)))"]]
            
            [:br]
            "Now if we put all the pieces together, we will see our art in all of its glory."
            [:br]
            [:br]
            [:pre [code "language-clojure line-numbers"
                   "[canvas {:id \"circles\"\n"
                   "         :setup sketch-setup\n"
                   "         :size [w h]\n"
                   "         :update sketch-update\n"
                   "         :draw draw\n"
                   "         :atom art}]"]]
            
            [canvas {:id "circles"
                     :setup sketch-setup
                     :size [w h]
                     :update sketch-update
                     :draw draw
                     :atom art}]
            "Exquisite! I hope that this walkthrough has helped you gain a better understanding of how we can utilize the tools that Reagent and React have provide us to bend the web to our will. Remember, with great power comes great responsibility. Now go forth and create!"])



(defn panel []
  (set! (. js/document -title) title)
  [:div
   {:class "f400"
    :style {:width 700 :margin :auto}}
   [:h2 title]
   [:p {:style {:padding 0 :font-size 10 :margin-top -15}} date]
   [:div body]])

(def post
  {:name route
   :preview preview
   :panel panel
   :tag tag})
