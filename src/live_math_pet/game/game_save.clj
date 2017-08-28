(ns live-math-pet.game.game-save
  (:require [live-math-pet.game.time :as t]
            [live-math-pet.game.saving :as sa]))

(defrecord Game-Save [pet save-date])

(defn new-save [pet save-date]
  (->Game-Save pet save-date))

(defn save-game-save [label game-save]
  (sa/save-associative label
    (update game-save :save-date t/format-date)))

(defn load-game-save [label]
  (let [save (sa/load-record label map->Game-Save)]
    (update save :save-date t/format-date)))
