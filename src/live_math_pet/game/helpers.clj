(ns live-math-pet.game.helpers)

(defmacro defmap
  "Generates a defn that creates a function with the name (str \"->\" map-name) that
    returns a map. The map will have keys of fields, with the values of the arguments passed to the function."
  [map-name fields]
  (let [adjusted-name# (str "->" map-name)
        args# (mapv (comp symbol name)  fields)
        body-map# (into {} (map #(do [% (symbol %2)]) fields args#))]

    `(defn ~(symbol adjusted-name#) ~args# ~body-map#)))