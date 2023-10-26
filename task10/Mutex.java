package task10;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Mutex {
    public static AtomicBoolean turn = new AtomicBoolean(false);
    public static Writer wrt = new Writer();

    public static void main(String[] arg) {
        Thread[] threads = new Thread[2];
        ReentrantLock locker1 = new ReentrantLock();
        ReentrantLock locker2 = new ReentrantLock();
        Writer writer = new Writer();
        ThreadsBehavior behavior1 = new ThreadsBehavior(writer, locker1, locker2);
        ThreadsBehavior behavior2 = new ThreadsBehavior(writer, locker1, locker2);
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
