(ns net.rand-num-test.server-test
  (:require [clojure.java.io :as io]
            [clojure.core.async :refer [>! <! go chan go-loop]]

            [helpers.net-helpers :as nh])

  (:import [java.net ServerSocket Socket]
           [java.io IOException]))

(def msg-q (chan 100))
(nh/start-message-loop msg-q)

(defn q [& msgs]
  (apply nh/queue-message msg-q msgs))

(defn random-num-sender [^Socket client]
  (let [n (str (- (rand-int 100) 50))]
    (with-open [c client]
      (q "Accepted a connection from" (nh/pretty-address c)
         "\nSent" n "from thread" (.getId (Thread/currentThread)))

      (nh/write c n)

      (let [response (nh/read-line c)]
        (q "Recieved:" response)))))

(defn simple-error-handler [server-sock exception]
  (do
    (q (.getMessage exception))
    (.close server-sock)))

(defn simple-rand-num-server [port]
  (nh/start-simple-server port random-num-sender
                          simple-error-handler))

(defn async-rand-num-server [port]
  (let [c (chan 100)]
    (nh/start-async-server port random-num-sender simple-error-handler)))
