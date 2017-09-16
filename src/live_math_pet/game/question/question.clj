(ns live-math-pet.game.question.question
  (:require [live-math-pet.helpers :as h]))

(defn new-question [operator arg1 arg2]
  (h/gen-arg-map new-question
    operator arg1 arg2))

(defn answer [question]
  (let [{:keys [operator arg1 arg2]} question]
    (operator arg1 arg2)))

(defn right-answer? [question guess]
  (= (answer question)
     guess))

(defn format-question [question operator-str-map]
  (let [{:keys [operator arg1 arg2]} question]
    (str arg1 " " (operator-str-map operator) " " arg2)))