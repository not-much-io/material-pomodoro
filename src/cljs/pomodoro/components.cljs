(ns pomodoro.components
  (:require [pomodoro.timer :as timer]))

(defn menu-large-screen []
  [:ul {:class "right hide-on-med-and-down"}
   [:li
    [:a {:href "#"}
     "Navbar link"]]])

(defn menu-small-screen []
  [:ul {:id "nav-mobile"
        :class "side-nav"}
   [:li
    [:a {:href "#"}
     "Navbar link"]]])

(defn logo []
  [:img {:src   "images/icon.png"
         :class "brand-logo"
         :style {:height "50px"
                 :paddingTop "10px"}}])

(defn nav-mobile-button []
  [:a {:id "button-collapse"
       :href "#"
       :data-activates "nav-mobile"
       :class "button-collapse"}
   [:i {:class "mdi-navigation-menu"}]])

(defn nav-bar []
  [:nav {:class "grey darken-3"
         :role "navigation"}
   [:div {:class "nav-wrapper container"}
    [logo]
    [menu-large-screen]
    [menu-small-screen]
    [nav-mobile-button]]])

(defn action-button []
  [:div {:class    "fixed-action-btn"
         :style    {:top   "32%"
                    :right "24px"}
         :on-click timer/toggle-pomodoro}
   [:a {:class "btn-floating btn-large waves-effect waves-light red z-depth-3"}
    [:i {:class (str "large " (if (= @timer/pomodoro-time-start timer/invalid-time)
                               "mdi-av-play-arrow"
                               "mdi-av-pause"))}]]])

(defn timer []
  (let [timer-str @timer/pomodoro-time]
    [:div {:class "section z-depth-2 grey darken-2"
           :style {:height "30%"}}
     [:div {:class "container center"}
      [:div {:class   "white-text"}
       [:font {:size 10} timer-str]]]]))

(defn work-time-slider []
  [:p {:class "range-field"}
   [:label {:for "work-time"}
    "Work time (min)"]
   [:input {:type "range"
            :id "work-time"
            :min "0"
            :max "120"}]])

(defn break-time-slider []
  [:p {:class "range-field"}
   [:label {:for "break-time"}
    "Break time (min)"]
   [:input {:type "range"
            :id "break-time"
            :min "1"
            :max "60"}]])

(defn long-break-time-slider []
  [:p {:class "range-field"}
   [:label {:for "long-break-time"}
    "Long break time (min)"]
   [:input {:type "range"
            :id "long-break-time"
            :min "0"
            :max "120"}]])

(defn sessions-before-long-break-slider []
  [:p {:class "range-field"}
   [:label {:for "sessions-before-rest"}
    "Sessions before long rest"]
   [:input {:type "range"
            :id "sessions-before-rest"
            :min "0"
            :max "120"}]])

(defn settings []
  [:div {:class "section"}
   [:div {:class "container"}
    [:form {:action "#"}
     [work-time-slider]
     [break-time-slider]
     [long-break-time-slider]
     [sessions-before-long-break-slider]]]])
