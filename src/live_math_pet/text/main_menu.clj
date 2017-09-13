(ns live-math-pet.text.main-menu
  (:require [live-math-pet.text.question-asking :as qa]
            [live-math-pet.game.pet :as pe]
            [live-math-pet.text.text-menu-helpers :as mh]
            [live-math-pet.text.store-menu :as sm]

            [clojure.string :as s]

            [helpers.general-helpers :as g]))


; TODO: Generalize to allow use in Quil version?
; The Quil version won't need ::reload. Redo the menu per implementation?
; Protocol? Just provide a "menu" function? Should return a state altering function.

(def q-rand-gen (g/new-rand-gen))

(defn hurt-starve-f [state]
  (update state :pet
          #(-> %
               (pe/starve 10)
               (pe/hurt 10))))

(def menu-str-to-key-map
  {"r" ::reload
   "s" ::store
   "f" ::feed
   "t" ::test
   "e" ::exit})

(def menu-actions
  {::reload identity
   ::store sm/menu-f
   ::feed #(qa/ask-questions % q-rand-gen)
   ::test hurt-starve-f
   ::exit nil})

(def formatted-options
  (mh/formatted-menu-strings menu-str-to-key-map))

(defn menu-action-by-char [option-char]
  (menu-actions (menu-str-to-key-map option-char)))

(defn prompt-for-menu-option []
  (mh/prompt-for-menu-option menu-str-to-key-map
                             #(s/lower-case (str (first %)))))

(defn menu-f [state]
  (mh/print-standard-menu state formatted-options)

  (when-let [menu-f (menu-action-by-char (prompt-for-menu-option))]
    (menu-f state)))
