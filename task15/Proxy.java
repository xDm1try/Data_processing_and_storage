package task15;

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Stream;

public class Proxy {
    public static void main(String[] args){
        int listenerPort;
        int outputPort;
        listenerPort = 1234;
        outputPort = 4321;
        // int listenerPort = Proxy.atoi(args[0]);
        // int outputPort = Proxy.atoi(args[1]);
        
        
        try (ServerSocket listener = new ServerSocket(listenerPort);
            Socket output = new Socket("127.0.0.1", outputPort)) {
            while(true){
                Socket client = listener.accept();
                output.getInputStream().transferTo(client.getOutputStream());
                client.getInputStream().transferTo(output.getOutputStream());
                
                System.out.println("client accepted");
            }
            
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        
    }
    public static int atoi(String str){
        int res = 0;
        for(int i = 0; i < str.length(); i++){
            res += res*10 + str.charAt(i) - '0';
        }
        return res;
    }
    
}
