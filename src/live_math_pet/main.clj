(ns live-math-pet.main
  (:require [live-math-pet.game.text.main :as t-main]
            [live-math-pet.game.defaults :as default]
            [live-math-pet.game.saving.game-save :as g-save])

  (:import (java.io FileNotFoundException))

  (:gen-class))


(defn load-with-default [label default-state]
  (try
    (g-save/load-game-save label)

    (catch FileNotFoundException e
      default-state)))

(defn -main [& [label?]]
  (let [label (or label? default/game-label)
        state (load-with-default label (default/game-state-for-now))]

    (t-main/text-main state label)))
