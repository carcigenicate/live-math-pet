(ns live-math-pet.game.text.menu
  (:require [live-math-pet.game.text.question-asking :as qa]

            [clojure.string :as s]
            [helpers.general-helpers :as g]))

; TODO: Generalize to allow use in Quil version?

(defn rev
  "Returns a function that takes its arguments in reverse order."
  [f]
  (fn [& args]
    (apply f (reverse args))))

(def menu-chars
  {\m ::main
   \s ::store
   \f ::feed})

; TODO: LOL. Get rid of `rev`
(defn menu-actions
  "Returns a function that takes a game-state and returns an altered game-state."
  [option-key rand-gen]
  ((rev get) option-key
    {::main identity

     ::store identity

     ::feed #(qa/ask-questions % rand-gen)}))

(defn menu-actions-by-char [option-char rand-gen]
  (menu-actions (menu-chars option-char)
                rand-gen))

; FIXME: ---------- Console specific code below ----------

(def formatted-menu-chars
  (into {}
    (for [[c k] menu-chars]
      [(s/capitalize (name k))
       (str (Character/toUpperCase ^Character c))])))

(defn print-menu-options []
  (doseq [[n k] formatted-menu-chars]
    (println (str n ": " k))))

(defn ask-for-menu-option []
  (let [lower-first #(Character/toLowerCase ^Character (first %))]
    (lower-first
      (g/ask-for-input ">: " "Invalid option.\n"
                       #(menu-chars (lower-first %))))))