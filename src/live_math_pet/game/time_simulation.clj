(ns live-math-pet.game.time-simulation
  (:require [live-math-pet.game.pet :as p]
            [live-math-pet.game.settings :as se]))

(defn sim-tick [pet sim-settings]
  (let [{heT :health-per-tick,
         paT :pain-per-tick,
         huT :hunger-per-tick} sim-settings

        healed-hurt-pet (if (p/starving? pet)
                          (p/hurt pet paT)
                          (p/heal pet heT))]

    (p/starve healed-hurt-pet huT)))

(defn advance-pet-by [pet settings ticks]
    (reduce (fn [acc-pet sec]
              (if (p/dead? acc-pet)
                (reduced acc-pet)
                (sim-tick acc-pet settings)))

            pet

            (range ticks)))