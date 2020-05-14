(ns website.subs
 (:require
  [re-frame.core :as rf]))

(rf/reg-sub
 ::page
 (fn [db]
   (:page db)))

(rf/reg-sub
 ::title
 (fn [db]
   (:title db)))
