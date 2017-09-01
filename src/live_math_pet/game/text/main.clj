(ns live-math-pet.game.text.main
  (:require [helpers.general-helpers :as g]

            [live-math-pet.game.time :as t]
            [live-math-pet.game.pet :as pe]
            [live-math-pet.game.game-state :as gs]
            [live-math-pet.game.settings :as se]
            [live-math-pet.game.text.question-asking :as qa]
            [live-math-pet.game.question.question-types :as qt]))

(defn get-menu-option [opt]
  ; TODO: Print menu, then switch over the options.
  ; TODO: Sufficient to just have each option return a game-state?
  (println))

(defn main-loop [initial-game-state rand-gen]
  (loop [last-time (t/now)
         acc-state initial-game-state]
    (let [])))

(def test-game-state
  (->
    (gs/new-game-state
      10 10
      qt/plus-minus-mult-easy
      se/test-settings)

    (update :pet #(pe/starve % 5))))