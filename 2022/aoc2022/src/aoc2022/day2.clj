(ns aoc2022.day2
  (:require [clojure.string :as string]
            [clojure.java.io :as io])
  (:gen-class))

(def input-path (io/file "resources" "day2"))
(def input-data (slurp input-path))
(defn non-empty-lines [input]
  ; Given an input string consisting of N match strings
  ; convert to a sequence non-empty lines
  (->> input
       (string/split-lines)
       (filter #(not (string/blank? %)))))


(defn product [x]
  ; [a b c] -> [[a a] [a b] [a c] [b a] ...]
  (for [a x
        b x]
    (list a b)))

; create a list of all possible games and outcomes
(defn first-wins? [a b]
  ; given 2 player's moves, returns the result for the first player
  (case [a b]
    [:rock     :scissors] true
    [:scissors :paper]    true
    [:paper    :rock]     true
    false))
(defn game-outcome [a b]
  (cond
    (= a b)           :draw
    (first-wins? a b) :lose
    (first-wins? b a) :win))
(def possible-games
  (let [possible-moves (product [:rock :paper :scissors])
        add-outcome (fn [moves]
                      (let [[a b] moves]
                        (list a b (game-outcome a b))))]
    (map add-outcome possible-moves)))


; scoring
(defn score-move [m]
  (case m
    :rock     1
    :paper    2
    :scissors 3))
(defn score-outcome [w]
  (case w
    :lose 0
    :draw 3
    :win  6))
(defn score-match [match]
  (let [[_ my-move outcome] match
        move-score    (score-move my-move)
        outcome-score (score-outcome outcome)
        total         (+ move-score outcome-score)]
    total))


; data inference
(defn find-game [matches?]
  (first (filter matches? possible-games)))

(defn infer-move-a [move-b outcome]
  (let [matches? (fn [game]
                   (let [[_ b c] game]
                     (and (= move-b b) (= outcome c))))
        game     (find-game matches?)]
    game))
(defn infer-move-b [move-a outcome]
  (let [matches? (fn [game]
                   (let [[a _ c] game]
                     (and (= move-a a) (= outcome c))))
        game     (find-game matches?)]
    game))
(defn infer-outcome [move-a move-b]
  (let [matches? (fn [game]
                   (let [[a b _] game]
                     (and (= move-a a) (= move-b b))))
        game     (find-game matches?)]
    game))

(defn infer-data [game]
  ; given a single missing element for a game, infer the value)
  (let [[move-a move-b outcome] game]
    (cond
      (nil? move-a) (infer-move-a move-b outcome)
      (nil? move-b) (infer-move-b move-a outcome)
      (nil? outcome) (infer-outcome move-a move-b))))

(defn day2-1 []
  ; A X -> (:rock :rock nil)
  (let [parse-match (fn [match]
                      (let [[a b]      (string/split match #" ")
                            their-move ({"A" :rock "B" :paper "C" :scissors} a)
                            my-move    ({"X" :rock "Y" :paper "Z" :scissors} b)]
                        (list their-move my-move nil)))]
    (->> input-data
         (non-empty-lines)
         (map parse-match)
         (map infer-data)
         (map score-match)
         (reduce +))))

(defn day2-2 []
  ; A X -> (:rock :rock nil)
  (let [parse-match (fn [match]
                      (let [[a b]      (string/split match #" ")
                            their-move ({"A" :rock "B" :paper "C" :scissors} a)
                            outcome    ({"X" :lose "Y" :draw "Z" :win} b)]
                        (list their-move nil outcome)))]
    (->> input-data
         (non-empty-lines)
         (map parse-match)
         (map infer-data)
         (map score-match)
         (reduce +))))

(defn -main [& args]
  (println (day2-1))
  (println (day2-2)))
