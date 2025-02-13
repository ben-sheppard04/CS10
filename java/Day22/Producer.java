/**
 * Puts messages in the box for ProducerConsumer
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate out helper classes
 * @author Tim Pierson, Dartmouth CS 10, Winter 2018, updated to accept number of messages to send in constructor
 * @author Tim Pierson, Dartmouth CS 10, provided for Winter 2024
 */
public class Producer extends Thread {
	private MessageBox box;
	private int numberToSend;

	public Producer(MessageBox box, int numberToSend) {
		this.box = box;
		this.numberToSend = numberToSend;
	}

	/**
	 * Wait for a while then puts a message
	 * Puts "EOF" when # messages have been put
	 */
	public void run() {
		try {
			for (int i = 0; i < numberToSend; i++) { 
				sleep((int)(Math.random()*5000)); //sleep for random time up to 5 seconds. simulates doing work
				box.put("message #" + i); //put a new message in MessageBox. put will cause to wait until consumer takes first one out
			}
			box.put("EOF"); //EOF means end of file
		}
		catch (InterruptedException e) {
			System.err.println(e);
		}
	}
}