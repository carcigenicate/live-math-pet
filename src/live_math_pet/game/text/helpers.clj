(ns live-math-pet.game.text.helpers)

(defn print-fl [& msgs]
  (apply print msgs)
  (flush))

(defn ask-for-input [prompt]
  (print-fl prompt)
  (read-line))

(defn format-pet [pet]
  (let [{:keys [health max-health satiation max-satiation]} pet]
    (str "{HP :" health "/" max-health " -  Sat: " satiation "/" max-satiation "}")))