(ns website.views
 (:require
  [re-frame.core :as rf]
  [website.subs :as subs]
  [website.blog :as blog]
  ))

(defn fa-link [class link]
  [:a {:href link
       :class "navbar"
       :style {:padding 4
               :margin :auto}}
   [:div {:class class}]])

(defn link [link text]
  [:a {:href link
       :class "navbar"} text])

(defn navbar []
  [:div {:style {:height 60
                 :display :flex
                 :justify-content :center
                 :width "100vw"
                 :font-size 12
                 :background-color "#DFDFDF"
                 :border-bottom "1px solid #424242"}}
   [:div {:style {:width 700
                  :display :flex}}
    [:a {:style {:margin-top :auto
                 :color :black
                 :margin-bottom :auto
                 :font-size 35
                 :font-weight 400
                 :font-family "'Lato', sans-serif"}
         :href "/"
         :class :name} "Sam Gates"]
    [:div
     {:class "navbar"
      :style {:padding-left 3
              :margin :auto
              :color :black
              :margin-left 10
              :margin-bottom 13
              }}
     [fa-link "fab fa-linkedin-in" "https://www.linkedin.com/in/samg8s/"]
     [fa-link "fab fa-github" "https://github.com/Turmolt"]
     [fa-link "fab fa-stripe-s" "https://www.shadertoy.com/user/BackwardsCap"]
     [fa-link "fab fa-twitter" "https://twitter.com/Turmolt"]
     [fa-link "fas fa-envelope" "mailto:skiracer1292@gmail.com"]]
     
    [:a {:class "fas fa-pencil-ruler link" :style {:margin :auto
                                                   :margin-right 5
                                                   :font-size 30
                                                   :margin-bottom 16}
         :href "/posts"}]]])

(defn main-panel []
  (set! (. js/document -title) "Home")
  [:div {:style {:width 700
                 :font-family "'Lato', sans-serif"
                 :font-weight 400
                 :font-size 17
                 :line-height 1.4
                 :text-align :justify
                 :margin :auto}}
   [:p "Hi, I'm Sam, a software engineer with a passion for creative coding. I am currently an engineer at "
    [link "http://www.heliosinteractive.com" "Helios Interactive"]
    " where I work with a talented team to create innovative marketing solutions, interactive games, and data visualizations. While in college I was a developer for the "
    [link "https://umaine.edu/vemi/" "Virtual Environment and Multimodal Interaction laboratory"]
    " at the University of Maine where I helped create projects that pushed the limits of human-computer interaction. "
    "I enjoy spending my free time tinkering with shaders, clojure, and taking my dog on adventures."]
   [:p "If you would like to partner up and create something awesome " [link "mailto:skiracer1292@gmail.com" "contact me"] " and we can make it happen."]])


(defn posts-panel [data]
  (set! (. js/document -title) "Posts")
  [:div {:style {:width 700
                 :text-align :justify
                 :margin :auto}}
   [blog/post-preview-panel]])

(defn post-display [current]
  (let [post (:panel (first (filter #(= (:name (:data current)) (:tag %)) blog/posts)))]
    (if (nil? post)
      [:div "hi"]
      post)))

(defn page []
  (let [current @(rf/subscribe [::subs/page])]
    [:div {:style {:justify-content :center}}
     [navbar]
     (case (:name (:data current))
       :home [main-panel]
       :posts [posts-panel (:data current)]
       [post-display current])]))
