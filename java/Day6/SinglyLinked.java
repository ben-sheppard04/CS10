import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A singly-linked list implementation of the SimpleList interface
 * Also implements iterable so SinglyLinked can be used in for-each loops
 * 
 * @author Tim Pierson, Dartmouth CS10, Winter 2024, based on SinglyLinked from prior terms, added iterator
 */
public class SinglyLinked<T> implements SimpleList<T>, Iterable<T> { //"implements" - promises Java that you will implement
	//all of the methods in that interface. If you don't - Java won't compile. Don't worry about iterable for now. Generic
	//T data type so can hold anything that is reference type!
	private Element head;	// front of the linked list. Private so that head can't be changed by outsider. Notice that head
	//is never newed. It is an alies pointing to first element.
	private int size;		// # elements in the list

	/**
	 * The linked elements in the list: each has a piece of data and a next pointer
	 */
	private class Element { //This is a class within a class! Private so that no other classes can edit the lineked list.
		//Will implement data and next pointers (could be in its own file). Constructor takes data as type T and pointer
		//to next Element.
		private T data;
		private Element next;

		private Element(T data, Element next) {
			this.data = data;
			this.next = next;
		}
	}

	public SinglyLinked() { //Constructor initiating the head to null and size to zero
		head = null;
		size = 0;
	}

	/**
	 * Return the number of elements in the List (they are indexed 0..size-1)
	 * @return number of elements
	 */
	public int size() {
		return size;
	} //This is O(1) - never have to loop over it.

	/**
	 * Returns true if there are no elements in the List, false otherwise
	 * @return true or false
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Helper function, advancing to the nth Element in the List and returning it
	 * @param n - number of items to advance
	 * @return item at index n
	 * @throws Exception for invalid index
	 */
	private Element advance(int n) throws Exception { //Helper method -- one of those additional methods that you could have.
		//Note that it throws Exception.
		Element e = head;
		//safety check for valid index (don't assume caller checked!)
		if (e == null || n < 0 || n >= size) {
			throw new Exception("invalid index"); //Told you to advance yet list is null or index is invalid
		}

		// Just follow the next pointers n times
		for (int i = 0; i < n; i++) {
			e = e.next; // returns the "n"th element
		}
		return e;
	}


	/**
	 * Add item at index idx
	 * @param idx index to add item
	 * @param item item to add
	 * @throws Exception invalid index
	 */
	public void add(int idx, T item) throws Exception {
		//safety check for valid index (can add at size index)
		if (idx < 0 || idx > size) {
			throw new Exception("invalid index");			
		}
		else if (idx == 0) {
			// Insert at head
			head = new Element(item, head); //new item gets next pointer set to head.
			// Does the splice in one line. Right side updates the next element to have old ehad.
			// Left side sets the head to point to new item
		}
		else {
			// It's the next thing after element # idx-1. Advance to this previous index.
			Element e = advance(idx-1);
			// Splice it in
			e.next = new Element(item, e.next);	//create new element with next pointing at prior element's next 
												//and prior element's next updated to point to this item
		}
		size++;
	}

	/**
	 * Add item at end of List
	 * @param item - item to add to List
	 * @throws Exception
	 */
	public void add(T item) throws Exception {
		add(size,item);
	}


	/**
	 * Remove and return item at index idx
	 * @param idx index to remove
	 * @throws Exception
	 */
	public T remove(int idx) throws Exception { //Review this method!
		T data = null; //data to return
		//safety check for valid index
		if (head == null || idx < 0 || idx >= size) {
			throw new Exception("invalid index");			
		}
		else if (idx == 0) {
			data = head.data;
			head = head.next;
		}
		else {
			// It's the next thing after element # idx-1
			Element e = advance(idx-1);
			data = e.next.data;
			// Splice it out
			e.next = e.next.next;  //nice!
		}
		size--;
		return data;
	}

	/**
	 * Return the item at index idx
	 * @param idx index of item
	 * @return item at index idx
	 * @throws Exception invalid index
	 */
	public T get(int idx) throws Exception {
		//safety check for valid index
		if (idx < 0 || idx >= size) {
			throw new Exception("invalid index");			
		}
		Element e = advance(idx);
		return e.data;
	}

	/**
	 * Overwrite the data at index idx
	 * @param idx index to overwrite
	 * @param item data to store at idx
	 * @throws Exception invalid index
	 */
	public void set(int idx, T item) throws Exception {
		//safety check for valid index
		if (idx < 0 || idx >= size) {
			throw new Exception("invalid index");			
		}
		Element e = advance(idx); // Run time complexity is O(n) becuase it is determined by the index you do it at
		e.data = item;
	}

	/**
	 * Return a String representation of the List
	 * @return box and pointer diagram with end marked with [/]
	 */
	@Override
	public String toString() { //Returns a String! It loops through every element until you hit end of the line. This
		//is theta(n) because there is no way to

		String result = "";
		for (Element x = head; x != null; x = x.next) 
			result += x.data + "->"; 
		result += "[/]";

		return result;
	}

	/**
	 * Returns an iterator over the elements in this List.
	 * @return iterator
	 */
	public Iterator<T> iterator() {  //satisfy iterator requirement in SimpleSet interface
		return new ListIterator();
	} //The method required by iterable

	/**
	 * Iterator class that implements the required functionality to use this List in a for each loop
	 */
	private class ListIterator implements Iterator<T> { //nested class (private to SinglyLinked). Implements Iterator.
		// Use curr to point to next item in List
		Element curr; //store current value

		public ListIterator() {
			curr = head;
		}

		/**
		 * Does iterator have more items?
		 *
		 * @return true if more items, false otherwise
		 */
		public boolean hasNext() {
			return curr != null;
		}

		/**
		 * Return the next item in the iterator, advance to next item in list
		 *
		 * @return item stored at curr position in the list
		 */
		public T next() {
			if (curr == null) {
				throw new IndexOutOfBoundsException(); //Can do specific Exception with this.
			}
			T data = curr.data;
			curr = curr.next;
			return data;
		}
	}

}