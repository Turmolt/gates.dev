(ns website.posts.common
  (:require ["prismjs/prism.js" :as prism]
            [reagent.core :as r]
            [reagent.dom :as rdom]))


(defn code [features & text]
  (r/create-class
   {:component-did-mount
    (fn [component]
      (let [node (rdom/dom-node component)]
        (prism/highlightElement node)))
    :render (fn [] [:code {:class features
                           :style {:font-size 15.5}} text])}))

(defn link
  [link text]
  [:a {:href link 
       :class :link}
   text])