(ns pomodoro.timer
  (:require [reagent.core :refer [atom]]
            [pomodoro.util :as util]
            [pomodoro.settings :as settings]))

(def ^:private standby-label "Waiting..")
(def ^:private work-label "Pomodoro")
(def ^:private short-break-label "Short Break")
(def ^:private long-break-label "Long Break")

(defonce pomodoro-time-label (atom "00:00:00"))
(defonce pomodoro-time (atom 0))
(defonce pomodoro-interval (atom standby-label))
(defonce session-nr (atom 0))

(defn- interval->duration [interval]
  (get {work-label        (util/min->ms @settings/pomodoro-time)
        short-break-label (util/min->ms @settings/short-break-time)
        long-break-label  (util/min->ms @settings/long-break-time)} interval))

(defn- next-interval? [curr-interval time]
  (< (interval->duration curr-interval) time))

(defn- next-interval [curr-interval]
  (cond (= curr-interval standby-label) work-label
        (= curr-interval work-label) (if (> @session-nr
                                            @settings/sessions-before-long-rest)
                                       (do
                                         (reset! session-nr 0)
                                         long-break-label)
                                       short-break-label)
        (or (= curr-interval long-break-label)
            (= curr-interval short-break-label)) (do (swap! session-nr inc)
                                                     work-label)))

(defn- interval-watch [key, ref, old-state, new-state]
  (if (next-interval? @pomodoro-interval new-state)
    (do
      (reset! pomodoro-time 0)
      (reset! pomodoro-interval (next-interval @pomodoro-interval)))))

(add-watch pomodoro-time :interval-watch interval-watch)

(defonce update-pomodoro-time
  (js/setInterval
    #(if (= @pomodoro-interval standby-label)
      (do
        (if (not (or (= @pomodoro-time 0)
                     (= @pomodoro-time-label "00:00:00")))
          (reset! pomodoro-time 0)
          (reset! pomodoro-time-label "00:00:00")))
      (do
        (reset! pomodoro-time (+ @pomodoro-time 500))
        (reset! pomodoro-time-label (util/ms->time @pomodoro-time))))
    500))

(defn- start-pomodoro []
  (settings/disable-settings)
  (reset! pomodoro-interval work-label))

(defn- stop-pomodoro []
  (settings/enable-settings)
  (reset! pomodoro-interval standby-label))

(defn toggle-pomodoro []
  (if (= @pomodoro-time 0) (start-pomodoro) (stop-pomodoro)))