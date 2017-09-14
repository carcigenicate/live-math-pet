(ns live-math-pet.store.store-actions)

(defn increase-pet-stat [state key by]
  (update-in state [:pet key] #(+ % by)))

(defn fully-revive-pet [state]
  (update state :pet #(assoc % :health (:max-health %))))

(defn increase-gain-risk [state inc-food-by inc-pain-by]
  (update state :settings
          #(-> %
               (update :food-per-right (partial + inc-food-by))
               (update :pain-per-wrong (partial + inc-pain-by)))))

