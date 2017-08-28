(ns live-math-pet.game.game-state
  (:require [live-math-pet.game.game-save :as gs]))

; TODO: Money?
(defrecord Game-State [pet last-feed-time])

(defn advance-pet-by [pet seconds]
  (pet))