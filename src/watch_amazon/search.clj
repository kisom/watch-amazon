(ns watch-amazon.search
  (:require [hiccup.util])
  (:require [clojure.string :as str :only [replace]])
  (:require [net.cgrand.enlive-html :as html]))

(def search-url
  "http://www.amazon.com/s/ref=nb_sb_noss")

(declare product-to-map)

(defn search-for-book [keywords]
  (let [params {:url "search-alias/stripbooks"
		:field-keywords keywords}
	url (hiccup.util/url search-url params)
	doc (html/html-resource url)]
    (map product-to-map (html/select doc [:div.productTitle]))))

(defn product-to-map [product]
  (let [product (first (:content product))]
    {:name (str/replace (first (:content product)) #"^\s*(.+)\s*$" "$1")
     :url (:href (:attrs product))}))

(defn title-match? [title product]
  (pos?
   (count
    (re-seq
     (re-pattern (str "(?i)" title))
     (:name product)))))

(defn search-for-title [title]
  (let [products (search-for-book title)]
    (filter #(title-match? title %) products)))

(defn search-and-trigger [title trigger]
  (let [results (search-for-title title)]
    (map #(into % {:trigger trigger})
	 results)))
