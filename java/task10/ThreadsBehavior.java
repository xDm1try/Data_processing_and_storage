package task10;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadsBehavior implements Runnable {
        private ReentrantLock locker1;
        private ReentrantLock locker2;
        private Writer writer;

        public ThreadsBehavior(Writer writer, ReentrantLock locker1, ReentrantLock locker2){
            this.locker1 = locker1;
            this.locker2= locker2;
            this.writer = writer;
        }

        public void run() {
            while(true){
                locker1.lock();
                locker2.lock();
                locker1.unlock();

                synchronized(writer){
                    writer.printStr();
                }
                try{
                    Thread.currentThread().sleep(250);
                }catch(InterruptedException e){

                }
                locker2.unlock();
            }
        }
    }