/** 
 * An incrementer simply increments a shared variable a specified number of times.
 * With two incrementers in two threads, the total should be twice the number of times, right?
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012, inspired by an example by Scot Drysdale
 * @author Tim Pierson, Dartmouth CS 10, provided for Winter 2024
 */
public class Incrementer extends Thread {
	private static int total = 0;  					// a variable shared by all incrementers
	private static final int times = 1000000;		// how many times to increment total, in each thread

	/**
	 * Increments total the specified number of times
	 */
	public void run() {
		for (int i = 0; i < times; i++) {
			total++; // Actually 3 steps -- 1. Read value from memory to CPU. 2. Increment. 3. Write value back to memory.
		}
	}

	public static void main(String [] args) throws Exception {
		Incrementer inc1 = new Incrementer();
		Incrementer inc2 = new Incrementer();

		// Fire off threads and wait for them to complete
		inc1.start();
		inc2.start();
		inc1.join();
		inc2.join();

		System.out.println("total at end = " + total);
	}
}







