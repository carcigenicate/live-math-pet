(ns live-math-pet.game.settings
  (:require [live-math-pet.game.saving :as sa]))

(defrecord Settings [health-per-tick
                     pain-per-tick pain-per-wrong
                     hunger-per-tick food-per-right])

(def settings-label "settings")

(defn save-settings [game-save]
  (sa/save-associative settings-label game-save))

(defn load-settings []
  (sa/load-record settings-label map->Settings))