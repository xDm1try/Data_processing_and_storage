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
        
        Thread makerB = new Thread(new MakerB(personalSemaphore));
        Thread makerC = new Thread(new MakerC(personalSemaphore));
        makerB.start();
        makerC.start();
        try {
            makerBSemaphore.acquire();
            makerCSemaphore.acquire();
            this.makeStuff(500);
            System.out.println("Module is made");
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
    
}
