(ns live-math-pet.question.question-types)

(def easy-range [0 10])

(def plus-minus-easy
  {+ easy-range
   - easy-range})

(def plus-minus-mult-easy
  (into plus-minus-easy
    {* easy-range}))

(def basic-easy
  (into plus-minus-mult-easy
    {/ [1 (second easy-range)]}))