(ns pomodoro.util)

(defn ms->time [ms]
  (let [seconds (int (/ ms 1000))
        minutes (int (/ seconds 60))
        hours (int (/ minutes 60))
        buff (fn [x] (if (< x 10) (str "0" x) x))]
    (str (buff hours) ":"
         (buff (- minutes (* 60 hours))) ":"
         (buff (- seconds (* 60 minutes))))))