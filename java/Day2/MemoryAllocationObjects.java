
/** MemoryAllocationObjects - demonstrate memory allocation for objects
 * Objects get an entry on the stack that points to a location on the heap
 * The object itself is allocated on the heap
 * 
 * @author Tim Pierson, Dartmouth CS 10, Winter 2024
 *
 */
public class MemoryAllocationObjects {

	public static void main(String[] args) {
		//declare Student objects
		Student05 alice = new Student05("f00xyz", "Alice", 2027);
		Student05 bob; //notice no new keyword
		bob = alice; //bob equals alice
		Student05 charlie = new Student05("f00abc", "Charlie", 2025);
		System.out.println(alice.name +" "+bob.name);

		//update alice's name
		alice.setName("Ally");
		System.out.println(alice.name+" "+bob.name);
		
		//printing objects implicitly calls toString()
		System.out.println(alice+" "+bob+" "+charlie);
	}
}
