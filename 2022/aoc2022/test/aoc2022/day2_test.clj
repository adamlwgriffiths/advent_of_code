(ns aoc2022.day2-test
  (:require [clojure.test :refer :all]
            [aoc2022.day2 :as day2 :refer :all]))

(deftest day2
  (testing "Day 2 answers"
    (is (= (day2-1) 11386))
    (is (= (day2-2) 13600))))
