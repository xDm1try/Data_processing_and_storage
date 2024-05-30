package task2;

class Join {
  public static void main(String[] args) {
    Thread second = new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        System.out.println(Thread.currentThread().getName() + " is printing");
      }
    }, "second");
    second.start();
    try {
      second.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    for (int i = 0; i < 10; i++) {
      System.out.println(Thread.currentThread().getName() + " is printing");
    }
  }

}