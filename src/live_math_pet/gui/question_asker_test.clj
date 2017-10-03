(ns live-math-pet.gui.question-asker-test
  (:require [seesaw.core :as sc]
            [seesaw.dev :as sd]
            [live-math-pet.game.question.question-generator :as qg]
            [live-math-pet.game.question.question-types :as qt]
            [live-math-pet.game.question.question :as que]
            [live-math-pet.operator-symbols :as os]
            [live-math-pet.gui.helpers :as gh]

            [live-math-pet.gui.question-panel.helpers :as qh]
            [helpers.general-helpers :as g]))

; TODO: Use question/question-asker

(def q-font "Arial-100")
(def ans-font "Arial-110")

(def q-gen (qg/new-question-generator qt/plus-minus-mult-easy))

(def global-rand-gen (g/new-rand-gen 99))

(defn new-question []
  (qg/simple-random-question q-gen global-rand-gen))

(defn question-label []
  (sc/text :font q-font
           :id :question-display
           :editable? false
           :halign :right
           :columns 8))

(defn answer-input []
  (sc/text :font ans-font
           :columns 5
           :id :guess-input
           :user-data nil))

(defn outcome-handler [right? correct-answer]
  (let [message  (if right?
                   "Correct!"
                   (str "Wrong(" correct-answer ")"))]

    (println message)))

(defn submit-handler [parent]
  (let [guess (qh/get-user-input parent)
        input (sc/select parent [:#guess-input])
        clear-answer #(sc/text! input "")]

    (if-let [parsed-guess (g/parse-int guess)]
      (let [correct-answer (qh/get-question-answer parent)]
        (outcome-handler (= correct-answer parsed-guess) correct-answer)
        (qh/set-question parent (new-question))
        (clear-answer)
        (sc/request-focus! input))

      (do
        (println "Bad input!")
        (clear-answer)))))

(defn submit-button []
  (sc/button :text "Submit"
             :id :submit-button
             :font q-font))

(defn question-panel []
  (let [q-label (question-label)
        ans-input(answer-input)
        submit-button (submit-button)]

    (sc/border-panel :center (sc/flow-panel :items [q-label ans-input])
                     :south submit-button)))

(defn main-panel []
  (let [q-panel (question-panel)]
    (sc/border-panel
      :north
      :center q-panel)))

(defn main-frame []
  (let [q-label (question-label)
        ans-input(answer-input)

        main-panel (main-panel)
        main-frame (sc/frame :title "Question Generator",
                             :content main-panel)

        sub-button  (sc/select main-frame [:#submit-button])]

    (sc/listen sub-button
       :action (fn [_] (submit-handler main-frame)))

    (gh/set-default-button main-panel sub-button)

    main-frame))

(defn -main []
  (let [frame (main-frame)]
    (qh/set-question frame (new-question))

    (-> frame
      (sc/pack!)
      (sc/show!))

    (sc/request-focus! (sc/select frame [:#guess-input]))))