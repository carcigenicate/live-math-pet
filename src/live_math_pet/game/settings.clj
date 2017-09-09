(ns live-math-pet.game.settings)

(defn new-settings [health-per-tick pain-per-tick hunger-per-tick]
  :health-per-tick)


(def test-settings
  (->Settings
    (->Sim-Settings 1 1 1)
    (->Question-Settings 1 1)))