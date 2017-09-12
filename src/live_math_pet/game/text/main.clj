(ns live-math-pet.game.text.main
  (:require [helpers.general-helpers :as g]

            [live-math-pet.game.main-loop :as ml]
            [live-math-pet.game.text.text-menu :as me]
            [live-math-pet.game.saving.game-save :as g-save]
            [live-math-pet.game.defaults :as default])

  (:import [java.io FileNotFoundException]))

(defn death-f [state]
  (println "Your pet died :(\nCreating a new save..."))

(defn text-main [state label]
  (ml/main-loop state label me/menu-f death-f))