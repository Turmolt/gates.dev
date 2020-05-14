(ns website.posts.blank
 (:require 
  [reagent.core :as r]
  [reagent.dom :as rdom]
  [re-frame.core :as rf]
  [website.events :as events]
  [quil.core :as q]
  [quil.middleware :as m]
  ["codemirror" :as CodeMirror]
  ))

(defn code-mirror
  "Create a code-mirror editor. The parameters:
  value-atom (reagent atom)
    when this changes, the editor will update to reflect it.
  options
  :style (reagent style map)
    will be applied to the container element
  :js-cm-opts
    options passed into the CodeMirror constructor
  :on-cm-init (fn [cm] -> nil)
    called with the CodeMirror instance, for whatever extra fiddling you want to do."
  [value-atom]

  (let [cm (atom nil)]
    (r/create-class
     {:component-did-mount
      (fn [this]
        (let [el (rdom/dom-node this)
              inst (CodeMirror.
                    el
                    (clj->js
                     (merge
                      {:lineNumbers true
                       :viewportMargin js/Infinity
                       :matchBrackets true
                       :autofocus false
                       :value @value-atom
                       :autoCloseBrackets true
                       :mode "clojure"})))]

          (reset! cm inst)
          (.on inst "change"
               (fn []
                 (let [value (.getValue inst)]
                   (when-not (= value @value-atom)
                     (reset! value-atom value)))))))

      :component-did-update
      (fn [this old-argv]
        (when-not (= @value-atom (.getValue @cm))
          (.setValue @cm @value-atom)
          ;; reset the cursor to the end of the text, if the text was changed externally
          (let [last-line (.lastLine @cm)
                last-ch (count (.getLine @cm last-line))]
            (.setCursor @cm last-line last-ch))))

      :reagent-render
      (fn [_ _ _]
        @value-atom
        [:div {:style {:width "670px"}}])})))

(def title "This is the title of the fancy first blog post")
(def body-preview "The body is very interesting")
(def body [:div "Lorem ipsum testing things :)"
           [code-mirror (r/atom "(str \"hello\")")]])


  
;[:pre [:code {:class "Clojure"
 ;               :ref (fn [n] (when n (js/setTimeout #(hl/highlightBlock n) 0)))}
  ;       "(+ 1 1) ;=> 2\n2"]]

(def tag :blank)
(def route "blank")

(defn preview []
  [:div {:class "f400"
         :style {:border-top "1px solid black"
                 :width "100%"
                 :height 100}}
   [:h3
   {:style {:margin "15px 0px 0px 0px"}} 
    title]
   [:p {:style {:font-size 15
                :margin "10px 0 0 0"
                :font-style "italic"}}
    body-preview]])


(defn update-sketch
  [state]
  state)

(defn circle
  [id]
  {:id id
   :time 0
   :color [0 0 0]})

(defn setup-sketch
  []
  (apply q/background [255 255 255])
  (circle 0))

(defn center-scale
  [t]
  (+ 50 (* 20 t)))

(defn draw [state]
  (apply q/background [255 255 255])
  (apply q/fill [0 0 0])
  (let [time (:time state)]
    (q/ellipse (center-scale (Math/cos (/ time 2.0))) (center-scale (Math/sin time)) 10 10)))

(defn sketch-update
  [state]
  (assoc state :time (+ 0.05 (:time state))))

(defn canvas []
  (r/create-class
   {:component-did-mount
    (fn [component]
      (let [node (rdom/dom-node component)]
        (q/sketch
         :host node
         :setup #'setup-sketch
         :update #'sketch-update
         :size [100 100]
         :draw #'draw
         :middleware [m/fun-mode])))
    :render (fn [] [:div])}))

(defn panel []
  (set! (. js/document -title) title)
  [:div
   {:class "f400"
    :style {:width 700 :margin :auto}}
   [:h2 title]
   [:div body]
   [canvas]])


(def post {:name "blank"
           :preview preview
           :panel panel
           :tag tag
           :route route})
