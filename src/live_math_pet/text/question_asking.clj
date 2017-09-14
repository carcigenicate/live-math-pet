(ns live-math-pet.text.question-asking
  (:require [live-math-pet.game.pet :as pe]
            [live-math-pet.game.question.question-generator :as qg]
            [live-math-pet.game.question.question :as que]
            [live-math-pet.operator-symbols :as os]
            [live-math-pet.game.game-state :as gs]

            [live-math-pet.text.helpers :as th]

            [helpers.general-helpers :as g]))

(def right-message "Right")
(defn wrong-message [ans] (str "Wrong (" ans ")"))

(defn raw-ask-question
  "Returns 3 possible values:
  nil if the user wants to stop,
  or, a pair of [the-answer, (true or false to indicate whether or not the user got the question right.)"
  [q-gen rand-gen]
  (let [q (qg/simple-random-question q-gen rand-gen)
        ans (que/answer q)

        guess  (g/parse-int
                 (th/ask-for-input (str (que/format-question q os/operator-symbols) ": ")))]

    (when (some? guess)
      [ans (= guess ans)])))

; TODO: Try to factor out asking/displaying questions/results using HOF.
; TODO: Accept: - Func taking a Question, to be used to display somewhere. Return the user's answer
; TODO:             - How to indicate to the caller if the answer was right?
; TODO:             - Expect them to use the question to get the answer? Probably cleanest.
; TODO:         - That's it?
(defn ask-question
  "Returns either the new game state that resulted from the user answering a question,
  or nil to indicate that the user wants to stop."
  [game-state rand-gen]
  (let [answered? (raw-ask-question (:q-gen game-state) rand-gen)]
    (if answered?
        (let [[answer right?] answered?
              settings (:settings game-state)
              {foR :food-per-right, paW :pain-per-wrong} settings
              [message act] (if right? [right-message #(pe/feed % foR)]
                                       [(wrong-message answer) #(pe/hurt % paW)])

              advanced-pet (act (:pet game-state))]

          (println message (pe/format-pet advanced-pet) "\n")
          (-> game-state
              (assoc :pet advanced-pet)
              (gs/apply-time))))))

(defn ask-questions [game-state rand-gen]
  (loop [acc-state game-state]

    (let [result-state? (ask-question acc-state rand-gen)]
      (if (nil? result-state?)
        acc-state
        (recur result-state?)))))