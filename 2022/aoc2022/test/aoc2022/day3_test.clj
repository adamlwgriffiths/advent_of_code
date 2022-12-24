(ns aoc2022.day3-test
  (:require [clojure.test :refer :all]
            [aoc2022.day3 :as day3 :refer :all]))

(deftest day3
  (testing "Day 3 functions"
    (is (= (compartments "abcdef") '("abc" "def")))

    (is (= (priority \a) 1))
    (is (= (priority \z) 26))
    (is (= (priority \A) 27))
    (is (= (priority \Z) 52))

    (is (= (common-char ["abc" "cde"]) \c)))
    (is (= (common-char ["abc" "cde" "cfg"]) \c))

  (testing "Day 3 answers"
    (is (= (day3-1) 8202))
    (is (= (day3-2) 2864))))
