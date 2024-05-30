(ns task1.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn fact [n]
  (if (> n 1)
    (reduce * (range 2
                     (inc n)))
    1))
(defn iter [N str taken i answ]
  
  )

(defn substr [n str]
  (iter (dec n) str 0 0 []))

(subs "abcd" 1 3)

(defn generate-strings [alphabet n]
  (let [permutations (apply concat (repeat n alphabet))]
    (->> permutations
         (partition n 1)
         (filter (fn [s] (not-any? (fn [[x y]] (= x y)) s)))
         (map (partial apply str)))))

(def my-alphabet [1 2 3 4 5])
(def result (generate-strings my-alphabet 3)) 
(println result)


(repeat 3 "abc")
(partition 3  3 (repeat 7 "abc"))


(partition 4 2 (range 20))