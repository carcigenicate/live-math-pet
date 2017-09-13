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
  (loop [acc-state (gs/apply-time game-state)]
    (if (pet-dead? acc-state)
      (do
        (death-f acc-state)
        (overwrite-save-with-new save-label acc-state))

      (if-let [menu-altered-state (menu-f acc-state)]
        (let [t-state (gs/apply-time menu-altered-state)]
          (g-save/save-game-save save-label t-state)
          (recur t-state))

        (gs/apply-time acc-state)))))