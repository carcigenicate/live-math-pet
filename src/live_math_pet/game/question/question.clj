(ns live-math-pet.question.question)

(defrecord Question [operator arg1 arg2])

(defn format-question [question operator-str-map]
  (let [{:keys [operator arg1 arg2]} question]
    (str arg1 " " (operator-str-map operator) " " arg2)))