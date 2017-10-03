(ns live-math-pet.gui.helpers
  (:import (javax.swing JRootPane)
           (java.awt Button)))

(defn set-default-button [^JRootPane root-pane, ^Button button]
  (.setDefaultButton (.getRootPane root-pane) button))