(ns live-math-pet.game.text.progression-management
  (:require [helpers.general-helpers :as g]))

#_
(defrecord Prog-Manager [scene game-state])

(defn ask-questions [game-state rand-gen])
  ; TODO: use g/ask-for-input to get users guess

(defn main-loop [initial-game-state rand-gen]
  (loop []))