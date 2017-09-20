(ns net.rand-num-test.client-test
  (:require [helpers.net-helpers :as nh]
            [clojure.core.async :refer [chan >!! <! thread go-loop]])

  (:import [java.net Socket]))

(def msg-q (chan 100))

(nh/start-message-loop msg-q)

(defn test-connect-f [^Socket server-sock]
  (let [rec (nh/read-line server-sock)]
    (nh/queue-message msg-q "Received:" rec)
    (Thread/sleep 2000)

    (nh/write server-sock rec)
    (nh/queue-message msg-q "Wrote: " rec)))

(defn simple-connection []
  (nh/connect-to "localhost" 5555 test-connect-f))

(defn async-connect-many [n]
  (doseq [m (range n)]
    (thread (simple-connection))))
