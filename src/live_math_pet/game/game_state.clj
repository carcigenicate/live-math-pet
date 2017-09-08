(ns live-math-pet.game.game-state
  (:require [live-math-pet.game.question.question-generator :as qg]
            [live-math-pet.game.question.question-types :as qt]
            [live-math-pet.game.pet :as pe]
            [live-math-pet.game.settings :as se]
            [live-math-pet.game.time :as t]
            [live-math-pet.game.time-simulation :as ts]))

; TODO: Money?

; TODO: Add settings and question-set to Game-Save
; TODO: Just save the entire state to disk?
; TODO: Map operators to strings so they can be saved easier?
; TODO: 

(defrecord Game-State [pet q-gen last-update settings])

(defn new-game-state [starting-health starting-satiation question-set settings]
  (->Game-State (pe/new-pet starting-health starting-satiation)
                (qg/->Question-Generator question-set)
                (t/now)
                settings))

(defn apply-time [game-state]
  (let [{:keys [last-update settings]} game-state
        now (t/now)
        elapsed (t/seconds-between last-update now)]

    (-> game-state
      (update :pet #(ts/advance-pet-by % (:sim-settings settings) elapsed))
      (assoc :last-update now))))

