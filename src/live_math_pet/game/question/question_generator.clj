(ns live-math-pet.game.question.question-generator
  (:require [helpers.general-helpers :as g]
            [live-math-pet.game.question.question :as que]))

(defrecord Question-Generator [operator-ranges])

(defn simple-random-question [q-gen rand-gen]
  (let [ranges (:operator-ranges q-gen)
        [op [mi ma]] (g/random-from-collection ranges rand-gen)
        rn #(g/random-int mi (inc ma) rand-gen)]
    (que/->Question op (rn) (rn))))