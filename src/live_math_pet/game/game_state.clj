(ns live-math-pet.game.game-state
  (:require [live-math-pet.game.game-save :as gs]
            [live-math-pet.game.question.question-generator :as qg]
            [live-math-pet.game.question.question-types :as qt]
            [live-math-pet.game.pet :as pe]
            [live-math-pet.game.settings :as se]))

(def default-starting-max-health 100)
(def default-starting-max-satiation 100)

; TODO: Money?
; TODO: Add settings and question-set to Game-Save
(defrecord Game-State [pet q-gen settings])

(defn new-game-state [starting-health starting-satiation question-set settings]
  (->Game-State (pe/new-pet starting-health starting-satiation)
                (qg/->Question-Generator question-set)
                settings))

(def test-starting-state
  (new-game-state default-starting-max-health default-starting-max-satiation
                  qt/plus-minus-mult-easy
                  se/test-settings))

