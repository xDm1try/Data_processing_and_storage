(ns task4.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(defn my_while [pred action]
  (when (pred)
    (action)
    (recur pred action)))

(defn phil [number forks_set n_times]
  (let [fork1 (nth forks_set number)
        fork2 (nth forks_set (mod (inc number) (count forks_set)))
        counter (atom 0)]
    (my_while
     #(<= @counter n_times)
     (fn []
       (dosync
        (locking *out*
          (println number " is thinking"))
        (Thread/sleep (rand-int 100))
        (alter fork1 inc)
        (locking *out*
          (println number " took fork1 " fork1))
        (Thread/sleep (rand-int 100))
        (alter fork2 inc)
        (locking *out*
          (println number " is eating" fork1 " " fork2))
        (alter fork1 dec)
        (alter fork2 dec)
        (Thread/sleep (rand-int 100))
        (swap! counter inc))))))

(defn philos [count n_times]
  (let [forks_set (map #(ref %) (repeat count 0))
        philosofs (map #(future (phil % forks_set n_times)) (range count))]
    (map deref philosofs)))

(philos 3 5)
