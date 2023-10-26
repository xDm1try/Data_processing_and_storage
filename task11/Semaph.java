package task11;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Semaph {
    public static AtomicBoolean turn = new AtomicBoolean(false);
    public static Writer wrt = new Writer();

    public static void main(String[] arg) {
        Thread[] threads = new Thread[2];
        Semaphore sem1 = new Semaphore(1);
        Semaphore sem2 = new Semaphore(1);
        Writer writer = new Writer();
        ThreadsBehavior behavior1 = new ThreadsBehavior(writer, sem1, sem2);
        ThreadsBehavior behavior2 = new ThreadsBehavior(writer, sem1, sem2);
        threads[0] = new Thread(behavior1, "SECOND");
        threads[0].setDaemon(true);
        threads[1] = new Thread(behavior2, "FIRST");
        threads[1].setDaemon(true);
        

        for (Thread thread : threads) {
            thread.start();
        }
        try {
            Thread.currentThread().sleep(200000);
        } catch (InterruptedException e) {

        }

    }
}
