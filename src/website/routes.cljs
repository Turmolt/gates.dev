(ns website.routes
 (:require [re-frame.core :as re-frame]
           [website.events :as e]
           [reitit.frontend :as rf]
           [reitit.ring :as ring]
           [reitit.frontend.easy :as rfe]
           [website.director :as director]))

(def routes
  ["/"
   [""
    {:name :home}]
   ["posts"
    [""
     {:name :posts}]]
   ["projects"
    [""
     {:name :projects}]]
   (for [post director/pages]
     [(:name post)
      {:name (:tag post)}])])

(defn on-navigate [new-match]
  (when new-match
    (re-frame/dispatch [::e/navigated new-match])))

(def router
  (rf/router
   routes))

(defn init-routes! []
  (rfe/start!
   router
   on-navigate
   {:use-fragment true}))