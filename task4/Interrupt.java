package task4;

public class Interrupt {
    public static void main(String[] args) {
        Thread daughterThread = new Thread(() -> {
            try {
                while (true) {
                    System.out.println(System.currentTimeMillis());
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                return;
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
