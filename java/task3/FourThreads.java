package task3;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FourThreads {

	public static void main(String[] args) {
		int countOfStrings = 100;
		ArrayList<String> strings = new ArrayList<>(countOfStrings);
		for (int i = 0; i < countOfStrings; i++) {
			strings.add(Integer.toString(i));
		}
		Task task = new Task(strings);
		Thread[] threads = new Thread[4];
		for (int i = 0; i < 4; i++) {
			threads[i] = new Thread(task, "Thread[" + i + "]");
			threads[i].start();
		}
	}

	private static class Task implements Runnable {
		BlockingQueue<String> sequence = new LinkedBlockingQueue<>();

		public Task(ArrayList<String> strings) {
			for (int i = 0; i < strings.size(); i++) {
				this.sequence.add(strings.get(i));
			}
		}

		@Override
		public void run() {
			while (!sequence.isEmpty()) {
				try {
					System.out.println(Thread.currentThread().getName() + " is printing " + sequence.take());
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			}
		}

	}

}
