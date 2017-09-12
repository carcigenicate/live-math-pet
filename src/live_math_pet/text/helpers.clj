(ns live-math-pet.text.helpers)

(defn print-fl [& msgs]
  (apply print msgs)
  (flush))

(defn ask-for-input [prompt]
  (print-fl prompt)
  (read-line))