(ns live-math-pet.store.store-items
  (:require [live-math-pet.store.store-helpers :as st-h]))

(def stat-adjustment-amount 1)
(def stat-adjustment-cost 10)

(defn adjust-stat [state key op]
  (st-h/effect-state-with-cost state stat-adjustment-cost
       (fn [state]
         (update-in state [:pet key] #(op % stat-adjustment-amount)))))

(def items
  {::increase-health #(adjust-stat % :health +)
   ::increase-satiation #(adjust-stat % :satiation +)})