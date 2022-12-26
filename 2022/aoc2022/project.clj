(defproject aoc2022 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://www.github.com/adamlwgriffiths/adventofcode"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 ;[org.clojure/math.combinatorics "0.1.6"]
                 [org.clojure/spec.alpha "0.3.218"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :day1 {:main ^:skip-aot aoc2022.day1}
             :day2 {:main ^:skip-aot aoc2022.day2}
             :day3 {:main ^:skip-aot aoc2022.day3}
             :day4 {:main ^:skip-aot aoc2022.day4}
             :day5 {:main ^:skip-aot aoc2022.day5}}
  :aliases {"day1" ["with-profile" "day1" "run"]
            "day2" ["with-profile" "day2" "run"]
            "day3" ["with-profile" "day3" "run"]
            "day4" ["with-profile" "day4" "run"]
            "day5" ["with-profile" "day5" "run"]})