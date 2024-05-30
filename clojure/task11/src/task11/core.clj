(defn generate-strings [chars n]
  (reduce
   (fn [acc _]
     (for [seq acc c chars :when (not= c (last seq))]
       (str seq c)))
   chars
   (repeat (dec n) nil)))

(defn no-repeats? [s]
  (not-any? (fn [[x y]] (= x y)) (partition 2 1 s)))

(def my-alphabet ["a" "b" "c"])
(def n 3)
;(def result (filter no-repeats? (generate-strings my-alphabet n)))
;(println result)