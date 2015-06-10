(ns pomodoro.core
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as EventType])
  (:import goog.History))

;; -------------------------
;; State

(defonce pomodoro-time (atom {:hours 0
                              :minutes 0
                              :seconds 0}))

(defonce work-time (atom 25))
(defonce break-time (atom 5))
(defonce long-break-time (atom 15))
(defonce sessions-before-long-break (atom 4))

;; -------------------------
;; Components

(def nav-bar
  [:nav {:class "grey darken-3 z-depth-1"
         :role "navigation"}
   [:div {:class "nav-wrapper container"}
    [:a {:id "logo-container"
         :href "#"
         :class "brand-logo"}
     [:img {:src "images/icon.png"
            :style {:height     "50px"
                    :paddingTop "10px"}}]]
    [:ul {:class "right hide-on-med-and-down"}
     [:li
      [:a {:href "#"}
       "Navbar link"]]]]])

(def action-button
  [:div {:class "fixed-action-btn"
         :style {:top "32%"
                 :right "24px"}}
   [:a {:class "btn-floating btn-large waves-effect waves-light red z-depth-3"}
    [:i {:class "large mdi-av-play-arrow"}]]])

(def timer
  [:div {:class "section z-depth-2 grey darken-2"
         :style {:height "30%"}}
   [:div {:class "container"}
    [:h2 {:class "white-text"}
     [:i {:class "mdi-image-timer small"}]
     "01:49"]]])

(def settings
  [:div {:class "section"}
   [:div {:class "container"}
    [:form {:action "#"}
     [:p {:class "range-field"}
      [:label {:for "work-time"}
       "Work time (min)"]
      [:input {:type "range"
               :id "work-time"
               :min "0"
               :max "120"}]]
     [:p {:class "range-field"}
      [:label {:for "break-time"}
       "Break time (min)"]
      [:input {:type "range"
               :id "break-time"
               :min "1"
               :max "60"}]]
     [:p {:class "range-field"}
      [:label {:for "long-break-time"}
       "Long break time (min)"]
      [:input {:type "range"
               :id "long-break-time"
               :min "0"
               :max "120"}]]
     [:p {:class "range-field"}
      [:label {:for "sessions-before-rest"}
       "Sessions before long rest"]
      [:input {:type "range"
               :id "sessions-before-rest"
               :min "0"
               :max "120"}]]]]])


;; -------------------------
;; Views

(defn home-page []
  [:div
   nav-bar
   action-button
   timer
   settings])

(defn about-page []
  [:div [:h2 "About pomodoro"]
   [:div [:a {:href "#/"} "go to the home page"]]])

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
                    (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
                    (session/put! :current-page #'about-page))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      EventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (hook-browser-navigation!)
  (mount-root))
