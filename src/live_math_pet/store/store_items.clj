(ns live-math-pet.store.store-items
  (:require [live-math-pet.store.store-helpers :as st-h]
            [live-math-pet.store.store-actions :as ma]
            [live-math-pet.store.item :as i]))

(def stat-increase-amount 1)
(def stat-increase-cost 10)

(def full-revive-cost 100)

(def q-risk-cost 50)
(def q-val-increase 0.15)
(def q-pain-increase (* q-val-increase 2))

(def items
  (let [i i/->Item]
    [(i (str "Increase max health by " stat-increase-amount)
        stat-increase-cost
        #(ma/increase-pet-stat % :max-health stat-increase-amount))

     (i (str "Increase max satiation by " stat-increase-amount)
        stat-increase-cost
        #(ma/increase-pet-stat % :max-satiation stat-increase-amount))

     (i "Fully restore health"
        full-revive-cost
        ma/fully-revive-pet)

     (i (str "Increase question value by " q-val-increase
             ", but also increase the damage penalty for wrong answers")
        q-risk-cost
        #(ma/increase-gain-risk % q-val-increase q-pain-increase))]))
