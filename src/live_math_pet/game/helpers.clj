(ns live-math-pet.game.helpers
  (:require [clojure.string :as s]))

(defmacro defmap
  "Generates a defn that creates a function with the name (str \"->\" map-name) that
    returns a map. The map will have keys of fields, with the values of the arguments passed to the function."
  [map-name fields]
  (let [adjusted-name# (str "->" map-name)
        args# (mapv (comp symbol name)  fields)
        body-map# (into {} (map #(do [% (symbol %2)]) fields args#))]

    `(defn ~(symbol adjusted-name#)
       ~(str args#) ; Setting the docstring as the name of the expected arguments
       ~args#
       ~body-map#)))

(defmacro gen-arg-map [parent-fn & values]
  `(let [args# (-> ~parent-fn var meta :arglists first)]
     (into {}
       (map #(vector (keyword %) %2) args# ~(vec values)))))