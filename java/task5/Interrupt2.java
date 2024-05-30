package task5;

public class Interrupt2 {
    public static void main(String[] args) {
        Thread daughterThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(System.currentTimeMillis());
            }
            System.out.println(Thread.currentThread().getName() + " is interrupted");
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
