package task5;

public class Interrupt2 {
    public static void main(String[] args) {
        Thread daughterThread = new Thread(() -> {
            try {
                while (true) {
                    System.out.println(System.currentTimeMillis());
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted");
                return;
            }
        }, "daughterThread");
        daughterThread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println("Main interrupted");
        }
        System.out.println(Thread.currentThread().getName() + " is interrupting");
        daughterThread.interrupt();
    }
}
