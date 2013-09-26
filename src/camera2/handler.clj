(ns camera2.handler
  (:require [aleph.http :as aleph]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [aleph.http :refer [start-http-server wrap-ring-handler]]
            [compojure.core :refer [GET defroutes]]
            [lamina.core :refer [receive siphon]])
  (:gen-class))

(def test-broadcast-channel (lamina.core/permanent-channel))

(defn chat-handler [ch handshake]
  (receive ch
    (fn [name]
      (siphon (lamina.core/map* #(str name ": " %) ch) test-broadcast-channel)
      (siphon test-broadcast-channel ch))))

(defroutes test-app-routes
  (GET "/" [] "Hello World")
  (GET "/socket" []
       (println "socket")
       (aleph/wrap-aleph-handler chat-handler))
  (route/resources "/")
  (route/not-found "Not Found"))

(def test-app
  (handler/site test-app-routes))

(defn -main [& args]
  (start-http-server (wrap-ring-handler
                      test-app)
                     {:port 5000 :websocket true})
  (println "server started"))
