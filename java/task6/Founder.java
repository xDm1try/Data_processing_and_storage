import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public final class Founder {
    
    private final List<Runnable> workers;
    private static CyclicBarrier barrier;

    public Founder(final Company company) {
        this.workers = new ArrayList<>(company.getDepartmentsCount());
        this.barrier = new CyclicBarrier(company.getDepartmentsCount(), new Runnable (){
            public void run(){
                System.out.println(Thread.currentThread());
                company.showCollaborativeResult();    
            }
        });
        for(int i = 0; i < company.getDepartmentsCount(); i++){
            this.workers.add(i, new Worker(company.getFreeDepartment(i)));
        }
    }

    public void start() {
        for (final Runnable worker : workers) {
            new Thread(worker).start();
        }
    }
    public static void main(String[] args){
        System.out.println(Thread.currentThread());
        Company company = new Company(50);
        Founder founder = new Founder(company);
        founder.start();      
        
        
    }

    private class Worker implements Runnable{
        private Department department;

        public Worker(Department department){
            this.department = department;
        }

        public void run(){
            
            this.department.performCalculations();
            try{
                barrier.await();
            }catch(BrokenBarrierException e){
                
            }catch(InterruptedException e){
                
            }
            
        }
    }
}