(ns live-math-pet.game.saving.game-save
  (:require [live-math-pet.game.time :as t]
            [live-math-pet.game.operator-symbols :as os]
            [live-math-pet.game.saving.saving-helpers :as sh]
            [live-math-pet.game.game-state :as gs]
            [live-math-pet.game.settings :as se]

            [clojure.edn :as edn]
            [live-math-pet.game.pet :as pe]
            [live-math-pet.game.question.question-generator :as qg]))

(def operator-path [:q-gen :operator-ranges])

(defn translate-math-operators-to-symbols [game-state]
  (update-in game-state operator-path
    #(into {}
       (map (fn [[o r]]
              [(os/operator-symbols o) r]) %))))

(defn translate-date-obj-to-str [game-state]
  (update game-state :last-update t/format-date))

#_
(defn flatten-game-state [game-state]
  (let [{:keys [pet, q-gen, last-update, settings]} game-state
        {:keys [health, max-health, satiation, max-satiation]} pet
        {:keys [sim-settings, question-settings]} settings
        {:keys [health-per-tick, pain-per-tick, hunger-per-tick]} sim-settings
        {:keys [pain-per-wrong, food-per-right]} question-settings]
    ; TODO: Way too much redundancy?
    game-state))
#_
(defn flatten-to-children [assoc?]
  (if (associative? assoc?)
    (mapcat flatten-to-children assoc?)
    ()))

(defn flatten-to-leaves [record]
  (into {}
    (filter
      #(and (map-entry? %)
            (not (associative? (second %))))

      (tree-seq associative? (partial apply list) record))))

(defn save-game-save [label game-state]
  (->> game-state
       (translate-math-operators-to-symbols)
       (translate-date-obj-to-str)
       (sh/save-associative label)))

(defn translate-math-symbols-to-operators [game-state]
  (update-in game-state operator-path
    #(into {}
       (map (fn [[s r]]
              [(os/operator-from-symbol s) r]) %))))

(defn translate-date-str-to-obj [game-state])

; TODO: Will need to manually deconstruct the flattened state.
(defn load-game-save [label]
  (println "Load not implemented."))
