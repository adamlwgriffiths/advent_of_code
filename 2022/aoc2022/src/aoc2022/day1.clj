(ns aoc2022.day1
  (:require [clojure.string :as string]
            [clojure.java.io :as io])
  (:gen-class))

(def input-path (io/file "resources" "day1"))
(def input-data (slurp input-path))


(defn into-groups [s](string/split s #"\n\n"))
(defn parse-group [g]
 (->> g
      (string/split-lines)
      (map #(. Integer parseInt %))))
(defn sum [g] (reduce + g))

(defn day1-1 []
  ; split the file into lines
  ; split the lines into groups delimited by empty lines
  ; remove the empty groups left over from the empty lines
  ; sum each group
  ; return the max
  (->> input-data
       (into-groups)
       (map parse-group)
       (map sum)
       (apply max)))

(defn day1-2 []
  (->> input-data
       (into-groups)
       (map parse-group)
       (map sum)
       (sort >)
       (take 3)
       (sum)))

(defn -main [& args]
  (println (day1-1))
  (println (day1-2)))
