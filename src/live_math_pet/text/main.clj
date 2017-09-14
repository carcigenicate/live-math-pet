(ns live-math-pet.text.main
  (:require [helpers.general-helpers :as g]

            [live-math-pet.text.main-menu :as me]

            [live-math-pet.game.main-loop :as ml]
            [live-math-pet.saving.game-save :as g-save]
            [live-math-pet.game.defaults :as default]
            [live-math-pet.time-alive :as ta]
            [live-math-pet.helpers :as h])

  (:import [java.io FileNotFoundException]))

(defn days-alive [state]
  (let [hours-alive (ta/hours-alive state)]
    (h/format-round (/ hours-alive 24.0) 3)))

(defn death-f [state]
  (println "Your pet died :(\nCreating a new save..."))

(defn text-main [state label]
  (println "Your pet has been alive for" (days-alive state) "days.")

  (ml/main-loop state label me/menu-f death-f))