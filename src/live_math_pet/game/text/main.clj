(ns live-math-pet.game.text.main
  (:require [helpers.general-helpers :as g]

            [live-math-pet.game.time :as t]
            [live-math-pet.game.pet :as pe]
            [live-math-pet.game.game-state :as g-state]
            [live-math-pet.game.settings :as se]
            [live-math-pet.game.text.question-asking :as qa]
            [live-math-pet.game.question.question-types :as qt]))

; TODO: How is the mneu going to work?
(defn get-menu-option [])

(defn main-loop [initial-game-state rand-gen]
  (loop [last-time (t/now)
         acc-state initial-game-state]
    (let [])))


(def test-game-state
  (->
    (g-state/new-game-state
      10 10
      qt/plus-minus-mult-easy
      se/test-settings)

    (update :pet #(pe/starve % 5))))