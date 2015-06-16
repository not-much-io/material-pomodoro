(ns pomodoro.timer
  (:require [reagent.core :refer [atom]]
            [pomodoro.util :as util]
            [pomodoro.settings :as settings]))

(defonce invalid-time (js/Date.))
(defonce pomodoro-time-start (atom invalid-time))
(defonce pomodoro-time (atom "00:00:00"))
(defonce pomodoro-state (atom "not-running"))

(defonce update-pomodoro-time
  (js/setInterval
     #(let [start (.getTime @pomodoro-time-start)
            curr  (.getTime (js/Date.))]
       (if (= start (.getTime invalid-time))
         (reset! pomodoro-time "00:00:00")
         (reset! pomodoro-time (util/ms->time (- curr start)))))
     50))

(defn- start-pomodoro []
  (settings/disable-settings)
  (reset! pomodoro-time-start (js/Date.)))

(defn- stop-pomodoro []
  (settings/enable-settings)
  (reset! pomodoro-time-start invalid-time))

(defn toggle-pomodoro []
  (if (= invalid-time @pomodoro-time-start)
    (start-pomodoro)
    (stop-pomodoro)))

