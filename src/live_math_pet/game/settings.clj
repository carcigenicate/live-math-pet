(ns live-math-pet.game.settings
  (:require [live-math-pet.game.helpers :as h]))

(defn new-settings [health-per-tick pain-per-tick hunger-per-tick pain-per-wrong food-per-right]

  (h/gen-arg-map new-settings
    health-per-tick
    pain-per-tick
    hunger-per-tick
    pain-per-wrong
    food-per-right))

(def test-settings
  (new-settings 1 1 1 1 1))