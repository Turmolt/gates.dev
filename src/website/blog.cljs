(ns website.blog
 (:require [re-frame.core :as rf]
           [website.posts.blank :as p0]))

(def posts [p0/post])

(defn post-preview [post]
  [:a {:href (str "/" (:name post))
       :class "link"}
   [(post :preview)]])

(defn post-preview-panel []
  [:div {:style {:display :inline-block
                 :width "100%"}}
   [:h1 {:class "f400"
         :style {:font-size 23
                 :text-align-last "center"
                 }} "Posts about things that I find interesting"]
   [:div
    (for [x posts] [post-preview x])]])

(defn panel []
  [post-preview-panel])
