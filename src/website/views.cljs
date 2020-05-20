(ns website.views
 (:require
  [re-frame.core :as rf]
  [website.subs :as subs]
  [website.pages :as pages]
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
                 :width "100%"
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
              :margin-bottom 13}}
     [fa-link "fab fa-linkedin-in" "https://www.linkedin.com/in/samg8s/"]
     [fa-link "fab fa-github" "https://github.com/Turmolt"]
     [fa-link "fab fa-stripe-s" "https://www.shadertoy.com/user/BackwardsCap"]
     [fa-link "fab fa-twitter" "https://twitter.com/Turmolt"]
     [fa-link "fas fa-envelope" "mailto:skiracer1292@gmail.com"]]
    [:a {:class "fas fa-pencil-ruler link" :style {:margin :auto
                                                   :margin-right 5
                                                   :font-size 28
                                                   :margin-bottom :auto}
         :href "/projects"}
     [:div {:style {:font-size 8 :text-align :center :padding-top 2} :class :f400} "projects"]]
    [:a {:class "far fa-sticky-note link" :style {:margin-top :auto
                                                   :margin-bot :auto
                                                   :margin-left 10
                                                   :margin-right 5
                                                   :font-size 30
                                                   :margin-bottom :auto}
         :href "/posts"}
     [:div {:style {:font-size 8 :text-align :center} :class :f400} "posts"]]]])

(defn main-panel []
  (set! (. js/document -title) "Home")
  [:div {:style {:width 700
                 :font-size 18
                 :line-height 1.4
                 :text-align :justify
                 :margin :auto}
         :class :f400}
   [:p "Hi, I'm Sam, a software engineer with a passion for creative coding. I am currently a developer at "
    [link "http://www.heliosinteractive.com" "Helios Interactive"]
    " where I work with a talented team to create innovative marketing solutions, interactive games, and data visualizations. While in college I was a developer for the "
    [link "https://umaine.edu/vemi/" "Virtual Environment and Multimodal Interaction laboratory"]
    " at the University of Maine where I helped create projects that pushed the limits of human-computer interaction. "
    "I enjoy spending my free time making games, tinkering with shaders, clojure, and taking my dog on adventures."]
   [:p "If you would like to partner up and create something awesome " [link "mailto:skiracer1292@gmail.com" "contact me"] " and we can make it happen."]])


(defn posts-panel []
  (set! (. js/document -title) "Posts")
  [:div {:style {:width 700
                 :text-align :justify
                 :margin :auto}}
   [pages/post-preview-panel]])

(defn projects-panel []
  (set! (. js/document -title) "Posts")
  [:div {:style {:width 700
                 :text-align :justify
                 :margin :auto}
         :class :f400}
   [pages/project-preview-panel]])

(defn post-display [current]
  (let [post (:panel (first (filter #(= (:name (:data current)) (:tag %)) pages/pages)))]
    (if (nil? post)
      [:div {:style {:text-align :center}} "Your page is in another castle"]
      post)))

(defn footer[]
  [:div 
   {:style {:text-align :center
            :left 0
            :right 0
            :bottom 0
            :width "100%"
            :font-size 10
            :margin :auto
            :padding-top "20px"
            :padding-bottom "10px"
            :color "#999999"}
    :class :f400}
   "© Sam Gates"])

(defn page []
  (let [current @(rf/subscribe [::subs/page])]
    [:div {:style {:justify-content :center
                   }}
     [:div {:style {:min-height "95vh"}}
      [navbar]
      (case (:name (:data current))
        :home [main-panel]
        :posts [posts-panel]
        :projects [projects-panel]
        [post-display current])]
     [footer]]))
