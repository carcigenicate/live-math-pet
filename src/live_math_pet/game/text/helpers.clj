(ns live-math-pet.game.text.helpers)

(defn print-fl [& msgs]
  (apply print msgs)
  (flush))

(defn ask-for-input [prompt]
  (print-fl prompt)
  (read-line))