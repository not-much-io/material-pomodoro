(ns pomodoro.timer
  (:require [reagent.core :refer [atom]]
            [pomodoro.util :as util]))

(defonce invalid-time (js/Date.))
(defonce pomodoro-time-start (atom invalid-time))
(defonce pomodoro-time (atom "00:00:00"))

(defonce update-pomodoro-time
  (js/setInterval
     #(let [start (.getTime @pomodoro-time-start)
            curr  (.getTime (js/Date.))]
       (if (= start (.getTime invalid-time))
         (reset! pomodoro-time "00:00:00")
         (reset! pomodoro-time (util/ms->time (- curr start)))))
     50))

(defn- start-pomodoro []
  (reset! pomodoro-time-start (js/Date.)))

(defn- stop-pomodoro []
  (reset! pomodoro-time-start invalid-time))

(defn toggle-pomodoro []
  (if (= invalid-time @pomodoro-time-start)
    (start-pomodoro)
    (stop-pomodoro)))

