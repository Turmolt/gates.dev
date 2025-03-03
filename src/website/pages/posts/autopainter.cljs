(ns website.pages.posts.autopainter
  (:require
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [quil.core :as q]
   [quil.middleware :as m]
   ["prismjs/prism.js" :as prism]
   [website.pages.common :refer [canvas]]))


(def title "Auto Painter")
(def body-preview "Painting with code")
(def date "05/15/2020")
(def body
  [:div {:class :about}
   "I made a website! I wanted a place to showcase things that I have worked on and to be able to talk about cool programming stuff, so here we are!" [:br] [:br] "I hope to create posts semi-regularly about things that I find interesting and maybe some of the information that I share will be of use to someone." [:br] [:br] "If not, at least I had fun along the way, right?"])

(def tag :autopainter)
(def route "autopainter")

(def w 360)
(def h 440)

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
  [:img {:src "/turmolt.github.io/public/resources/autopainter.png" :style {:width 60 :height 60 :margin 10 :margin-top 20 :float :left}}])

(defn preview []
  [:div {:class "f400"
         :style {:border-top "1px solid black"
                 :width "100%"
                 :height 100}}
   [icon]
   [:div {:style {:margin-top 25}}
    [:h3
     {:style {:margin "15px 0px 0px 0px"}}
     title]
    [:p {:style {:font-size 15
                 :margin "10px 0 0 0"
                 :font-style "italic"}}
     body-preview]]])


(def numcircles 350)

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
  (q/ellipse (+ (/ w 2) (/ (* id (Math/cos angle)) 2.0))
             (+ (/ h 2) (/ (* id (Math/sin angle)) 2.0))
             10 10))

(defn angle
  "calulate the angle based on the delta provided"
  [delta]
  (-> (q/frame-count)
      (* 0.000125)
      (* delta)))

(defn sketch-setup
  "Returns inital state of page for update-render loop"
  []
  (apply q/background (:background palette))
  (map circle (range 0 numcircles)))

(defn sketch
  "make art"
  [circles]
  (apply q/background (:background palette))
  (q/no-stroke)
  (doseq [c circles]
    (apply q/fill (:color c))
    (draw-circle (:id c)
                 (angle (:delta c)))))

(defn panel []
  (set! (. js/document -title) title)
  [:div
   {:class "f400"
    :style {:max-width 700 :margin :auto
            :padding 15}}
   [:h2 title]
   [:p {:class :date} date]
   [:div body]
   [:div {:style {:text-align :center :margin-left -15}}
    [canvas {:id "circles"
             :setup sketch-setup
             :update identity
             :size [w h]
             :draw sketch
             :atom art}]]])


(def post {:name route
           :route (str "https://turmolt.medium.com/auto-painter-6ae8f5948ee9")
           :icon icon
           :title title
           :preview body-preview
           :panel panel
           :tag tag})
