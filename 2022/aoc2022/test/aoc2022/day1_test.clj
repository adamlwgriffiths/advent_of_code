(ns aoc2022.day1-test
  (:require [clojure.test :refer :all]
            [aoc2022.day1 :as day1 :refer :all]))

(deftest day1
  (testing "Day 1 answers"
    (is (= (day1-1) 71502))
    (is (= (day1-2) 208191))))
