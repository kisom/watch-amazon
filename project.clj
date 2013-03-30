(defproject watch-amazon "1.0.0"
  :description "Watch Amazon for book prices."
  :url "http://github.com/kisom/watch-amazon"
  :main watch-amazon.core
  :license {:name "ISC License"
	    :url "http://tyrfingr.is/licenses/LICENSE.ISC"}
  :dependencies [[org.clojure/clojure "1.5.1"]
		 [enlive "1.1.1"]
		 [hiccup "1.0.3"]
		 [simple-email "1.0.7"]])
