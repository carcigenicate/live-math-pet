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

; ----- Saving -----

(defn translate-math-operators-to-symbols [game-state]
  (update-in game-state operator-path
    #(into {}
       (map (fn [[o r]]
              [(os/operator-symbols o) r]) %))))

(defn translate-date-obj-to-str [game-state]
  (update game-state :last-update t/format-date))

(defn translate-to-symbols-pre-save [game-state]
  (-> game-state
      (translate-date-obj-to-str)
      (translate-math-operators-to-symbols)))

(defn nested-recs-to-nest-maps [record-node])


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
  (update game-state :last-update t/read-date))

(defn translate-from-symbols-post-load [unprepared-game-state]
  (-> unprepared-game-state
      (translate-math-symbols-to-operators)
      (translate-date-str-to-obj)))

; TODO: Eww, but not as bad as I thought it would be
; TODO: Move the messy bits to another file?
(defn decompress-state [flattened-game-state]
  (let [fgs flattened-game-state
        {:keys [last-update, operator-ranges]} fgs
        {:keys [health, max-health, satiation, max-satiation]} fgs
        {:keys [pain-per-tick, hunger-per-tick, health-per-tick]} fgs
        {:keys [pain-per-wrong, food-for-right]} fgs]

    (gs/->Game-State
      (pe/->Pet satiation max-satiation health max-health)
      (qg/->Question-Generator operator-ranges)
      last-update
      (se/->Settings
        (se/->Sim-Settings health-per-tick pain-per-tick hunger-per-tick)
        (se/->Question-Settings pain-per-tick food-for-right)))))

(defn load-game-save [label]
  (-> (sh/load-raw-map label)
      (decompress-state)
      (translate-from-symbols-post-load)))
