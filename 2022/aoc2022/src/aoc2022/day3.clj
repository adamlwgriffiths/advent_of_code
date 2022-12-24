(ns aoc2022.day3
  (:require [clojure.string :as string]
            [clojure.java.io :as io]
            [clojure.set :as set])
  (:gen-class))

(def input-path (io/file "resources" "day3"))
(def input-data (slurp input-path))
(defn non-empty-lines [input]
  ; Given an input string consisting of N match strings
  ; convert to a sequence non-empty lines
  (->> input
       (string/split-lines)
       (filter #(not (string/blank? %)))))

(def lowercase-letters "abcdefghijklmnopqrstuvwxyz")
(def uppercase-letters (string/upper-case "abcdefghijklmnopqrstuvwxyz"))
(def letters (string/join [lowercase-letters uppercase-letters]))

(def rucksacks (non-empty-lines input-data))
(defn compartments [rucksack]
  ; "abcdef" -> (("abc") ("def"))
  (let [len      (count rucksack)
        half-len (/ len 2)]
    (list
     (string/join (take half-len rucksack))
     (string/join (drop half-len rucksack)))))

(defn priority [char]
  ; a -> 1 ... z -> 26, A -> 27 ... Z -> 52
  (let [index (string/index-of letters (str char))
        score (+ index 1)]
    score))

(defn common-char [items]
  (let [sets (map set items)]
    (first (reduce set/intersection sets))))

(defn day3-1 []
  ; take all rucksacks
  ; divide each rucksack into 2
  ; flatten this into a single list
  ; group into groups of 2
  ; find the common item
  ; map to priority
  ; sum
  (->> rucksacks
       ; ("abcd" "efgh")
       (map compartments)
       ; (("ab" "cd") ("ef "gh))
       (map common-char)
       ; (\c \d)
       (map priority)
       ; (3 4)
       (reduce +)))

(defn day3-2 []
  ; take all rucksacks
  ; group into groups of 3
  ; find the common item
  ; map to priority
  ; sum
  (->> rucksacks
       ; ("abcd" "efgh" "ijkl" "mno")
       (partition-all 3)
       ; (("abcd" "efgh" "ijkl") ("mno" ...))
       (map common-char)
       ; (\c \d)
       (map priority)
       ; (3 4)
       (reduce +)))

(defn -main [& args]
  (println (day3-1))
  (println (day3-2)))
