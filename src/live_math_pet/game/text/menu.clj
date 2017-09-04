(ns live-math-pet.game.text.menu
  (:require [live-math-pet.game.text.question-asking :as qa]

            [clojure.string :as s]
            [helpers.general-helpers :as g]
            [live-math-pet.game.pet :as pe]))

; TODO: Generalize to allow use in Quil version?

(defn rev
  "Returns a function that takes its arguments in reverse order."
  [f]
  (fn [& args]
    (apply f (reverse args))))

(def menu-chars
  {\r ::reload
   \s ::store
   \f ::feed
   \t ::test
   \e ::exit})

(defn hurt-starve [state]
  (update state :pet
          #(-> %
               (pe/starve 10)
               (pe/hurt 10))))

; TODO: LOL. Get rid of `rev`
(defn menu-actions
  "Returns a function that takes a game-state and returns an altered game-state,
    or nil to indicate exiting."
  [option-key rand-gen]
  ((rev get) option-key
    {::reload  identity

     ::store identity

     ::feed  #(qa/ask-questions % rand-gen)

     ::test  hurt-starve

     ::exit nil}))

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