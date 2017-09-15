(ns live-math-pet.saving.game-save
  (:require [clojure.edn :as edn]

            [live-math-pet.time :as t]

            [live-math-pet.saving.saving-helpers :as sh]

            [live-math-pet.operator-symbols :as os]
            [live-math-pet.game.game-state :as gs]
            [live-math-pet.game.settings :as se]
            [live-math-pet.game.pet :as pe]
            [live-math-pet.game.question.question-generator :as qg]))

(def operator-path [:q-gen :operator-ranges])

; ----- Saving -----

(defn translate-math-operators-to-symbols [game-state]
  (update-in game-state operator-path
    #(into {}
       (map (fn [[o r]]
              [(os/operator-symbols o) r]) %))))

(defn translate-date-obj-to-str [game-state]
  (-> game-state
    (update :creation-date t/format-date)
    (update :last-update t/format-date)))

(defn translate-to-symbols-pre-save [game-state]
  (-> game-state
      (translate-date-obj-to-str)
      (translate-math-operators-to-symbols)))

(defn save-game-save [label game-state]
  (-> game-state
      (translate-to-symbols-pre-save)
      (sh/save-associative label)))

; ----- Loading -----

(defn translate-math-symbols-to-operators [game-state]
  (update-in game-state operator-path
    #(into {}
       (map (fn [[s r]]
              [(os/operator-from-symbol s) r]) %))))

(defn translate-date-str-to-obj [game-state]
  (-> game-state
      (update :creation-date t/read-date)
      (update :last-update t/read-date)))

(defn translate-from-symbols-post-load [unprepared-game-state]
  (-> unprepared-game-state
      (translate-math-symbols-to-operators)
      (translate-date-str-to-obj)))

(defn load-game-save [label]
  (-> (sh/load-raw-map label)
      (translate-from-symbols-post-load)))
