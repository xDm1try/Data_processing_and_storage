package task14;

import java.util.concurrent.Semaphore;

public class MakerC implements Runnable, Maker{
    private Semaphore personalSemaphore;
    public MakerC(Semaphore personalSemaphore){
        this.personalSemaphore = personalSemaphore;
    }

    public void run(){
        for(int i = 0; i < Widget.count; i++){
            this.makeStuff(2000);
            personalSemaphore.release();
            System.out.println("C is made");
        }

    }
    
}
