(ns aoc2022.day5
  (:require [clojure.string :as string]
            [clojure.java.io :as io])
  (:gen-class))

(def input-path (io/file "resources" "day5"))
(defn input-data []
  (with-open [rdr (io/reader input-path)]
    (->> rdr
         (line-seq)
         (reduce conj []))))

(defn prinret "Print + Return" [x] (prn x) x)
(defn str-to-int [s] (. Integer parseInt s))
(defn to-0-based [n] (- n 1))
(defn to-1-based [n] (+ n 1))
(defn replace-item
  "Returns a list with the n-th item of l replaced by v."
  [l n v]
  (concat (take n l) (list v) (drop (inc n) l)))
(defn find-index [match? l]
  (count (take-while #(not (match? %)) l)))
(defn n-lists [n] (take n (repeatedly list)))
(defn remove-blanks [c] (filter #(not (string/blank? %)) c))

; initial state parsing
(defn parse-block-at [row n]
  ; extract the initial state block character for stack N
  ; [0] [1]...[n]
  ; 1 + n*4
  (let [offset (+ 1 (* 4 n))
        value  (nth row offset)]
    (if (= \space value)
      nil
      value)))

(defn parse-row [row stacks]
  ; given an initial state row
  ; add to each stack the resepective block value
  ; if one exists, otherwise leave the stack alone
  (let [parse-stack (fn [index stack]
                      ; only add to the stack if it's not empty
                      (let [value (parse-block-at row index)]
                        (if (nil? value)
                          stack
                          (conj stack value))))]
    (map-indexed parse-stack stacks)))

(defn parse-initial-state-rows [input stacks]
  ; parse all the rows of the initial state
  (if (empty? input)
    stacks
    (recur
      (drop-last input)
      (parse-row (last input) stacks))))

(defn parse-initial-state [state-input]
  ; parse the entire initial state rows
  ; firstly, determine the number of stacks from the last line
  ; then parse the remaining input
  (let [indices     (string/split (last state-input) #"\s+")
        num-stacks  (str-to-int (last indices))
        stack-input (drop-last state-input)]
    (parse-initial-state-rows stack-input (n-lists num-stacks))))

; move parsing
(defn parse-move [move]
  (let [words (string/split move #"\s+")
        [action count _ source _ target] words]
    {:action action
     :count  (str-to-int count)
     :source (to-0-based (str-to-int source))
     :target (to-0-based (str-to-int target))}))

(defn parse-moves [moves]
  (->> moves
       (map parse-move)))

(defn parse-input [input]
  ; split the input at the first empty line
  ; above this is the initial stack state
  ; below are the moves
  (let [state-and-moves (split-at (find-index string/blank? input) input)
        state-lines     (remove-blanks (first state-and-moves))
        move-lines      (remove-blanks (second state-and-moves))
        initial-state   (parse-initial-state state-lines)
        moves           (parse-moves move-lines)]
    {:initial-state initial-state
     :moves moves}))

(defn move-single-crate [stacks source target count]
  ; move 'count' crate from the source to the target, one at a time
  (if (= count 0)
    stacks
    (let [source-stack (nth stacks source)
          target-stack (nth stacks target)
          crate        (first source-stack)
          new-source   (rest source-stack)
          new-target   (concat (list crate) target-stack)
          updated-source (replace-item stacks source new-source)
          updated-target (replace-item updated-source target new-target)]
      (recur updated-target source target (dec count)))))

(defn move-all-crates [stacks source target count]
  ; move 'count' crates from the source to the target, all at once
  (let [source-stack (nth stacks source)
        target-stack (nth stacks target)
        crates       (take count source-stack)
        new-source   (drop count source-stack)
        new-target   (concat crates target-stack)
        updated-source (replace-item stacks source new-source)
        updated-target (replace-item updated-source target new-target)]
    updated-target))

(defn apply-move [move-fn stacks move]
  ; all indices are 1-based
  ; action is assumed to be "move"
  (let [count     (move :count)
        source    (move :source)
        target    (move :target)
        new-state (move-fn stacks source target count)]
    ; FIXME: we use doall to resolve the lazy sequence
    ; or we will cause a stack overflow! why?!
    (doall new-state)))

(defn apply-moves [move-fn state moves]
  (if (empty? moves)
    state
    (let [new-state       (apply-move move-fn state (first moves))
          remaining-moves (rest moves)]
      (recur move-fn new-state remaining-moves))))

(defn top-blocks [stacks]
  (let [blocks (map first stacks)
        str    (apply str blocks)]
    str))

(defn day5-1 []
  (let [input       (parse-input (input-data))
        state       (input :initial-state)
        moves       (input :moves)
        final-state (apply-moves move-single-crate state moves)
        message     (top-blocks final-state)]
    message))

(defn day5-2 []
  (let [input       (parse-input (input-data))
        state       (input :initial-state)
        moves       (input :moves)
        final-state (apply-moves move-all-crates state moves)
        message     (top-blocks final-state)]
    message))

(defn -main [& args]
  (println (day5-1))
  (println (day5-2)))
