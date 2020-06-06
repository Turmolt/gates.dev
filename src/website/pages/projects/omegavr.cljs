(ns website.pages.projects.omegavr
  (:require
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [quil.core :as q]
   [quil.middleware :as m]
   ["prismjs/prism.js" :as prism]
   [website.pages.common :refer [link iframe width height]]))


(def title "Omega VR")
(def body-preview "A Virtual Reality adventure that takes you on a trip to the moon")
(def date "May 2017")

(defn icon []
  [:img {:src "/turmolt.github.io/public/resources/OmegaVR Icon.png" :style {:width 60 :height 60 :margin 10 :margin-top 20 :float :left}}])


(def body
  [:div {:style {:font-size 18 :line-height 1.4}}
   [iframe {:src "https://www.youtube.com/embed/R3st99Iy6Nw"
            :name "Omega VR"}]
   [:br]
   [:div {:style {:text-align :center}}
    "Click and drag to look around!"] [:br]
   [link "https://www.omegawatches.com/en-us/" "Omega"]
   ", the Swiss luxury watch brand, wanted to give their valued customers an adventure like they had never seen before. Space travel is every child's dream, and landing on the moon is one of the greatest achievements of mankind, so we highlighted both of these things in this thrilling experience." [:br] [:br] "Omega VR is a virtual reality experience for the HTC Vive. It was taken on tour across China and shown to thousands of customers of the Omega brand."])


(def tag :omegavr)
(def route "omegavr")

(defn panel []
  (set! (. js/document -title) title)
  [:div
   {:class "f400"
    :style {:max-width 700 :margin :auto
            :padding 15}}
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
