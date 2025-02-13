import java.util.Iterator;
import java.util.ListIterator;

/**
 * An array (expanding as needed) implementation of the SimpleList interface
 * Also implements an iterator so it can be used in a for-each loop
 * 
 * @author Tim Pierson, Dartmouth CS10, Winter 2024, based on prior terms GrowingArray
 */
public class GrowingArray<T> implements SimpleList<T>, Iterable<T> {
	private T[] array;
	private int size;		// how much of the array is actually filled up so far
	private static final int initCap = 10; // How big the array should be initially. Why choose 10??

	public GrowingArray() {
		array = (T[]) new Object[initCap];  // java generics oddness -- create an array of objects and typecast to array of T
		//Only allocates memory (instantiates array) in constructor once it is called - good practice.
		size = 0;
	}

	/**
	 * Return the number of elements in the List (they are indexed 0..size-1)
	 * @return number of elements
	 */
	public int size() {//O(1) runtime
		return size;
	}

	/**
	 * Returns true if there are no elements in the List, false otherwise
	 * @return true or false
	 */
	public boolean isEmpty() {
		return size == 0;
	}


	/**
	 * Add item at index idx, move other items to make a hole for this new item
	 * @param idx index to add
	 * @param item item to add
	 * @throws Exception invalid index
	 */
	public void add(int idx, T item) throws Exception { //Adding in middle (if don't have to make new array) then it has to shift so not O(1)
		if (idx > size || idx < 0) throw new Exception("invalid index");
		if (size == array.length) {
			// Double the size of the array, to leave more space
			T[] copy = (T[]) new Object[size*2];
			// Copy it over
			for (int i=0; i<size; i++) copy[i] = array[i]; //This copying process is expensive
			array = copy; //old array is garbage collected
		}
		// Shift right to make room
		for (int i=size-1; i>=idx; i--) array[i+1] = array[i]; //Starts at right, goes left and shifts over to the right up to the index to make space for new one.
		array[idx] = item;
		size++;
	}

	/**
	 * Add item at end of List
	 * @param item - item to add to List
	 * @throws Exception
	 */
	public void add(T item) throws Exception { //Constant runtime (assuming you don't't have to copy)
		add(size,item);
	}

	/**
	 * Remove and return the item at index idx.  Move items left to fill hole.
	 * @param idx index of item to remove
	 * @return the value previously at index idx
	 * @throws Exception invalid index
	 */
	public T remove(int idx) throws Exception { //Worst runtime if you remove first one. Best if you remove the last one (don't have to move anything)
		if (idx > size-1 || idx < 0) throw new Exception("invalid index");
		T data = array[idx];
		// Shift left to cover it over
		for (int i=idx; i<size-1; i++) array[i] = array[i+1];
		size--;
		return data;
	}

	/**
	 * Return item at index idx
	 * @param idx index of item to return
	 * @return item stored at index idx
	 * @throws Exception invalid index
	 */
	public T get(int idx) throws Exception {
		if (idx >= 0 && idx < size) return array[idx]; //O(1) for any index- only 2 operations baseAddress + idx * type of memory bytes
		else throw new Exception("invalid index");
	}

	/**
	 * Overwrite item at index idx with item parameter
	 * @param idx index of item to get
	 * @param item overwrite existing item at index idx with this item
	 * @throws Exception invalid index
	 */
	public void set(int idx, T item) throws Exception {
		if (idx >= 0 && idx < size) array[idx] = item; //O(1) for any index
		else throw new Exception("invalid index");
	}

	public String toString() {
		String result = "[";
		for (int i = 0; i < size-1; i++) {
			result += array[i] + ", ";
		}
		if (size > 0) {
			result += array[size - 1];
		}
		result += "]";

		return result;
	}

	/**
	 * Returns an iterator over the elements in this List.
	 * @return iterator
	 */
	public Iterator<T> iterator() {  //satisfy iterator requirement in SimpleSet interface
		return new ListIterator();
	}

	/**
	 * Iterator class that implements the required functionality to use this List in a for each loop
	 */
	private class ListIterator implements Iterator<T> {
		// Use curr to point to next item in List
		int curr;

		public ListIterator() {
			curr = 0;
		}

		/**
		 * Does iterator have more items?
		 *
		 * @return true if more items, false otherwise
		 */
		public boolean hasNext() {
			return curr < size;
		}

		/**
		 * Return the next item in the iterator, advance to next item in list
		 *
		 * @return item stored at curr position in the list
		 */
		public T next() {
			if (curr >= size) {
				throw new IndexOutOfBoundsException();
			}
			curr++;
			return array[curr-1];
		}
	}
}
