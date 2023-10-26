package task11;

import java.util.concurrent.Semaphore;


public class ThreadsBehavior implements Runnable {
        private Semaphore semaphore1;
        private Semaphore semaphore2;
        private Writer writer;

        public ThreadsBehavior(Writer writer, Semaphore semaphore1, Semaphore semaphore2){
            this.semaphore1 = semaphore1;
            this.semaphore2= semaphore2;
            this.writer = writer;
        }

        public void run() {
            while(true){
                try{
                semaphore1.acquire();
                semaphore2.acquire();
                semaphore1.release();

                synchronized(writer){
                    writer.printStr();
                }
                    Thread.currentThread().sleep(250);
                }catch(InterruptedException e){

                }finally{
                    semaphore2.release();
                }
                
            }
        }
    }