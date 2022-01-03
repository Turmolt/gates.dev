(ns website.director
 (:require [re-frame.core :as rf]
           [website.pages.posts.techart :as techart]
           [website.pages.posts.first :as p0]
           [website.pages.posts.one :as p1]
           [website.pages.posts.autopainter :as autopainter]
           [website.pages.projects.gardener :as gardener]
           [website.pages.projects.ohcrap :as ohcrap]
           [website.pages.projects.vvc :as vvc]
           [website.pages.projects.usopen :as usopen]
           [website.pages.projects.omegavr :as omega]
           [website.pages.projects.damnationarmy :as damnation]
           [website.pages.projects.whatliesbelow :as whatliesbelow]
           [website.pages.projects.mercedestennis :as mercedestennis]
           [website.pages.projects.lbxcon :as lbx]))

(def posts [techart/post autopainter/post p1/post p0/post])

(def projects [mercedestennis/post whatliesbelow/post ohcrap/post lbx/post usopen/post damnation/post vvc/post gardener/post omega/post])

(def pages (flatten (conj posts projects)))

(defn preview [{icon :icon title :title body-preview :preview}]
  [:div {:class :preview}
   [:div {:class "f400 preview"
          :style {:border-top "1px solid black"
                  :display :flex
                  :min-height 100
                  :padding-bottom 15}}
    [:div {:style {:height "100%"
                   :max-width 80
                   :margin-top :auto
                   :margin-bottom :auto}} [icon]]
    [:div {:style {:margin-top :auto
                   :margin-bottom :auto}}
     [:h3
      {:style {:margin "15px 0px 0px 0px"}}
      title]
     [:p {:style {:font-size 15
                  :margin "10px 0 0 0"
                  :font-style "italic"}}
      body-preview]]]])

(defn post-preview [post]
  [:a {:href (str (:route post))
       :class "link"}
   [preview post]])

(defn preview-panel [title entries]
  [:div {:style {:display :inline-block
                 :width "100%"}}
   [:h1 {:class "f400"
         :style {:font-size 23
                 :text-align-last "center"}} 
    title]
   [:div
    (for [x entries] [post-preview x])]])

;projects to showcase:
;dynamic animation of vehicles in aapex race
;ice age?
;defuser
;oh crap!
;army of damnation
;the gardener
