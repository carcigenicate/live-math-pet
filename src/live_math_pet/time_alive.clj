(ns live-math-pet.time-alive
  (:require [clj-time.core :as ct]
            [live-math-pet.time :as t]))

(defn hours-alive [game-state]
  (let [creation-date (t/read-date (:creation-date game-state))]
    (ct/in-hours
      (ct/interval creation-date (ct/now)))))