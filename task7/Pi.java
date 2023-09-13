package task7;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class Pi {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int countOfThreads = input.nextInt();
        int countOfIterations = input.nextInt();
        input.close();
        Double result = .0;
        ArrayList<ComputingTask> tasks = new ArrayList<>(tasks);
        for (int i = 0; i < countOfThreads; i++) {
            tasks.add(i, new Thread(, "Thread[" + i + "]"));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    private class ComputingTask implements Callable<Double> {
        private Double result = .0;
        private final int number;
        private final int countOfThreads;
        private final int countOfIterations;
        
        public ComputingTask(int number, int countOfThreads, int countOfIterations){
            this.number = number;
            this.countOfThreads = countOfThreads;
            this.countOfIterations = countOfIterations;
        }

        @Override
        public Double call() {
            try {
                for(int i = 0; i < countOfIterations; i++){
                    int n = number + countOfThreads * i;
                    result += 1/ (n % 2 == 0 ? n * (-2) - 1 : 2 * n -1);
                    Thread.sleep(250);
                }
            } catch (InterruptedException exception) {
                System.out.println(Thread.currentThread().getName() + " is interrupted");
            }
            return result;
        }
    }
}
