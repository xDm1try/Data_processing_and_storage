package task16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) throws IOException {
        URI uri = URI.create("https://openjdk.org/groups/net/httpclient/intro.html");
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

        connection.setRequestMethod("GET"); 
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try(BufferedReader inHttp = new BufferedReader(new InputStreamReader( connection.getInputStream()));
                Scanner inputKeyboard = new Scanner(System.in)){ 
                int counter = 0;
                for(String line = inHttp.readLine(); line != null; counter++, line = inHttp.readLine()){
                    System.out.println(line);
                    if (counter == 25){
                        System.out.println("Press SPACE to continue");
                        counter = 0;
                        String input = inputKeyboard.nextLine();
                        while(!input.equals(" "))
                            input = inputKeyboard.nextLine();
                        
                    }
                }
            }
        } else {
            System.out.println("HTTP request failed with response code: " + responseCode);
        }

        connection.disconnect();
    }
}

