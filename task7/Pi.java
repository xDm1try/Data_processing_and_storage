package task7;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Pi {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter count of threads and iterations");
        int countOfThreads = input.nextInt();
        int countOfIterations = input.nextInt();
        input.close();
        Double result = 0.0;
        ArrayList<FutureTask<Double>> tasks = new ArrayList<>(countOfThreads);
        for (int i = 0; i < countOfThreads; i++) {
            tasks.add(i, new FutureTask<>(new ComputingTask(i, countOfThreads, countOfIterations)));
            new Thread(tasks.get(i)).start();
        }
        
        for (FutureTask<Double> futureTasks : tasks) {
            try{
                result += futureTasks.get();
            }catch (InterruptedException exception){
                System.out.println("Main is interrupted");
            }catch (ExecutionException exception){
                System.out.println("Execution exception");
            }
            
        }
        System.out.println("RESULT = " + result * 4);
    }

    private static class ComputingTask implements Callable<Double> {
        private Double result = .0;
        private final int number;
        private final int countOfThreads;
        private final int countOfIterations;
        
        public ComputingTask(int number, int countOfThreads, int countOfIterations){
            this.number = number+1;
            this.countOfThreads = countOfThreads;
            this.countOfIterations = countOfIterations;
        }

        @Override
        public Double call() {
            try {
                int n = number;
                double numerator = 1;
                
                for(int i = 0; i < countOfIterations; i++){
                    if(n % 2 == 0){
                        numerator = -1;
                    }else{
                        numerator = 1;
                    }
                    this.result += numerator / (2 * n - 1);
                    
                    System.out.println("Res = " + numerator + " / " + (2 * n - 1) + " RESULT = " + this.result);
                    
                    Thread.sleep(10);
                    n += countOfThreads;
                }
            } catch (InterruptedException exception) {
                System.out.println(Thread.currentThread().getName() + " is interrupted");
            }
            return this.result;
        }
    }
}
