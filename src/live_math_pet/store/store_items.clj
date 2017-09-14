(ns live-math-pet.store.store-items
  (:require [live-math-pet.store.store-helpers :as st-h]
            [live-math-pet.store.store-actions :as ma]))

(def stat-increase-amount 1)
(def stat-increase-cost 10)

(def full-revive-cost 100)

(def life-force-sym "LF")

(defrecord Item [description cost action]
  Object
  (toString [_] (str description ": " cost life-force-sym)))

(defn apply-item-to-state [state item]
  (let [{:keys [action cost]} item]
    (-> state
        (action)
        (st-h/state-pay-with-life-force cost))))

(def items
  [(->Item (str "Increase max health by " stat-increase-amount)
           stat-increase-cost
           #(ma/increase-pet-stat % :max-health stat-increase-amount))

   (->Item (str "Increase max satiation by " stat-increase-amount)
           stat-increase-cost
           #(ma/increase-pet-stat % :max-satiation stat-increase-amount))

   (->Item "Fully restore health"
           full-revive-cost
           ma/fully-revive-pet)])