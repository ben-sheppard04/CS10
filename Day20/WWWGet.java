import java.net.*;
import java.io.*;

/**
 * Demonstrates getting a webpage from the internet
 * @author unknown, most likely Chris Bailey-Kellogg
 * @author Tim Pierson, Dartmouth CS 10, Fall 2018 -- small tweaks
 * @author Tim Pierson, Dartmouth CS 10, Winter 2020 -- update to point to html file
 * @author Tim Pierson, Dartmouth CS 10, provided for Winter 2024
 */
public class WWWGet {
	public static void main(String[] args) throws Exception {
		// Open a stream reader for processing the response from the URL
		URL url = new URL("https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/layout/flow.html"); // Oddly, wont work with Pierson website. No idea why
		System.out.println("*** getting " + url);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		// Read lines from the stream, just like reading a file
		String line;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
		in.close();
		System.out.println("*** done");
	}
}
