(ns live-math-pet.game.main-loop
  (:require [live-math-pet.game.game-state :as gs]
            [live-math-pet.saving.game-save :as g-save]
            [live-math-pet.game.pet :as pe]
            [live-math-pet.game.defaults :as default]))

(defn pet-dead? [state]
  (-> state :pet pe/dead?))

(defn overwrite-save-with-new [label state]
  (let [new-state (default/game-state-for-now)]
    (g-save/save-game-save label new-state)
    new-state))

(defn main-loop
  "menu-f should be a function that takes the current state, and returns an altered-state, or nil to stop."
  [game-state save-label menu-f death-f]
  (loop [acc-state game-state]
    (if (pet-dead? acc-state)
      (do
        (death-f acc-state)
        (overwrite-save-with-new save-label acc-state))

      (let [t-state (gs/apply-time acc-state)]
        (if-let [menu-altered-state (menu-f t-state)]
          (do
            (g-save/save-game-save save-label menu-altered-state)
            (recur menu-altered-state))

          t-state)))))