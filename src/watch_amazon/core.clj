(ns watch-amazon.core
  (:gen-class)
  (:import [java.io.StringWriter])
  (:require [net.cgrand.enlive-html :as html]))

(def purely-functional-data-structures "http://www.amazon.com/dp/0521663504/")

(defn parse-page [page]
  (html/html-resource (java.net.URL. page)))

(defn get-price [page]
  (let [doc (parse-page page)]
    (html/select doc (:span :actualPriceValue))))

(html/select *page* [:table.product])

(defn -main [& args]
  (prn (get-price purely-functional-data-structures)))

;; (:TABLE ((:CLASS "product"))
;;         (:TBODY NIL
;;                 (:TR NIL (:TD ((:CLASS "priceBlockLabel")) "List Price:")
;;                      (:TD NIL
;;                           (:SPAN ((:ID "listPriceValue") (:CLASS "listprice")) "$49.00")))
;;                 (:TR ((:ID "actualPriceRow"))
;;                      (:TD ((:ID "actualPriceLabel") (:CLASS "priceBlockLabelPrice"))
;;                           "Price:")
;;                      (:TD ((:ID "actualPriceContent"))
;;                           (:SPAN ((:ID "actualPriceValue"))
;;                                  (:B ((:CLASS "priceLarge")) "$42.08"))
