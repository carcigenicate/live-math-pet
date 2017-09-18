(ns live-math-pet.game.question.question-asking
  (:require [live-math-pet.game.pet :as pe]
            [live-math-pet.game.game-state :as gs]
            [live-math-pet.game.question.question-generator :as qg]
            [live-math-pet.game.question.question :as que]))

(defn gen-question [state rand-gen]
  (qg/simple-random-question (:q-gen state)
                             rand-gen))

; TODO: Try to factor out asking/displaying questions/results using HOF.
; TODO: Accept: - Func taking a Question, to be used to display somewhere. Return the user's answer
; TODO:             - How to indicate to the caller if the answer was right?
; TODO:             - Expect them to use the question to get the answer? Probably cleanest.
; TODO:         - That's it?
(defn ask-question
  "- question-f should be a function accepting a Question, and returning the user's guess
   - result-f should be a function accepting whether or not the question was answered wrong, and the resulting pet.
     The first argument will either be nil indicating that the question was answered correctly, or the correct answer.
  Returns either the new game state that resulted from the user answering a question,
  or nil to indicate that the user wants to stop."
  [game-state question-f result-f rand-gen]
  (let [q (gen-question game-state rand-gen)
        guess? (question-f q)]
    (if guess?
      (let [right? (que/right-answer? q guess?)
            settings (:settings game-state)
            {foR :food-per-right, paW :pain-per-wrong} settings
            ; TODO: Move message logic to passed func

            action (if right? #(pe/feed % foR)
                              #(pe/hurt % paW))

            advanced-pet (action (:pet game-state))]

        (result-f (if right? nil (que/answer q))
                  advanced-pet)

        (-> game-state
            (assoc :pet advanced-pet)
            (gs/apply-time))))))

(defn ask-questions
  "Asks the user questions, returning the resulting state.
  The descriptions of question-f and result-f can be found in the docs for ask-question."
  [game-state question-f result-f rand-gen]
  (let [dead? #(pe/dead? (:pet %))]
    (loop [acc-state game-state]
      (let [result-state? (ask-question acc-state question-f result-f rand-gen)]
        (if (or (nil? result-state?) (dead? result-state?))
          result-state?
          (recur result-state?))))))