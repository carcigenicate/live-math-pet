(ns live-math-pet.game.text.question-asking
  (:require [live-math-pet.game.pet :as pe]
            [live-math-pet.game.question.question-generator :as qg]
            [live-math-pet.game.question.question :as que]
            [live-math-pet.game.operator-symbols :as os]
            [live-math-pet.game.game-state :as gs]
            [live-math-pet.game.text.helpers :as th]
            [helpers.general-helpers :as g]))

(def right-message "Right!")
(def wrong-message "Wrong :(")

(defn raw-ask-question
  "Returns 3 possible values:
  nil if the user wants to stop,
  or, true or false to indicate whether or not the user got the question right."
  [q-gen rand-gen]
  (let [q (qg/simple-random-question q-gen rand-gen)
        ans (que/answer q)

        guess  (g/parse-int
                 (th/ask-for-input (str (que/format-question q os/operator-symbols) ": ")))]

    (when (some? guess)
      (= guess ans))))

; TODO: Try to factor out asking/displaying questions/results using HOF.
; TODO: Accept: - Func taking a Question, to be used to display somewhere. Return the user's answer
; TODO:             - How to indicate to the caller if the answer was right?
; TODO:             - Expect them to use the question to get the answer? Probably cleanest.
; TODO:         - That's it?
(defn ask-question
  "Returns either the new game state that resulted from the user answering a question,
  or nil to indicate that the user wants to stop."
  [game-state rand-gen]
  (let [right-or-stop? (raw-ask-question (:q-gen game-state) rand-gen)
        q-settings (-> game-state :settings :question-settings)
        {foR :food-per-right, paW :pain-per-wrong} q-settings
        [message act] (if right-or-stop? [right-message #(pe/feed % foR)]
                                         [wrong-message #(pe/hurt % paW)])]

    (when (some? right-or-stop?)
      ; FIXME: Eww! Figure out advanced pet above, then re-associate with state?
      (println message (str (act (:pet game-state))))
      (-> game-state
          (update :pet act)
          (gs/apply-time)))))

(defn ask-questions [game-state rand-gen]
  (loop [acc-state game-state]

    (let [result-state? (ask-question acc-state rand-gen)]
      (if (nil? result-state?)
        acc-state
        (recur result-state?)))))