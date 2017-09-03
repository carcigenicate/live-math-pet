(ns live-math-pet.game.text.main
  (:require [helpers.general-helpers :as g]

            [live-math-pet.game.time :as t]
            [live-math-pet.game.pet :as pe]
            [live-math-pet.game.game-state :as gs]
            [live-math-pet.game.settings :as se]
            [live-math-pet.game.text.question-asking :as qa]
            [live-math-pet.game.question.question-types :as qt]
            [live-math-pet.game.text.menu :as me]))

(def global-rand-gen (g/new-rand-gen 99))

; TODO: Try to generalize so this doesn't need to be entirely duplicated for the Quil version
(defn main-loop [initial-game-state rand-gen]
  (loop [acc-state initial-game-state]
    (me/print-menu-options)

    (let [option-char (me/ask-for-menu-option)
          menu-action (me/menu-actions-by-char option-char rand-gen)]

      (if menu-action
          (let [altered-state (menu-action acc-state)
                time-updated-state (gs/apply-time altered-state)]
            ; TODO: Almost done basic setup?
            (recur time-updated-state))))))



(def test-game-state
  (->
    (gs/new-game-state
      10 10
      qt/plus-minus-mult-easy
      se/test-settings)

    (update :pet #(pe/starve % 5))))