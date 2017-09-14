(ns live-math-pet.store.item
  (:require [live-math-pet.store.store-helpers :as st-h]))

(def life-force-sym "LF")

(defrecord Item [description cost action]
  Object
  (toString [_] (str cost life-force-sym " - " description)))

(defn apply-item-to-state [state item]
  (let [{:keys [action cost]} item]
    (-> state
        (action)
        (st-h/state-pay-with-life-force cost))))