package task8;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class SIGINT {
    static ArrayList<Thread> threads;
    static ArrayList<FutureTask<Double>> tasks;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter count of threads");
        int countOfThreads = input.nextInt();
        input.close();

        threads = new ArrayList<>(countOfThreads);

        Double result = 0.0;
        tasks = new ArrayList<>(countOfThreads);
        for (int i = 0; i < countOfThreads; i++) {
            tasks.add(i, new FutureTask<>(new ComputingTask(i, countOfThreads)));
            threads.add(i, new Thread(tasks.get(i)));
            threads.get(i).start();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (Thread thread : threads) {
                thread.interrupt();
            }
            double res = 0;
            for (FutureTask<Double> futureTasks : tasks) {
                try {
                    res += futureTasks.get();
                } catch (InterruptedException exception) {
                    System.out.println("HANDLER is interrupted");
                } catch (ExecutionException exception) {
                    System.out.println("Execution exception");
                }
            }
            System.out.println(res);
        }));

        for (FutureTask<Double> futureTasks : tasks) {
            try {
                result += futureTasks.get();
            } catch (InterruptedException exception) {
                System.out.println("Main is interrupted");
            } catch (ExecutionException exception) {
                System.out.println("Execution exception");
            }

        }
        System.out.println("RESULT = " + result * 4);
    }

    private static class ComputingTask implements Callable<Double> {
        public Double result = .0;
        private final int number;
        private final int countOfThreads;

        public ComputingTask(int number, int countOfThreads) {
            this.number = number + 1;
            this.countOfThreads = countOfThreads;
        }

        @Override
        public Double call() {
            try {
                int n = number;
                double numerator = 1;

                for (int i = 0; true; i++) {
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

            }
            return this.result;

        }
    }
}
