(ns live-math-pet.game.pet
  (:require [helpers.general-helpers :as g]
            [live-math-pet.helpers :as h]))

(def well-fed-percentage 0.8)

; TODO: Happiness? Based on repeated good/bad health?

(defn new-pet [max-health max-satiation]
  {:satiation max-satiation
   :max-satiation max-satiation
   :health max-health
   :max-health max-health})

(defn- clamp-health [pet]
  (update pet :health
          #(g/clamp % 0 (:max-health pet))))

(defn- clamp-satiation [pet]
  (update pet :satiation
          #(g/clamp % 0 (:max-satiation pet))))

(defn heal [pet by]
  (-> pet
      (update :health #(+ % by))
      (clamp-health)))

(defn hurt [pet by]
  (heal pet (- by)))

(defn feed [pet by]
  (-> pet
      (update :satiation #(+ % by))
      (clamp-satiation)))

(defn starve [pet by]
  (feed pet (- by)))

(defn dead? [pet]
  (<= (:health pet) 0))

(defn starving? [pet]
  (<= (:satiation pet) 0))

(defn well-fed? [pet]
  (>= (:satiation pet)
      (* (:satiation pet) well-fed-percentage)))

(defn format-pet [pet]
  (let [{:keys [health max-health satiation max-satiation]} pet
        r #(h/format-round % 3)]
    (str "{HP: " (r health) "/" max-health
         " -  Sat: " (r satiation) "/" max-satiation "}")))