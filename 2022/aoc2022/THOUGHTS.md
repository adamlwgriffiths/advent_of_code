* I haven't figured out the proper way of doing REPL driven development.
* The inability to put (println) anywhere due to the tree nature of the computation is a pain. Ie, the AST nature of lisp is too low level.
* println often breaks the computation, as it returns nil.
* Not permitting nested defn due to expansion issues (defn inside a defn) is ... just straight dumb. Instead you must do (let [x (fn [] ...)]). Yuck.
* Clojure warm-up time is bad. >=2 seconds.
* The lack of cons/car/cdr is bizarre.
* Nested structures (maps of maps) are annoying to work with without requiring functions (even when it's a simple task).
* Nested destructuring is something I constantly wanted but never had.
* Stack traces are mostly useless, eg:
```
    <1000s of lines of crap>
    ...
    clojure.core$apply.invokeStatic (core.clj:667)
    clojure.core$apply.invoke (core.clj:662)
    leiningen.core.injected$run_hooks.invokeStatic (form-init15044528798735351107.clj:1)
    leiningen.core.injected$run_hooks.invoke (form-init15044528798735351107.clj:1)
    leiningen.core.injected$prepare_for_hooks$fn__159$fn__160.doInvoke (form-init15044528798735351107.clj:1)
    clojure.lang.RestFn.applyTo (RestFn.java:137)
    clojure.lang.AFunction$1.doInvoke (AFunction.java:31)
    clojure.lang.RestFn.invoke (RestFn.java:408)
    clojure.test$do_report.invokeStatic (test.clj:357)
    clojure.test$do_report.invoke (test.clj:351)
    aoc2022.day5_test$fn__590$fn__591.invoke (day5_test.clj:139)
    aoc2022.day5_test$fn__590.invokeStatic (day5_test.clj:139)
    aoc2022.day5_test/fn (day5_test.clj:137)
    clojure.test$test_var$fn__9856.invoke (test.clj:717)
    clojure.test$test_var.invokeStatic (test.clj:717)
    ...
    <50 lines of crap>
```
* debugging in general is hard
* you can cause stack overflows by having large lazy lists that are evaluated later. This issue was found by performing a println while debugging this problem. Solution is to resolve the list from time to time? seriously?