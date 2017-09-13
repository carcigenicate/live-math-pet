(ns live-math-pet.text.store-menu
  (:require [live-math-pet.store.store-items :as si]
            [live-math-pet.text.text-menu-helpers :as mh]
            [clojure.string :as s]))

(def menu-str-to-option
  (-> (zipmap (map str (range))
              (map first si/items))

      (assoc "e" ::exit)))

(def menu-actions
  (-> si/items
      (assoc ::exit nil)))

(def formatted-menu
  (mh/formatted-menu-strings menu-str-to-option))

(defn prompt-for-menu-str []
  (let [num-chr? #(Character/isDigit ^Character %)
        lower #(Character/toLowerCase ^Character %)]
    (mh/prompt-for-menu-option menu-str-to-option
       #(if (every? num-chr? %)
          %
          (str (lower (first %)))))))

(defn prompt-for-menu-action? [state]
  (mh/print-standard-menu state formatted-menu)

  (-> (prompt-for-menu-str)
      (menu-str-to-option)
      (menu-actions)))

(defn menu-f [state]
  (loop [acc-state state]
    (if-let [action (prompt-for-menu-action? acc-state)]
      (recur (action acc-state))
      acc-state)))