(ns aoc2022.day5-test
  (:require [clojure.test :refer :all]
            [aoc2022.day5 :as day5 :refer :all]
            [clojure.string :as string]))

(def day-5-initial-state-strings
  ["[N]             [R]             [C]"
   "[T] [J]         [S] [J]         [N]"
   "[B] [Z]     [H] [M] [Z]         [D]"
   "[S] [P]     [G] [L] [H] [Z]     [T]"
   "[Q] [D]     [F] [D] [V] [L] [S] [M]"
   "[H] [F] [V] [J] [C] [W] [P] [W] [L]"
   "[G] [S] [H] [Z] [Z] [T] [F] [V] [H]"
   "[R] [H] [Z] [M] [T] [M] [T] [Q] [W]"
   " 1   2   3   4   5   6   7   8   9 "])

(def day-5-initial-state
  '((\N \T \B \S \Q \H \G \R)
    (\J \Z \P \D \F \S \H)
    (\V \H \Z)
    (\H \G \F \J \Z \M)
    (\R \S \M \L \D \C \Z \T)
    (\J \Z \H \V \W \T \M)
    (\Z \L \P \F \T)
    (\S \W \V \Q)
    (\C \N \D \T \M \L \H \W)))

(deftest day5-functions
  (testing "find-index"
    (is (= (find-index (partial = 1) [1 2 3]) 0))
    (is (= (find-index (partial = 2) [1 2 3]) 1))
    (is (= (find-index (partial = 3) [1 2 3]) 2))
    (is (= (find-index (partial = "b") ["a" "b" "c"]) 1)))

  (testing "to-0-based"
    (is (= (to-0-based 1) 0))
    (is (= (to-0-based 2) 1)))

  (testing "replace-item"
    (is (= (replace-item '(1 2 3) 1 4) '(1 4 3))))

  (testing "n-lists"
    (is (= (n-lists 1) [[]]))
    (is (= (n-lists 2) [[] []])))

  (testing "parse-block-at"
    (is (= (parse-block-at "[a] [b] [c]" 0) \a))
    (is (= (parse-block-at "[a] [b] [c]" 1) \b))
    (is (= (parse-block-at "[a] [b] [c]" 2) \c))
    (is (= (parse-block-at "[a]     [c]" 1) nil)))

  (testing "parse-row"
    (is (= (parse-row
            "[a] [b] [c]"
            '(() () ()))
           '((\a) (\b) (\c))))
    (is (= (parse-row
            "[a]     [c]"
            '((\x) (\y) (\z)))
           '((\a \x) (\y) (\c \z)))))

  (testing "parse-initial-state-rows"
    (is (= (parse-initial-state-rows ["[d]     [e]"
                                      "[a] [b] [c]"]
                                     '(() () ()))
           '((\d \a) (\b) (\e \c))))
    (is (= (parse-initial-state-rows
            (drop-last day-5-initial-state-strings)
            (n-lists 9))
           day-5-initial-state)))

  (testing "parse-initial-state"
    (is (= (parse-initial-state ["[d]     [e]"
                                   "[a] [b] [c]"
                                   " 1   2   3"])
           '((\d \a) (\b) (\e \c))))
    (is (= (parse-initial-state day-5-initial-state-strings)
           day-5-initial-state)))

  (testing "parse-move"
    (is (= (parse-move "move 3 from 9 to 7")
           {:action "move"
            :count 3
            :source 8
            :target 6})))
  
  (testing "move-single-crate"
    (is (= (move-single-crate
            '((\b \a) (\e \d \c) (\g \f))
            1 2 0)
           '((\b \a) (\e \d \c) (\g \f))))
    (is (= (move-single-crate
            '((\b \a) (\e \d \c) (\g \f))
            1 2 1)
           '((\b \a) (\d \c) (\e \g \f))))
    (is (= (move-single-crate
            '((\b \a) (\e \d \c) (\g \f))
            1 2 2)
           '((\b \a) (\c) (\d \e \g \f))))
    (is (= (move-single-crate
            '((\b \a) (\e \d \c) (\g \f))
            1 2 3)
           '((\b \a) () (\c \d \e \g \f)))))

  (testing "apply-move"
    ; move 2 from 2nd stack to 3rd stack
    (is (= (apply-move move-single-crate
            '((\b \a) (\e \d \c) (\g \f))
            {:action "move"
             :count 2
             :source 1
             :target 2})
           '((\b \a) (\c) (\d \e \g \f)))))

  (testing "apply-moves"
    (is (= (apply-moves move-single-crate
            '((\b \a) (\e \d \c) (\g \f))
            [{:action "move"
             :count 2
             :source 1
             :target 2}
             {:action "move"
              :count 2
              :source 0
              :target 2}])
            '(() (\c) (\a \b \d \e \g \f)))))


  (testing "top-blocks"
    (is (= (top-blocks '((\a \b) (\c) (\d \e)))
           "acd")))

  (testing "parse-input"
    (is (= ((parse-input (input-data)) :initial-state)
           day-5-initial-state))))

(deftest day5
  (testing "Day 5 answers"
    (is (= (day5-1) "PTWLTDSJV"))
    (is (= (day5-2) "WZMFVGGZP"))))
