(ns live-math-pet.game.question.question)

(defrecord Question [operator arg1 arg2])

(defn answer [question]
  (let [{:keys [operator arg1 arg2]} question]
    (operator arg1 arg2)))

(defn format-question [question operator-str-map]
  (let [{:keys [operator arg1 arg2]} question]
    (str arg1 " " (operator-str-map operator) " " arg2)))