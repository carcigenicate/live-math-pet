(ns live-math-pet.store.store-actions)

(defn increase-pet-stat [state key by]
  (update-in state [:pet key] #(+ % by)))

(defn fully-revive-pet [state]
  (update state :pet #(assoc % :health (:max-health %))))