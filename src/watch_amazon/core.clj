(ns watch-amazon.core
  (:gen-class)
  (:import [java.io.StringWriter])
  (:require [clojure.string :as str :only [join replace]])
  (:require [watch-amazon.alert :as alert])
  (:require [watch-amazon.search :as search])
  (:require [net.cgrand.enlive-html :as html]))

(def okasaki-99
  {:url "http://www.amazon.com/dp/0521663504/"
   :name "Purely Functional Data Structures"
   :trigger 42.10})

(def on-lisp
  {:url "http://www.amazon.com/dp/0130305529/"
   :name "On Lisp"
   :trigger 100.0})

(def base-url "amazon.com/gp/")

(defn fetch-page
  "retrieve the page, parsed into a node hash-map"
  [page]
  (html/html-resource (java.net.URL. page)))

(defn get-product
  "extract the product table from the parsed page node hash-map"
  [doc]
  (let [product (html/select doc [:table.product])]
    (if (empty? product)
      :not-for-sale
      product)))

(defn get-price
  "extract the price from the product table"
  [product]
  (if (= product :not-for-sale)
    :not-for-sale
    (first
     (:content
      (first
       (html/select product [:b.priceLarge]))))))

(defn parse-price [price]
  (if (= price :not-for-sale)
    :not-for-sale
    (Double.
     (str/replace price #"^\$" ""))))

(defn fetch-price [page]
  (parse-price (get-price (get-product (fetch-page page)))))

(defn product-price [product]
  (println "[+] checking " (:name product))
  (let [price (fetch-price (:url product))
	product (into product {:price price})]
    (if (and (not= price :not-for-sale) (< price (:trigger product)))
      (do
	(println "[+] alert!")
	(alert/alert product)))))

(defn watcher [products]
  (println "-- product list --")
  (prn products)
  (loop []
      (println "[+] wake up")
      (dorun (map product-price products))
      (println "[+] back to sleep")
      (Thread/sleep 86400)
      (recur)))

(defn usage []
  (println "lein run <trigger price> <title>"))

(defn -main [& args]
  (let [argc (count args)]
    (if (< argc 2) (usage)
	(let [products (search/search-and-trigger (str/join " " (rest args))
						  (Double. (first args)))]
	  (watcher products)))))
