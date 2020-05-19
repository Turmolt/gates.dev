(ns website.pages
 (:require [re-frame.core :as rf]
           [website.posts.blank :as p0]
           [website.posts.one :as p1]))

(def posts [p1/post p0/post])

(def projects [p1/post])

(def pages (flatten (conj posts projects)))

(defn post-preview [post]
  [:a {:href (str "/" (:name post))
       :class "link"}
   [(post :preview)]])

(defn post-preview-panel []
  [:div {:style {:display :inline-block
                 :width "100%"}}
   [:h1 {:class "f400"
         :style {:font-size 23
                 :text-align-last "center"}} 
    "Posts"]
   [:div
    (for [x posts] [post-preview x])]])

;projects to showcase:
;ice age?
;defuser
;oh crap!
;army of damnation
;the gardener

(defn project-preview-panel []
  [:div {:style {:display :inline-block
                 :width "100%"}}
   [:h1 {:class "f400"
         :style {:font-size 23
                 :text-align-last "center"}} 
    "Projects"]
   [:div
    (for [x projects] [post-preview x])]])

(defn panel []
  [post-preview-panel])
