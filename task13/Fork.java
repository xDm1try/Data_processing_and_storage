package task13;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class Fork {
    public ReentrantLock lock;
    private String nameOfFork;
    private String nameOfOwner;
    public Condition condition;
    public Fork(String nameOfFork){
        this.lock = new ReentrantLock(true);
        this.nameOfFork = nameOfFork;
        this.condition = lock.newCondition();
    }
    public boolean takeFork(){
        if(lock.isHeldByCurrentThread()){
            return true;
        }
        if(!lock.isLocked()){
            System.out.println(Thread.currentThread().getName() + " is taking " + this.nameOfFork);
            this.nameOfOwner = Thread.currentThread().getName();
            lock.lock();
            return true;
        }
        System.out.println(Thread.currentThread().getName() + " didnt take " + this.nameOfFork);
        return false;
    }
    public void dropFork(){
        if (lock.isHeldByCurrentThread()){
            System.out.println(Thread.currentThread().getName() + " is dropping " + this.nameOfFork);
            this.nameOfOwner = null;
            condition.signalAll();
            lock.unlock();
        }
    }
}
