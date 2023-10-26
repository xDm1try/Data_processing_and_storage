package task12;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class List {
    static public Node firstNode = null;
    static public Semaphore semaphore = new Semaphore(1);
    public Thread scannerThread = new Thread(new ScannerThread());
    public Thread sortThread = new Thread(){
        public void run(){
            while(true){
                try {
                    
                    Thread.currentThread().sleep(5000);
                    semaphore.acquire();
                    for(Node currentNode1 = firstNode; currentNode1 != null; currentNode1 = currentNode1.getNext()){
                        for(Node currentNode2 = firstNode.getNext(); currentNode2 != null; currentNode2 = currentNode2.getNext()){
                            if (currentNode2.getValue().compareTo(currentNode1.getValue()) == -1){
                                String spacing = currentNode2.getValue();
                                currentNode2.setValue(currentNode1.getValue());
                                currentNode1.setValue(spacing);
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
