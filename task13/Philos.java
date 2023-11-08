package task13;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;

public class Philos {
    private Fork leftFork;
    private Fork rightFork;
    public Thread t;
    public static Semaphore semaphore1 = new Semaphore(9, true);

    public Philos(Fork leftFork, Fork rightFork, String name) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        t = new Thread(new PhilosBehavour(leftFork, rightFork), name);
    }

    public static void main(String[] args) {
        ArrayList<Philos> philos = new ArrayList<>(5);
        Fork firstFork = new Fork("Fork 0");
        Fork nextFork = firstFork;
        for (int i = 0; i < 5; i++) {
            Fork ownFork = nextFork;
            if (i == 4) {
                nextFork = firstFork;
            } else {
                nextFork = new Fork("Fork " + (i + 1));
            }
            philos.add(i, new Philos(nextFork, ownFork, "Phil " + i));
        }
        for (Philos phil : philos) {
            phil.t.start();
        }
    }

    private class PhilosBehavour implements Runnable {
        private Fork leftFork;
        private Fork rightFork;

        public PhilosBehavour(Fork leftFork, Fork rightFork) {
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        public void run() {
            Random rand = new Random(System.nanoTime());
            while (true) {
                try {
                    Thread.currentThread().sleep(rand.nextInt(250));
                    semaphore1.acquire(1);
                    this.leftFork.takeFork();
                    Thread.currentThread().sleep(rand.nextInt(250));
                    if(!this.rightFork.isLocked()){
                        this.rightFork.takeFork();
                    }else{
                        this.leftFork.dropFork();
                        Condition leftCondition = this.leftFork.lock.newCondition();
                        Condition rightCondition = this.rightFork.lock.newCondition();

                        while(!rightFork.lock.tryLock() || !leftFork.lock.tryLock()){
                            rightCondition.await();
                            leftCondition.await();
                        }
                        
                    }
                    Thread.currentThread().sleep(rand.nextInt(250));

                    this.leftFork.dropFork();
                    this.rightFork.dropFork();

                    semaphore1.release();

                } catch (InterruptedException e) {
                }
            }
        }
    }
}
