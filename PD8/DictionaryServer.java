import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class DictionaryServer {
    private static Map<String, String> dictionary = new HashMap<String, String>();
    public static void main(String[] args) throws IOException {
        // Listen on a server socket for a connection
        System.out.println("waiting for someone to connect");
        ServerSocket listen = new ServerSocket(4242);
        // When someone connects, create a specific socket for them
        Socket sock = listen.accept(); // wait for connection. "Blocks" at this point
        System.out.println("someone connected.");

        // Now talk with them
        PrintWriter out = new PrintWriter(sock.getOutputStream(), true); // Can send messages to client as if printing to console
        BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream())); // Can read what client sends us with BufferedRead
        out.println("Enter GET [a word] to get definition. Enter SET [a word] [a definition... to set a definition for word."); // Sends message to client

        String line;
        while ((line = in.readLine()) != null) {// Read from client line by line until null -- they hang up. Sits and waits until client sends something
            System.out.println(line);
            String[] splitInput = line.split(" ");
            System.out.println(splitInput);
            System.out.println("test in first else");
            String action = splitInput[0];
            String word = splitInput[1];
            if(action.equals("GET")){
                if(dictionary.containsKey(word)){
                    out.println("Definition: " + dictionary.get(word));
                }
                else {
                    out.println("Word not in dictionary.");
                }
            }
            else if(action.equals("SET")) {
                dictionary.put(word, line.substring(2 + action.length() + word.length()));
                out.println("Set word.");
            }
            else{
                out.println("Please enter valid format.");
            }
            System.out.println("ran");
        }
        System.out.println("client hung up");

        // Clean up shop
        out.close();
        in.close();
        sock.close();
        listen.close(); // End with closing the main socket on server
    }
}
