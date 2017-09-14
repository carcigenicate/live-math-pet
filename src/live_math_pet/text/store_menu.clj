(ns live-math-pet.text.store-menu
  (:require [live-math-pet.store.store-items :as si]
            [live-math-pet.text.text-menu-helpers :as mh]
            [clojure.string :as s]
            [helpers.general-helpers :as g]))

(def menu-str->items
  (-> (zipmap (map str (range))
              si/items)

      (assoc "e" nil)))

(def formatted-menu
  (vec
    (for [[s i] menu-str->items]
      [s (if i (str i) "Exit")])))

(defn prompt-for-menu-str []
  (let [prep #(str (Character/toLowerCase ^Character (first %)))]
    (prep
      (g/ask-for-input mh/prompt mh/entry-error-message
                       #(contains? menu-str->items (prep %))))))

(defn prompt-for-menu-item? [state]
  (mh/print-standard-menu state formatted-menu)

  (-> (prompt-for-menu-str)
      (menu-str->items)))

(defn menu-f [state]
  (loop [acc-state state]
    (if-let [item (prompt-for-menu-item? acc-state)]
      (recur (si/apply-item-to-state acc-state item))
      acc-state)))