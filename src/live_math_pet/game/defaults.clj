(ns live-math-pet.game.defaults
  (:require [live-math-pet.game.game-state :as gs]
            [live-math-pet.game.pet :as pe]
            [live-math-pet.game.question.question-types :as qt]
            [live-math-pet.game.question.question-generator :as qg]
            [live-math-pet.game.settings :as se]))

; ---------- Game Save Defaults ----------

(def game-label "pet")

; ---------- Game State Defaults ----------

(def starting-health 100)
(def starting-satiation 100)

(def operator-ranges qt/plus-minus-mult-easy)

(def last-8-hours-dec
  "(starting health of 100) / (seconds in 8 hours)."
  0.00347)

(def health-per-tick (* last-8-hours-dec 1.2))
(def hunger-per-tick last-8-hours-dec)
(def pain-per-tick last-8-hours-dec)
(def pain-per-wrong-q 5)
(def food-per-right-q 2.5)

(defn game-state-for-now
  "Creates a default game state with the last update time as the time this function was called."
  []
  (gs/new-game-state-for-now
    starting-health, starting-satiation

    operator-ranges

    (se/new-settings health-per-tick pain-per-tick hunger-per-tick
                     pain-per-wrong-q food-per-right-q)))