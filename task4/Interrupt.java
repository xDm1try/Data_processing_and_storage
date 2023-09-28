package task4;

public class Interrupt {
    public static void main(String[] args) {
        Thread daughterThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(System.currentTimeMillis());
            }
        });
        daughterThread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println("Main interrupted");
        }
        daughterThread.interrupt();
    }
}
