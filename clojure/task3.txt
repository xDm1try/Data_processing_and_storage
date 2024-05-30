(ns task3.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn numbers [x] (take x (iterate inc 1)))
(println (numbers 5))

(defn heavy_mod [x y]
  (Thread/sleep 100)
  (not= 0 (mod x y)))

(defn parallelFilter [condition sequence part_count]
  (let [blocks (partition-all (int (/ (count sequence) part_count)) sequence)]
    (flatten
     (map deref
          (doall
           (map #(future (doall (filter condition %))) blocks))))))

(take 10 (parallelFilter #(not= 0 (mod % 7)) (numbers 25) 3))

(use 'clojure.test)
(deftest eg-tests
  (is (= (last (take 10 (parallelFilter #(not= 0 (mod % 2)) (numbers 25) 8))) 19))
  (is (= (last (take 10 (parallelFilter #(not= 0 (mod % 7)) (numbers 25) 8))) 11)))
(run-tests)

(defn test_funct1 []
  (let [start-time (System/currentTimeMillis)]
    (reduce + (take 10000 (parallelFilter #(not= 0 (heavy_mod % 2)) (numbers 25) 8)))
    (let [end-time (System/currentTimeMillis)]
      (println "Function executed in" (- end-time start-time) "milliseconds"))))
(defn test_funct2 []
  (let [start-time (System/currentTimeMillis)]
    (reduce + (take 10000 (filter #(not= 0 (heavy_mod % 2)) (numbers 25))))
    (let [end-time (System/currentTimeMillis)]
      (println "Function executed in" (- end-time start-time) "milliseconds"))))

(test_funct1)
(test_funct2)

