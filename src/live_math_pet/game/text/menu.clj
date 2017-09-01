(ns live-math-pet.game.text.menu
  (:require [live-math-pet.game.text.question-asking :as qa]))

(defn rev
  "Returns a function that takes its arguments in reverse order."
  [f]
  (fn [& args]
    (apply f (reverse args))))

(def menu-chars
  {::main \m
   ::store \s
   ::feed \f})

; TODO: LOL. Get rid of `rev`
(defn menu-options [option-key rand-gen]
  ((rev get) option-key
    {::main identity

     ::store identity

     ::feed #(qa/ask-questions % rand-gen)}))
