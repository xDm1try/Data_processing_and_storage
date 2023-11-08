package task14;

import java.util.concurrent.Semaphore;

public class MakerM implements Runnable, Maker{
    private Semaphore personalSemaphore;
    public MakerM(Semaphore personalSemaphore){
        this.personalSemaphore = personalSemaphore;
    }

    public void run(){
        Semaphore makerBSemaphore = new Semaphore(0);
        Semaphore makerCSemaphore = new Semaphore(0);
        
        Thread makerB = new Thread(new MakerB(makerBSemaphore));
        Thread makerC = new Thread(new MakerC(makerCSemaphore));
        makerB.start();
        makerC.start();
        for(int i = 0; i < Widget.count; i++){
            try {
            makerBSemaphore.acquire();
            makerCSemaphore.acquire();
            System.out.println("Module is made");
            personalSemaphore.release();
            } catch (Exception e) {
            }
        }

    }
    
}
