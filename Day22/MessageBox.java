/**
 * Coordinates sending and receiving a message for ProducerConsumer
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate out helper classes
 * @author Tim Pierson, Dartmouth CS 10, provided for Winter 2024
 */
public class MessageBox {
	private String message = null;

	/**
	 * Put m as message once it's okay to do so (current message has been taken)
	 */
	public synchronized void put(String m) throws InterruptedException { // synchronized makes sure only one Producer at a time can store message.
		//check to see if message is not null, might have been woken by put() notifyAll
		while (message != null) { // Why is it in a while loop? so that when they wake up, will allow one through and then rest of them will remain waiting.
			//if was in if statement, it would sleep once, then all the rest would try to go through
			wait();
		}
		message = m;
		notifyAll(); //wakes producers AND consumers
	}

	/**
	 * Takes message once it's there, leaving empty message
	 */
	public synchronized String take() throws InterruptedException {
		//check to see if message is null, might have been woken by take() notifyAll
		while (message == null) {
			wait();
		}
		String m = message;
		message = null;
		notifyAll();  //wakes producers AND consumers
		return m;
	}
}