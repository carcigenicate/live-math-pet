(ns live-math-pet.store.store-helpers
  (:require [live-math-pet.game.pet :as pe]))

(defn pay-with-life-force [pet cost]
  (let [{:keys [satiation]} pet
        starved-pet (pe/starve pet cost)]

    (if (pe/starving? starved-pet)
      (pe/hurt starved-pet (- cost satiation))
      starved-pet)))

(defn effect-state-with-cost [state cost f]
  (-> state
    (update :pet #(pay-with-life-force % cost))
    (f)))