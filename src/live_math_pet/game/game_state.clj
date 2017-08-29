(ns live-math-pet.game.game-state
  (:require [live-math-pet.game.game-save :as gs]))

; TODO: Money?
; TODO: Add settings and question-set to Game-Save
(defrecord Game-State [pet last-feed-time question-set settings])
