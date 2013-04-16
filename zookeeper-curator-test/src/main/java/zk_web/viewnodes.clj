(ns zk-web.viewnodes
  (:require [zk-web.zk :as zk]
            [clojure.string :as str])
  (:use [zk-web.util]
        ))


(defn init-client [addr]
  (zk/mk-zk-cli addr))


(def cli (init-client "localhost:2181"))

(def pt
  (zk/ls cli "/"))

