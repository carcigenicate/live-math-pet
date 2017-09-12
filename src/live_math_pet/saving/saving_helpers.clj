(ns live-math-pet.saving.saving-helpers
  (:require [clojure.edn :as edn]))

(def save-path "./")
(def save-extension "pet")

(defn label-path [label]
  (str save-path label \. save-extension))

(defn save-associative [associative label]
  (spit (label-path label)
        (into {} associative)))

(defn load-raw-map [label]
  (edn/read-string
    (slurp (label-path label))))

(defn load-record [label map-constructor]
  (map-constructor
    (load-raw-map label)))