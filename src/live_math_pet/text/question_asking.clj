(ns live-math-pet.text.question-asking
  (:require [live-math-pet.game.pet :as pe]
            [live-math-pet.game.question.question-generator :as qg]
            [live-math-pet.game.question.question :as que]
            [live-math-pet.game.question.question-asking :as qa]
            [live-math-pet.game.game-state :as gs]

            [live-math-pet.operator-symbols :as os]

            [live-math-pet.text.helpers :as th]

            [helpers.general-helpers :as g]))

(def right-message "Right")
(defn wrong-message [ans] (str "Wrong (" ans ")"))

(defn question-f
  "Returns 3 possible values:
  nil if the user wants to stop,
  or, a pair of [the-answer, (true or false to indicate whether or not the user got the question right.)"
  [question]
  (let [guess  (g/parse-int
                 (th/ask-for-input (str (que/format-question question os/operator-symbols) ": ")))]

    guess))

(defn result-f [right-answer? advanced-pet]
  (let [msg (if right-answer? (wrong-message right-answer?)
                              right-message)]

    (println (str msg " - " (pe/format-pet advanced-pet) "\n"))))

(defn ask-questions [game-state rand-gen]
  (qa/ask-questions game-state
                    question-f
                    result-f
                    rand-gen))
