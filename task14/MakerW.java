package task14;

import java.util.concurrent.Semaphore;

public class MakerW implements Runnable, Maker{
    private Semaphore personalSemaphore;

    public MakerW(){
    }

    public void run(){
        Semaphore makerASemaphore = new Semaphore(0);
        Semaphore makerMSemaphore = new Semaphore(0);
        Thread makerA = new Thread(new MakerA(makerASemaphore));
        Thread makerM = new Thread(new MakerM(makerMSemaphore));
        makerA.start();
        makerM.start();
        while(true){
            try{
            makerASemaphore.acquire();
            makerMSemaphore.acquire();
            this.makeStuff(500);
            System.out.println("Widget is made");
            }catch(InterruptedException e){
            }
        }
    }
    
}
