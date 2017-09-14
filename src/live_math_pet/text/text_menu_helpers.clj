(ns live-math-pet.text.text-menu-helpers
  (:require [clojure.string :as s]
            [helpers.general-helpers :as g]
            [live-math-pet.game.pet :as pe]))

(def prompt ">: ")
(def entry-error-message "Invalid option.\n")

(defn format-str [^String s]
  (-> s
      (s/upper-case)))

(defn format-keyword [k]
  (let [str-k (name k)
        words (s/split str-k #"\-")]

    (s/join " "
      (map s/capitalize words))))

; TODO: Generalize
(defn formatted-menu-strings
  "Takes a map mapping characters to keywords, and returns a map of formatted strings ready for pretty printing."
  [menu-char-map]
  (into {}
    (for [[s k] menu-char-map]
      [(format-str s)
       (format-keyword k)])))

(defn print-menu-options [formatted-char-map]
  (doseq [[n k] formatted-char-map]
    (println (str n ": " k))))

(defn prompt-for-menu-option
  "The preprocessor is applied to the user's input,
    then checked for membership in the binding map."
  [binding-map preprocessor]
  (preprocessor
      (g/ask-for-input prompt entry-error-message
                       #(binding-map (preprocessor %)))))

(defn print-standard-menu [state formatted-options]
  (println (str "\n" (-> state :pet pe/format-pet)))
  (print-menu-options formatted-options))

; TODO: Not going to work as-is. Will need to be passed too many things to justify it right now
#_
(defn general-menu-f [state formatted-options menu-action-map]
  (println (str "\n" (-> state :pet pe/format-pet)))
  (print-menu-options formatted-options)

  (when-let [menu-f (menu-action-by-char (prompt-for-menu-option))]
    (menu-f state)))

