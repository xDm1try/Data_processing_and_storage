package task8;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;

public class PiWithInterruption {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int countOfThreads = input.nextInt();
        input.close();
        ArrayList<FutureTask> tasks = new ArrayList<>(countOfThreads);
        for(int i = 0; i < countOfThreads; i++){
            tasks.add(i, new FutureTask<>(new ComputingTask(i, countOfThreads)));
            
        }

    }

    private static class ComputingTask implements Callable<Double> {
        private Double result = .0;
        private final int number;
        private final int countOfThreads;
        private final CyclicBarrier barrier;

        public ComputingTask(int number, int countOfThreads) {
            this.number = number + 1;
            this.countOfThreads = countOfThreads;
            this.barrier = new CyclicBarrier(countOfThreads);
        }

        @Override
        public Double call() {
            try {
                int n = number;
                double numerator = 1;
                int i = 0;

                while (barrier.await() == 0) {
                    System.out.println("ITERATION = " + i);
                    i++;
                    if (n % 2 == 0) {
                        numerator = -1;
                    } else {
                        numerator = 1;
                    }
                    this.result += numerator / (2 * n - 1);

                    System.out.println("Res = " + numerator + " / " + (2 * n - 1) + " RESULT = " + this.result);
                    Thread.sleep(10);
                    n += countOfThreads;
                }

            } catch (InterruptedException exception) {
                System.out.println(Thread.currentThread().getName() + " is interrupted");

            } catch (BrokenBarrierException e) {
                System.out.println("BARIER is broken");
                e.printStackTrace();
            }
            return this.result;
        }
    }
}
