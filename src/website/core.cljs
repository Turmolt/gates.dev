(ns website.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [website.events :as events]
   [website.views :as views]
   [website.config :as config]
   [website.routes :as routes]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load start []
  (re-frame/clear-subscription-cache!)
  (routes/init-routes!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/page] root-el)))

(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (start))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (js/console.log "stop"))
