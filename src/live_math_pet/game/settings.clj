(ns live-math-pet.game.settings)

(defrecord Sim-Settings [health-per-tick pain-per-tick hunger-per-tick])
(defrecord Question-Settings [pain-per-wrong food-per-right])

(defrecord Settings [sim-settings question-settings])

(def settings-label "settings")

(def test-settings
  (->Settings
    (->Sim-Settings 1 1 1)
    (->Question-Settings 1 1)))