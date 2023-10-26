package task11;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Writer {

    public void printStr() {
        System.out.println(Thread.currentThread().getName());
        
    }

}