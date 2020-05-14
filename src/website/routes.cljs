(ns website.routes
 (:require [re-frame.core :as re-frame]
           [website.events :as e]
           [reitit.frontend :as rf]
           [reitit.coercion.spec :as rss]
           [reitit.frontend.easy :as rfe]
           [website.blog :as blog]))

(def routes
  ["/"
   [""
    {:name :home
     :controllers [{:start (fn [& params] (js/console.log "Home"))
                    :stop (fn [& params] (js/console.log "Leaving Home"))}]}]
   ["posts"
    [""
     {:name :posts
      :controllers [{:start (fn [& params] (js/console.log "Posts"))
                     :stop (fn [& params] (js/console.log "Leaving Posts"))}]}]]
   (for [post blog/posts]
     [(:route post)
      {:name (:tag post)
       :controllers [{:start (fn [& params] (js/console.log (:name post)))
                      :stop (fn [& params] (js/console.log "Leaving " (:name post)))}]}])])

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
   {:use-fragment false}))