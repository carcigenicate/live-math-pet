(ns live-math-pet.gui.question-panel.helpers
  (:require [live-math-pet.operator-symbols :as os]
            [live-math-pet.game.question.question :as que]
            [seesaw.core :as sc]))

(defn format-question [question]
  (str (que/format-question question os/operator-symbols)
       " ="))

(defn question-display [parent]
  (sc/select parent [:#question-display]))

(defn set-question [parent question]
  (let [question-label (question-display parent)]
    (sc/config! question-label
       :text (format-question question),
       :user-data question)))

(defn get-question-answer [parent]
  (let [question-label (question-display parent)
        question (sc/config question-label :user-data)]
    (que/answer question)))

(defn get-user-input [parent]
  (sc/text
    (sc/select parent [:#guess-input])))