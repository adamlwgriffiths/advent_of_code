# Advent of Code 2022 - Clojure

## Usage

Run a day:

```
lein day<number>

lein day1
```

Run tests

```
lein test

# run tests from day 1
lein test :only aoc2022.day1-test

# run a single test from day5
lein test :only aoc2022.day5-test/day5-functions
```

## Where are things?

* /resources - input data
* /src - day by day code
* /test - unit / regression tests
* /project.clj - clojure libraries and lein aliases
