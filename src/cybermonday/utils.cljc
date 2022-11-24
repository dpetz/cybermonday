(ns cybermonday.utils
  (:require [clojure.walk :as walk]
            [clojure.string :as str]))

(defn update-stack-top
  "Update the last element in v with (f (peek v))"
  [v f & rest]
  (conj (pop v) (apply f (peek v) rest)))

(defn make-hiccup-node
  "Creates a hiccup node, consuming a sequence of children"
  ([tag children]
   (apply vector tag {} (filter identity children)))
  ([tag attrs children]
   (apply vector tag attrs (filter identity children))))

(defn hiccup?
  "Tests to see if an element is valid hiccup"
  [element]
  (and (vector? element)
       (keyword? (first element))
       (map? (second element))))

(defn collect-text
  "Given hiccup, return a sequence of just the text items"
  [ir]
  (->> (walk/postwalk
        (fn [item]
          (if (or
               (vector? item)
               (string? item))
            item
            nil))
        ir)
       flatten
       (filter identity)))

(defn gen-id
  "Given an IR node, generate an id based on all its text"
  [node]
  (->> (collect-text node)
       (map #(str/replace % #"[^0-9A-Za-z\s\-]" ""))
       (map #(str/replace % #"\s+" "-"))
       (apply str)
       str/lower-case))

(def html-comment-re #"<!--([\s\S]*?)-->")

(defn cleanup
  "Removes excess whitespace and nils from the resulting AST."
  [hiccup]
  (walk/postwalk
   (fn [item]
     (cond
       (string? item) (when (seq item) item)
       (hiccup? item) (filterv (complement nil?) item)
       :else item))
   hiccup))
