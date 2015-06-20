(ns pomodoro.util)

(defn min->ms [min]
  (* min 60 1000))

(defn ms->time [ms]
  (let [seconds (int (/ ms 1000))
        minutes (int (/ seconds 60))
        hours (int (/ minutes 60))
        buff (fn [x] (if (< x 10) (str "0" x) x))]
    (str (buff hours) ":"
         (buff (- minutes (* 60 hours))) ":"
         (buff (- seconds (* 60 minutes))))))

(defn time->ms [time]
  (let [[hours minutes seconds] (clojure.string/split time #":")
        hours (int hours)
        minutes (int minutes)
        seconds (int seconds)]
    (+ (* hours 60 60 1000) (* minutes 60 1000) (* seconds 1000))))