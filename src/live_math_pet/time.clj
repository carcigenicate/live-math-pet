(ns live-math-pet.time
  (:require [clj-time.core :as ct]
            [clj-time.local :as cl]
            [clj-time.format :as cf]))

(def date-formatter (cf/formatters :basic-date-time))

(defn seconds-between [d1 d2]
  (ct/in-seconds
    (ct/interval d1 d2)))

(defn seconds-since [d]
  (seconds-between d (ct/now)))

(defn read-date [d-str]
  (cf/parse date-formatter d-str))

(defn format-date [d]
  (cf/unparse date-formatter d))

(def now ct/now)

(defn timed-do* [f]
  (let [start-t (now)
        result (f)
        secs-taken (seconds-between start-t (now))]
    [secs-taken result]))

(defmacro timed-do [& body]
  `(timed-do* (fn [] ~@body)))