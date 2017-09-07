(ns live-math-pet.game.text.main
  (:require [helpers.general-helpers :as g]

            [live-math-pet.game.time :as t]
            [live-math-pet.game.pet :as pe]
            [live-math-pet.game.game-state :as gs]
            [live-math-pet.game.settings :as se]
            [live-math-pet.game.text.question-asking :as qa]
            [live-math-pet.game.question.question-types :as qt]
            [live-math-pet.game.text.menu :as me]
            [live-math-pet.game.saving.game-save :as g-save]))

; TODO: IT WORKS! Set up an actual main that handles loading.

(def global-rand-gen (g/new-rand-gen 99))

(def default-game-label "pet")

; TODO: Try to generalize so this doesn't need to be entirely duplicated for the Quil version
(defn main-loop [initial-game-state rand-gen]
  (loop [acc-state (gs/apply-time initial-game-state)]
    (println (str "\n" (-> acc-state :pet str)))
    (me/print-menu-options)

    (let [option-char (me/ask-for-menu-option)
          menu-action (me/menu-actions-by-char option-char rand-gen)]

      (if menu-action
          (let [altered-state (menu-action acc-state)
                t-state (gs/apply-time initial-game-state)]
            ; Only necessary to save when the user picks something other than exit?
            ; If the user wants to exit, the state should be the same
            ;  as the last time they picked an option.
            (g-save/save-game-save default-game-label t-state)

            (recur t-state))

          acc-state))))

(def test-game-state
  (->
    (gs/new-game-state
      10 10
      qt/plus-minus-mult-easy
      se/test-settings)

    (update :pet #(pe/starve % 5))))