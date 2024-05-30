package task16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class Client {
    public static void main(String[] args) throws IOException {
        URI uri = URI.create("https://openjdk.org/groups/net/httpclient/intro.html");
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(26, true);
        Thread printer = new Thread(){
            public void run(){
                System.out.println("Printer start");
                while(true){
                    try{
                        System.out.println(queue.take());
                    }catch(InterruptedException e){
                        
                    }
                }
            }
        };
        printer.setDaemon(true);
        printer.start();

        connection.setRequestMethod("GET"); 
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try(BufferedReader inHttp = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                Scanner inputKeyboard = new Scanner(System.in)){ 
                int counter = 0;
                for(String line = inHttp.readLine(); line != null; counter++, line = inHttp.readLine()){
                    try{
                        queue.put(line);
                        if (counter == 25){
                            queue.put("Press SPACE to continue");
                            counter = 0;
                            String input = inputKeyboard.nextLine();
                            while(!input.equals(" "))
                                input = inputKeyboard.nextLine();
                    }
                    }catch(InterruptedException e){
                        System.out.println(e);
                    }
                }
            }
        } else {
            System.out.println("HTTP request failed with response code: " + responseCode);
        }
        // 11◄
        // 12☻
        // 13◙
        // 16♫

        connection.disconnect();
    }
}

