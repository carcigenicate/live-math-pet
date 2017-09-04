(ns live-math-pet.game.saving.game-save
  (:require [live-math-pet.game.time :as t]
            [live-math-pet.game.operator-symbols :as os]

            [live-math-pet.game.game-state :as gs]
            [live-math-pet.game.settings :as se]

            [clojure.edn :as edn]
            [live-math-pet.game.pet :as pe]
            [live-math-pet.game.question.question-generator :as qg]))

(def operator-path [:q-gen :operator-ranges])

(def save-path "./")
(def save-extension "pet")

(def reader-options {:readers {'live-math-pet.game.game-state.Game-State gs/map->Game-State

                               'live-math-pet.game.question.question-generator.Question-Generator
                               qg/map->Question-Generator

                               'live-math-pet.game.pet.Pet pe/map->Pet

                               'live-math-pet.game.settings.Settings se/map->Settings
                               'live-math-pet.game.settings.Question-Settings se/map->Question-Settings
                               'live-math-pet.game.settings.Sim-Settings se/map->Sim-Settings}})

(defn label-path [label]
  (str save-path label \. save-extension))

(defn save-record [label record]
  (spit (label-path label) (pr-str record)))

(defn load-raw-map [label]
  (edn/read-string
    (slurp (label-path label))))

(defn load-record [label map-constructor]
  (map-constructor
    (load-raw-map label)))

(defn translate-operators-to-symbols [game-state]
  (update-in game-state operator-path
    #(into {}
       (map (fn [[o r]]
              [(os/operator-symbols o) r]) %))))

(defn save-game-save [label game-state]
  (->> game-state
       (translate-operators-to-symbols)
       (save-record label)))

(defn translate-symbols-to-operators [game-state]
  (update-in game-state operator-path
    #(into {}
       (map (fn [[s r]]
              [(os/operator-from-symbol s) r]) %))))

(defn load-game-save [label]
  (-> (sh/load-record label gs/map->Game-State)
      (translate-symbols-to-operators)))
