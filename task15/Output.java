package task15;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Output {
    public static void main(String[] args) {
        ArrayList<Socket> clients = new ArrayList<>();
        BlockingQueue<String> messages = new ArrayBlockingQueue<>(50);
        Thread writer = new Thread("WRITER"){
            public void run(){
                while(true){
                    try {
                        System.out.println(messages.poll());
                    } catch (Exception e) {
                    }
                }
            }
        };

        try(ServerSocket listener = new ServerSocket(35701)){
            while(true){
                Socket client = listener.accept();
                System.in.transferTo(client.getOutputStream());
            }
        }catch(Exception e){

        }

    }
    
}
