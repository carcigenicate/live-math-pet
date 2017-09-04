(ns live-math-pet.game.operator-symbols
  (:require [clojure.set :as set]))

(def operator-symbols
  {+ \+
   - \-
   * \*
   \ \\})

(def operator-from-symbol
  (set/map-invert operator-symbols))