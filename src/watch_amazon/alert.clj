(ns watch-amazon.alert
  (:require [simple-email.core :as mail]))

(def srv (mail/mail-server-from-env))

(def usr (System/getenv "MAILTO"))

(defn mail-subject [product]
  (str "\"" (:name product) "\" trigger"))

(defn mail-body [product]
  (str "Hello,\n\"" (:name product) "\" is at $" (:price product) "."))

(defn alert [product]
  (mail/send-to srv usr (mail-subject product) (mail-body product)))
