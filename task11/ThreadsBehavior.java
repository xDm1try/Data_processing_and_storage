package task11;

import java.util.concurrent.Semaphore;


public class ThreadsBehavior implements Runnable {
        private Semaphore semaphore1;
        private Writer writer;

        public ThreadsBehavior(Writer writer, Semaphore semaphore1){
            this.semaphore1 = semaphore1;
            this.writer = writer;
        }

        public void run() {
            while(true){
                try{
                semaphore1.acquire();

                synchronized(writer){
                    writer.printStr();
                }
                    Thread.currentThread().sleep(250);
                }catch(InterruptedException e){

                }finally{
                    semaphore1.release();
                }
                
            }
        }
    }