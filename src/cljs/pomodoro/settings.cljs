(ns pomodoro.settings
  (:require [reagent.core :refer [atom]]))

(defonce pomodoro-time (atom 25))
(defonce short-break-time (atom 5))
(defonce long-break-time (atom 15))
(defonce sessions-before-long-rest (atom 4))

(defonce settings-enabled-state (atom ""))

(defn disable-settings []
  (reset! settings-enabled-state "disabled"))

(defn enable-settings []
  (reset! settings-enabled-state ""))