(ns live-math-pet.main
  (:require [live-math-pet.text.main :as t-main]
            [live-math-pet.game.defaults :as default]
            [live-math-pet.saving.game-save :as g-save])

  (:import [java.io FileNotFoundException])

  (:gen-class))

; TODO:
;  - Finishing touches before trying full graphics
;  - Generalize question-asking loop

(defn load-with-default [label default-state]
  (try
    (g-save/load-game-save label)

    (catch FileNotFoundException e
      default-state)))

(defn -main [& [label?]]
  (let [label (or label? default/game-label)
        state (load-with-default label (default/game-state-for-now))]

    (t-main/text-main state label)))