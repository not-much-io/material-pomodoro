(ns pomodoro.components
  (:require [pomodoro.timer :as timer]
            [pomodoro.settings :as settings]
            [clojure.string :as string]))

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
    [:i {:class (str "large " (if (= @timer/pomodoro-interval timer/standby-label)
                                "mdi-av-play-arrow"
                                "mdi-av-pause"))}]]])

(defn timer []
  (let [timer-str @timer/pomodoro-time-label
        timer-state @timer/pomodoro-interval]
    [:div {:class "section z-depth-2 grey darken-2"
           :style {:height "30%"}}
     [:div {:class "container center"}
      [:div {:class   "white-text"}
       [:font {:size 10} timer-str]]
      [:div {:class   "white-text"}
       [:font {:size 5} timer-state]]]]))

(defn pomodoro-time-slider []
  (let [set-at @settings/pomodoro-time]
    [:p {:class "range-field"}
     [:label {:for "pomodoro-time"}
      (string/replace "pomodoro time (% min)" #"%" set-at)]
     [:input {:type      "range"
              :id        "pomodoro-time"
              :min       "0"
              :max       "120"
              :class     @settings/settings-enabled-state
              :on-change (fn [e]
                           (reset! settings/pomodoro-time (.-target.value e)))
              :value     set-at}]]))

(defn short-break-time-slider []
  (let [set-at @settings/short-break-time]
    [:p {:class "range-field"}
     [:label {:for "short-break-time"}
      (string/replace "Short break time (% min)" #"%" set-at)]
     [:input {:type      "range"
              :id        "short-break-time"
              :min       "1"
              :max       "60"
              :class     @settings/settings-enabled-state
              :on-change (fn [e]
                           (reset! settings/short-break-time (.-target.value e)))
              :value     set-at}]]))

(defn long-break-time-slider []
  (let [set-at @settings/long-break-time]
    [:p {:class "range-field"}
     [:label {:for "long-break-time"}
      (string/replace "Long break time (% min)" #"%" set-at)]
     [:input {:type      "range"
              :id        "long-break-time"
              :min       "0"
              :max       "120"
              :class     @settings/settings-enabled-state
              :on-change (fn [e]
                           (reset! settings/long-break-time (.-target.value e)))
              :value     set-at}]]))

(defn sessions-before-long-break-slider []
  (let [set-at @settings/sessions-before-long-rest]
    [:p {:class "range-field"}
     [:label {:for "sessions-before-rest"}
      (string/replace "Sessions before long break (%)" #"%" set-at)]
     [:input {:type      "range"
              :id        "sessions-before-rest"
              :min       "0"
              :max       "20"
              :class     @settings/settings-enabled-state
              :on-change (fn [e]
                           (reset! settings/sessions-before-long-rest (.-target.value e)))
              :value     set-at}]]))

(defn settings []
  [:div {:class "section"}
   [:div {:class "container"}
    [:form {:action "#"}
     [pomodoro-time-slider]
     [short-break-time-slider]
     [long-break-time-slider]
     [sessions-before-long-break-slider]]]])
