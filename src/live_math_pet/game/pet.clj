(ns live-math-pet.game.pet
  (:require [helpers.general-helpers :as g]))

; TODO: Happiness? Based on repeated good/bad health?
(defrecord Pet [hunger health max-health max-hunger])

(defn- clamp-health [pet]
  (update pet :health
          #(g/clamp % 0 (:max-health pet))))

(defn- clamp-hunger [pet]
  (update pet :hunger
          #(g/clamp % 0 (:max-hunger pet))))

(defn heal [pet by]
  (-> pet
      (update :health #(+ % by))
      (clamp-health)))

(defn hurt [pet by]
  (heal pet (- by)))

(defn feed [pet by]
  (-> pet
      (update :hunger #(+ % by))
      (clamp-hunger)))

(defn starve [pet by]
  (feed pet (- by)))

