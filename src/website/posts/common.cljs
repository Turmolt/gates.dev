(ns website.posts.common)

(defn link
  [link text]
  [:a {:href link 
       :class :link}
   text])