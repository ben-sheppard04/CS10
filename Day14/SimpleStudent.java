import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Demo of Comparable and Comparator
 * 
 * @author Tim Pierson, Dartmouth CS10, Winter 2024, based on prior term code
 */
public class SimpleStudent implements Comparable<SimpleStudent> {
	private String name;
	private int year;
	
	public SimpleStudent(String name, int year) {
		this.name = name;
		this.year = year;
	}

	public int getYear() {
		return year;
	}

	/**
	 * Comparable: just use String's version (lexicographic)
	 */
	public int compareTo(SimpleStudent s2) {
		return name.compareTo(s2.name);
	}

	@Override
	public String toString() {
		return name + " '"+year;
	}
	
	public static void main(String[] args) {
		//create ArrayList of students and add some
		List<SimpleStudent> students = new ArrayList<SimpleStudent>();
		students.add(new SimpleStudent("charlie", 18));
		students.add(new SimpleStudent("alice", 20));
		students.add(new SimpleStudent("bob", 19));
		students.add(new SimpleStudent("elvis", 21));
		students.add(new SimpleStudent("denise", 20));
		System.out.println("original:" + students);
		
		// Three methods for using Comparator
		
		// Method 1:
		// Create Java PriorityQueue and use Student 
		// class's compareTo method (lexicographic order)
		// this is used if comparator not passed to PriorityQueue constructor
		PriorityQueue<SimpleStudent> pq = new PriorityQueue<SimpleStudent>();
		pq.addAll(students); //add all Students in ArrayList in one statement
		
		//remove until empty (this essentially sorting!)
		System.out.println("\nlexicographic:");
		while (!pq.isEmpty()) System.out.println(pq.remove());
		
		
		// Method 2: 
		// Use a custom Comparator.compare (length of name) instead
		// of using the element's compareTo function
		// Java will use this to compare two Students (here on length of name)
		class NameLengthComparator implements Comparator<SimpleStudent> {
			public int compare(SimpleStudent s1, SimpleStudent s2) {
				return s1.name.length() - s2.name.length();
			}
		} 
		Comparator<SimpleStudent> lenCompare = new NameLengthComparator();
		pq = new PriorityQueue<SimpleStudent>(lenCompare); //passing Comparator to PriorityQueue
		pq.addAll(students); //add all students to PriorityQueue
		System.out.println("\nlength:");
		//remove until empty (sorting)
		while (!pq.isEmpty()) System.out.println(pq.remove());
		
		//Method 3:
		// Use a custom Comparator via Java 8 anonymous function (here based on year)
		// pass Comparator to PriorityQueue constructor
		pq = new PriorityQueue<SimpleStudent>((SimpleStudent s1, SimpleStudent s2) -> s2.year - s1.year);
		pq.addAll(students); //add all students to Priority Queue
		System.out.println("\nyear:");
		//remove until empty (sorting)
		while (!pq.isEmpty()) System.out.println(pq.remove());
	}
}
