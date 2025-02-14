/**
 * A resource that can be acquired and released (for MonitoredDiningPhilosophers)
 * In this version, it doesn't control its own availability
 * 
 *  @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate out MonitoredFork from MonitoredDiningPhilosophers
 *  @author Tim Pierson, Dartmouth CS 10, provided for Winter 2024
 */
public class MonitoredFork {
	boolean available = true;
}