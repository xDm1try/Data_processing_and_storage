
class FirstTask {
    public static void main(String[] args){
        Runnable print = () -> {
           
                for(int i = 0; i < 10; i++){
                    System.out.println(Thread.currentThread().getName() + " print " + i);
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException e){
                        System.out.println(e);
                    }
                }
            
        };
        Thread secondThread = new Thread(print, "second");
        secondThread.start();
        for(int i = 0; i < 10; i++){
            System.out.println(Thread.currentThread().getName() + "print " + i);
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
    }
}