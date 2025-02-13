/**
 * Helper for IncrementerSync
 * Packages up the total in an object with a synchronized increment method
 * @author Tim Pierson, Dartmouth CS 10, provided for Winter 2024
 */
public class IncrementerTotal {
	int total = 0;
	public synchronized void inc() { // synchronized keyword in front of inc method means only one thread can be running this code at once
		total++;
	}
}