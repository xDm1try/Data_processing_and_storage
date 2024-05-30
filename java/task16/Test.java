package task16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class Test {

    public static void main(String[] args) throws IOException {
        URI uri = URI.create("https://openjdk.org/groups/net/httpclient/intro.html");
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

        connection.setRequestMethod("GET"); 
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            int linesCount = 0;
            boolean shouldScroll = false;

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                linesCount++;

                if (linesCount >= 25) {
                    shouldScroll = true;
                    break;
                }
            }

            if (shouldScroll) {
                System.out.print("Press space to scroll down...");
                while (System.in.read() != ' ') {
                    // Wait for space key to be pressed
                }
                System.out.println();
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                }
            }

            in.close();
        } else {
            System.out.println("HTTP request failed with response code: " + responseCode);
        }

        connection.disconnect();
    }
}

