(ns live-math-pet.store.store-helpers
  (:require [live-math-pet.game.pet :as pe]))

(defn pet-life-force [pet]
  (+ (:health pet)
     (:satiation pet)))

(defn has-enough-life-force? [pet cost]
  (>= (pet-life-force pet)
      cost))

(defn pay-with-life-force [pet cost]
  (let [{:keys [hunger]} pet
        starved-pet (pe/starve pet cost)]

    (if (pe/starving? starved-pet)
      (pe/hurt starved-pet (- cost hunger)))))

(defn effect-state-with-cost [state cost f]
  (-> state
    (update :pet #(pay-with-life-force % cost))
    (f)))