(ns aoc2022.day4-test
  (:require [clojure.test :refer :all]
            [aoc2022.day4 :as day4 :refer :all]))

(deftest day4
  (testing "Day 4 answers"
    (is (= (day4-1) 453))
    (is (= (day4-2) 919))))
