(ns website.posts.blank
 (:require 
  [reagent.core :as r]
  [reagent.dom :as rdom]
  [quil.core :as q]
  [quil.middleware :as m]
  ["prismjs/prism.js" :as prism]))


(def title "This is the title of the fancy first blog post")
(def body-preview "The body is very interesting")
(def body 
  [:div {:style {:font-size 17 :line-height 1.3}} "I have made a website! I wanted a place to showcase things that I have worked on and to be able to talk about cool programming stuff, so here we are!"
   [:pre [:code {:class "language-clojure line-numbers"
                 :ref (fn [n] (when n (prism/highlightElement n)))}
          "(defn over-25? [& rest] \n (filter #(> 25 (:age %)) rest)) \n"
          "(str \"hello world\")"]]])



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


(def numcircles 550)

(def palette
  "a palette of colors"
  {:name        "Golden Ratio"
   :background  [255 255 255]
   :colors      [[100 0 0]
                 [200 100 50]
                 [250 200 100]
                 [90 8 0]]})

(defonce art (atom nil))

(defn rand-color
  "returns a random color from the palette"
  []
  (rand-nth (:colors palette)))

(defn circle
  "creates a map of randomly colored circles"
  [id]
  {:id     id
   :delta  id
   :color  (rand-color)})

(defn draw-circle
  "draws a circle at the position calculated by its angle from start and id"
  [id angle]
  (q/ellipse (+ (/ 700 2) (* id (Math/cos angle)))
             (+ (/ 700 2) (* id (Math/sin angle)))
             10 10))

(defn angle
  "calulate the angle based on the delta provided"
  [delta]
  (-> (q/frame-count)
      (* 0.0001)
      (* delta)))

(defn sketch-setup
  "Returns inital state of page for update-render loop"
  []
  (apply q/background (:background palette))
  (map circle (range 0 numcircles)))

(defn sketch-update
  "update the state each frame"
  [state]
  (map #(assoc %
               :delta (+ 0.01 (- (:id %)  (:time %))))
       state))

(defn sketch
  "make art"
  [circles]
  (apply q/background (:background palette))
  (q/no-stroke)
  (doseq [c circles]
    (apply q/fill (:color c))
    (draw-circle (:id c)
                 (angle (:delta c)))))


(defn canvas []
  (r/create-class
   {:component-did-mount
    (fn [component]
      (let [node (rdom/dom-node component)]
        (set! art (q/sketch
                   :id "circles"
                   :host node
                   :setup #'sketch-setup
                   :update #'sketch-update
                   :size [700 700]
                   :draw #'sketch
                   :middleware [m/fun-mode]))))
    :component-will-unmount
    #(q/with-sketch art (q/exit))
    :render (fn [] [:div])}))

(defn panel []
  (set! (. js/document -title) title)
  [:div
   {:class "f400"
    :style {:width 700 :margin :auto}}
   [:h2 title]
   [:div body]
   [canvas]])


(def post {:name route
           :preview preview
           :panel panel
           :tag tag})
