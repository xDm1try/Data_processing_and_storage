package task12;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class List {
    static public volatile Node firstNode = null;
    static public Semaphore semaphore = new Semaphore(1, true);
    public Thread scannerThread = new Thread(new ScannerThread());
    public Thread sortThread = new Thread(){
        public void run(){
            while(true){
                try {

                    Thread.currentThread().sleep(5500);
                    semaphore.acquire();
                    
                    for (Node idx_i = firstNode; idx_i != null; idx_i = idx_i.getNext()) {
                        for (Node idx_j = idx_i.getNext(); idx_j != null; idx_j = idx_j.getNext()) {
                            if(idx_i.getValue().compareTo(idx_j.getValue()) > 0){
                                String spacing = idx_i.getValue();
                                idx_i.setValue(idx_j.getValue());
                                idx_j.setValue(spacing);
                            }
                        }
                    }
                    semaphore.release();
                } catch (Exception e) {
                    
                }
            }

        }
    };
    public class ScannerThread implements Runnable{
        
        public void run(){
            Scanner input = new Scanner(System.in);
            while(true){
                try{
                    String newString = input.nextLine();
                    semaphore.acquire();
                if (newString.equals("=")){
                    Node nextNode = firstNode;
                    while(nextNode != null){
                        System.out.println(nextNode.getValue());
                        nextNode = nextNode.getNext();
                    }
                }else{
                    Node newNode = new Node(newString, firstNode);
                    firstNode = newNode;
                }
                semaphore.release();
                }catch(InterruptedException e){
                }
            }
        }
    }

    public static void main(String[] args) {
    List list = new List();
    list.sortThread.start();
    list.scannerThread.start();

    }
    private class Node{
        private Node next;
        private String value;
        public Node(String value, Node nextNode){
            this.value = value;
            this.next = nextNode;
        }
        public Node(String value){
            this.value = value;
            this.next = null;
        }
        public String getValue(){
            return this.value;
        }
        public void setValue(String newString){
            this.value = newString;
        }
        public Node getNext(){
            return this.next;
        }
    }
}
