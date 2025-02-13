import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles communication to/from the server for the editor
 *
 * @author Ben Sheppard
 * @author Tara Salli
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Chris Bailey-Kellogg; overall structure substantially revised Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 * @author Tim Pierson Dartmouth CS 10, provided for Winter 2024
 */
public class EditorCommunicator extends Thread {
	private PrintWriter out;		// to server
	private BufferedReader in;		// from server
	protected Editor editor;		// handling communication for
	private CommunicationProtocol handler; // handles incoming messages from server

	/**
	 * Establishes connection and in/out pair
	 */
	public EditorCommunicator(String serverIP, Editor editor) {
		this.editor = editor;
		handler = new CommunicationProtocol(editor); // Creates handler by passing in editor
		System.out.println("connecting to " + serverIP + "...");
		try {
			Socket sock = new Socket(serverIP, 4242);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("...connected");
		}
		catch (IOException e) {
			System.err.println("couldn't connect");
			System.exit(-1);
		}
	}

	/**
	 * Sends message to the server
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 */
	public void run() {
		try {
			// Handle messages
			// Loop to try to get a message from the server. When you get a message, update sketch of client
			String line;
			while((line = in.readLine()) != null) { // On message from server
				handler.handleMessage(line); // handle message
				editor.repaint(); // need to immediately repaint after handling message on every client
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("server hung up");
		}
	}
	// Send editor requests to the server

	/**
	 * Request server to move shape using protocol
	 * @param id ID of shape to move
	 * @param dx dx of moving shape
	 * @param dy dy of moving shape
	 */
	public void requestMove(int id, int dx, int dy) {
		send("toServer move " + id + " " + dx + " " + dy);
	}
	/**
	 * Request server to recolor shape using protocol
	 * @param id ID of shape to recolor
	 * @param color new color
	 */
	public void requestRecolor(int id, Color color) {
		send("toServer recolor " + id + " " + color.getRGB());
	}
	/**
	 * Request server to delete shape using protocol
	 * @param id ID of shape to move
	 */
	public void requestDelete(int id) {
		send("toServer delete " + id);
	}
}
