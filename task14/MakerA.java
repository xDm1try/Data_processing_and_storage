package task14;

import java.util.concurrent.Semaphore;

import task11.Semaph;

public class MakerA implements Runnable, Maker{
    private Semaphore personalSemaphore;

    public MakerA(Semaphore personalSemaphore){
        this.personalSemaphore = personalSemaphore;
    }

    public void run(){
        for(int i = 0; i < Widget.count; i++){
            this.makeStuff(1000);
            System.out.println("A is made");
            personalSemaphore.release();
        }
            
    }   
}
