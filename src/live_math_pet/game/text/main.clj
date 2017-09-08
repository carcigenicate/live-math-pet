(ns live-math-pet.game.text.main
  (:require [helpers.general-helpers :as g]

            [live-math-pet.game.time :as t]
            [live-math-pet.game.pet :as pe]
            [live-math-pet.game.game-state :as gs]
            [live-math-pet.game.settings :as se]
            [live-math-pet.game.text.question-asking :as qa]
            [live-math-pet.game.question.question-types :as qt]
            [live-math-pet.game.text.menu :as me]
            [live-math-pet.game.saving.game-save :as g-save]
            [live-math-pet.game.defaults :as default])

  (:import [java.io FileNotFoundException]))

; TODO: IT WORKS! Set up an actual main that handles loading.

(def global-rand-gen (g/new-rand-gen 99))

; TODO: Try to generalize so this doesn't need to be entirely duplicated for the Quil version
(defn main-loop [game-state save-label rand-gen]
  (loop [acc-state (gs/apply-time game-state)]
    (println (str "\n" (-> acc-state :pet str)))
    (me/print-menu-options)

    (let [option-char (me/ask-for-menu-option)
          menu-action (me/menu-actions-by-char option-char rand-gen)]

      (if menu-action
          (let [altered-state (menu-action acc-state)
                t-state (gs/apply-time altered-state)]

            ; Only necessary to save when the user picks something other than exit?
            ; If the user wants to exit, the state should be the same
            ;  as the last time they picked an option.
            (g-save/save-game-save save-label t-state)

            (recur t-state))

          acc-state))))

(defn load-with-default [label default-state]
  (try
    (g-save/load-game-save label)

    (catch FileNotFoundException e
      default-state)))


(defn -main [& [label?]]
  (let [label (or label? default/game-label)
        state (load-with-default label (default/game-state-for-now))]

    (main-loop state label global-rand-gen)))