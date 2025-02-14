import java.net.*;
import java.io.*;
 
/**
 * Simple server that waits for someone to connect on port 4242,
 * and then repeatedly asks for their name and greets them.
 * Connect either by "telnet localhost 4242" or by running HelloClient.java
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Tim Pierson, Dartmouth CS 10, provided for Winter 2024
 */

public class HelloServer {
	public static void main(String[] args) throws IOException {
		// Listen on a server socket for a connection
		System.out.println("waiting for someone to connect");
		ServerSocket listen = new ServerSocket(4242);
		// When someone connects, create a specific socket for them
		Socket sock = listen.accept(); // wait for connection. "Blocks" at this point
		System.out.println("someone connected");

		// Now talk with them
		PrintWriter out = new PrintWriter(sock.getOutputStream(), true); // Can send messages to client as if printing to console
		BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream())); // Can read what client sends us with BufferedRead
		out.println("who is it?"); // Sends message to client
		String line;
		while ((line = in.readLine()) != null) { // Read from client line by line until null -- they hang up. Sits and waits until client sends something
			System.out.println("received:" + line);
			out.println("hi " + line + "!  anybody else there?"); // Send message back to client
		}
		System.out.println("client hung up");

		// Clean up shop
		out.close();
		in.close();
		sock.close();
		listen.close(); // End with closing the main socket on server
	}
}


















