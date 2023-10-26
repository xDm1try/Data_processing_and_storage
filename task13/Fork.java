package task13;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Renderer;

public class Fork {
    public ReentrantLock lock;
    private String nameOfFork;
    private String nameOfOwner;
    private Condition condition;
    public Fork(String nameOfFork){
        this.lock = new ReentrantLock();
        this.nameOfFork = nameOfFork;
    }
    public void takeFork(){
        try{
            while(true){
                if(!lock.isLocked()){
                    System.out.println(Thread.currentThread().getName() + " is taking " + this.nameOfFork);
                    this.nameOfOwner = Thread.currentThread().getName();
                    lock.lock();
                    break;
                }else{
                    System.out.println(Thread.currentThread().getName() + " try " + this.nameOfFork);
                    Thread.currentThread().sleep(250);
                }
            }
            
        }catch(InterruptedException e){
        }
    }
    public void dropFork(){
        if (lock.isHeldByCurrentThread()){
            System.out.println(Thread.currentThread().getName() + " is dropping " + this.nameOfFork);
            System.out.flush();
            this.nameOfOwner = null;
            lock.unlock();
            
        }
    }
}
