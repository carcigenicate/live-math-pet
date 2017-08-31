(ns live-math-pet.game.settings
  (:require [live-math-pet.game.saving :as sa]))

(defrecord Sim-Settings [health-per-tick pain-per-tick hunger-per-tick])
(defrecord Question-Settings [pain-per-wrong food-per-right])

(defrecord Settings [sim-settings question-settings])

(def settings-label "settings")

(defn save-settings [game-save]
  (sa/save-associative settings-label game-save))

(defn load-settings []
  (sa/load-record settings-label map->Settings))

(def test-settings
  (->Settings
    (->Sim-Settings 1 1 1)
    (->Question-Settings 1 1)))