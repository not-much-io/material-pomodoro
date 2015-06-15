(ns pomodoro.settings
  (:require [reagent.core :refer [atom]]))

(defonce work-time (atom 25))
(defonce break-time (atom 5))
(defonce long-break-time (atom 15))
(defonce sessions-before-long-rest (atom 4))

(defn set-setting [setting value]
  (let [atom-to-change (cond (= setting "work-time") work-time
                             (= setting "break-time") break-time
                             (= setting "long-break-time") long-break-time
                             (= setting "sessions-before-long-rest") sessions-before-long-rest)]
    (reset! atom-to-change value)))