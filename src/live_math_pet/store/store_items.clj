(ns live-math-pet.store.store-items
  (:require [live-math-pet.store.store-helpers :as st-h]))

(def stat-adjustment-amount 1)
(def stat-adjustment-cost 10)

(def revive-cost 100)

; TODO: Incorporate a cost field, and automate some of the tasks
;        like "charging" the pet and showing a description.
#_
(defrecord Item [action description])

(defn update-pet [state f]
  (update-in state [:pet key] f))

(defn adjust-stat [state key op]
  (st-h/effect-state-with-cost state stat-adjustment-cost
       (fn [s]
         (update-in s [:pet key] #(op % stat-adjustment-amount)))))

(defn revive [state]
  (-> state
      (st-h/effect-state-with-cost revive-cost
        (fn [s]
          (update s :pet #(assoc % :health (:max-health %)))))))

(def items
  {::increase-health #(adjust-stat % :max-health +)
   ::increase-satiation #(adjust-stat % :max-satiation +)
   ::full-revive revive})