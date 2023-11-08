package task13;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Philos {
    private Fork leftFork;
    private Fork rightFork;
    public Thread t;
    public static ReentrantLock forks = new ReentrantLock(true);
    public static Condition condition = forks.newCondition();


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
                forks.lock();
                try {
                    Thread.currentThread().sleep(rand.nextInt(250));
                    while(!this.leftFork.takeFork() || !this.rightFork.takeFork()){
                        leftFork.dropFork();
                        rightFork.dropFork();
                        condition.await();
                    }
                    forks.unlock();
                    Thread.currentThread().sleep(rand.nextInt(250));

                    forks.lock();
                    this.leftFork.dropFork();
                    this.rightFork.dropFork();
                    condition.signalAll();
                    forks.unlock();
                } catch (InterruptedException e) {
                }
            }
        }
    }
}