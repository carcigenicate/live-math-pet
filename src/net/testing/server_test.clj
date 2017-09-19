(ns net.testing.server-test
  (:require [clojure.java.io :as io]
            [clojure.core.async :refer [>! <! go thread]])

  (:import [java.net ServerSocket]))

(defn start-server [accept-f port]
  (let [ss (ServerSocket. port)]

    ; TODO: Exception Handling?
    (while (not (.isClosed ss))
      (let [client (.accept ss)]
        (thread
          (accept-f client))))))

(defn test-accept-f [client]
  (let [w (io/writer client)]))
