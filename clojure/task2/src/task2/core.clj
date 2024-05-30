(ns task2.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn sieve [s]
  (lazy-seq
   (cons (first s)
         (sieve (filter #(not= 0 (mod % (first s))) (rest s))))))

(def primes (sieve (iterate inc 2)))
(take 10 primes)

(nth primes 9)

(use 'clojure.test)
(deftest eg-tests
  (is (= (last (take 2 primes)) 3))
  (is (= (last (take 5 primes)) 11)))
  (is (= (last (take 8 primes)) 19))

(run-tests)