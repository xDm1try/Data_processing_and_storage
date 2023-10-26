import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public final class Founder {
    
    private final List<Runnable> workers;
    private static CountDownLatch latch;

    public Founder(final Company company) {
        this.workers = new ArrayList<>(company.getDepartmentsCount());
        this.latch = new CountDownLatch(company.getDepartmentsCount());
        for(int i = 0; i < company.getDepartmentsCount(); i++){
            this.workers.add(i, new Worker(company.getFreeDepartment(i)));
        }
    }

    public void start() {
        for (final Runnable worker : workers) {
            new Thread(worker).start();
        }
        System.out.println("WAITING");
        try{
            latch.await();
        }catch(InterruptedException ex){
            System.out.println("MAIN INTERRUPTED");
        }
        
        System.out.println("READY");
    }
    public static void main(String[] args){
        Company company = new Company(50);
        Founder founder = new Founder(company);
        founder.start();      
        company.showCollaborativeResult();
        
    }

    private class Worker implements Runnable{
        private Department department;

        public Worker(Department department){
            this.department = department;
        }

        public void run(){
            
            this.department.performCalculations();
            latch.countDown();
        }
    }
}