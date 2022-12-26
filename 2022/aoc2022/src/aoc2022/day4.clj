(ns aoc2022.day4
  (:require [clojure.string :as string]
            [clojure.java.io :as io])
  (:gen-class))

(def input-path (io/file "resources" "day4"))
(defn input-data []
  (with-open [rdr (io/reader input-path)]
    (->> rdr
         (line-seq)
         (filter #(not (string/blank? %)))
         (reduce conj []))))

(def to-int #(. Integer parseInt %))

(defn ranges []
  ; split on , and flatten
  ; split on -
  ; parse to int
  ; "1-2,3-4" -> ((1 2) (3 4) ...)
  (->> (input-data)
       (map #(string/split % #","))
       (flatten)
       (map #(string/split % #"-"))
       (flatten)
       (map to-int)
       (partition-all 2)
       (partition-all 2)))

(defn ranges-contained? [a b]
  ; true if one range contains the other
  (let [x (fn [a] (nth a 0))
        y (fn [a] (nth a 1))
        contains? (fn [a b]
                 (and (<= (x a) (x b))
                      (>= (y a) (y b))))]
    (or (contains? a b) (contains? b a))))

(defn ranges-overlap? [a b]
  ; true if the ranges overlap at all
  (let [x (fn [a] (nth a 0))
        y (fn [a] (nth a 1))
        overlap? (fn [a b]
                   (or
                    (and (>= (x a) (x b)) (<= (x a) (y b)))
                    (and (>= (y a) (x b)) (<= (y a) (y b)))))]
    (or (overlap? a b) (overlap? b a)))
  )

(defn day4-1 []
  (->> (ranges)
       (map (partial apply ranges-contained?))
       (filter true?)
       (count)))

(defn day4-2 []
  (->> (ranges)
       (map (partial apply ranges-overlap?))
       (filter true?)
       (count)))

(defn -main [& args]
  (println (day4-1))
  (println (day4-2)))
