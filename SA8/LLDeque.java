/**
 * Implements SimpleDeque with a Linked List to allow adding and removing at both ends of the queue
 * @author Ben Sheppard
 */
public class LLDeque<T> implements SimpleDeque<T>{
    private Element head;
    private Element tail;
    private int size;

    /**
     * Element class for each element of linked list.
     * @author Ben Sheppard
     */
    private class Element{
        T data;
        Element next;
        Element previous;
        public Element(T data, Element next, Element previous){
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
    }
    public LLDeque(){
        head = null;
        tail = null;
        size = 0;
    }
    /**
     * Adds to the front of the deque
     * O(1)
     * @param item Item to add
     */
    public void addFirst(T item) {
        if(size == 0){
            head = new Element(item, null, null);
            tail = head;
        }
        else{
            head = new Element(item, head, null ); //sets new head to point to old head
            head.next.previous = head; //sets previous of the old head to the new head
        }
        size++;
    }

    /**
     * Adds to the end of the deque
     * O(1)
     * @param item Item to add
     */
    public void addLast(T item) {
        if(size == 0){
            head = new Element(item, null, null);
            tail = head;
        }
        else{
            tail = new Element(item, null, tail); //sets new tail to old tail
            tail.previous.next = tail; //sets the next of the old tail to the new tail
        }
        size++;
    }

    /**
     * Removes first element of deque.
     * O(1)
     * @return Data that was removed
     * @throws Exception Can't remove if deque is empty
     */
    public T removeFirst() throws Exception {
        if(head == null){
            throw new Exception("Deque is empty");
        }
        if(size == 1){
            T removed = head.data; //data will remove
            head = null;
            tail = null;
            size--;
            return removed;
        }
        else{
            T removed = head.data; //data will remove
            head = head.next; //old head garbage collected
            size--;
            return removed;
        }
    }

    /**
     * @return Data removed
     * O(1)
     * @throws Exception Can't remove if deque is empty
     */
    public T removeLast() throws Exception {
        if(head == null){
            throw new Exception("Deque is empty");
        }
        if(size == 1){
            T removed = tail.data;
            head = null;
            tail = null;
            size--;
            return removed;
        }
        else{
            T removed = tail.data;
            tail = tail.previous;
            size--;
            return removed;
        }
    }

    /**
     * Gets data of first element.
     * O(1)
     * @return Data of first element
     * @throws Exception Deque empty
     */
    public T getFirst() throws Exception {
        if(head == null){
            throw new Exception("Deque empty.");
        }
        return head.data; //head is first
    }

    /**
     * Gets data of last element.
     * O(1)
     * @return Data of last element.
     * @throws Exception Deque Empty
     */
    public T getLast() throws Exception {
        if(head == null){
            throw new Exception("Deque empty.");
        }
        return tail.data; //tail is last element
    }

    /**
     * Clears deque, setting head and tail to null.
     * O(1)
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Checks if deque contains an item
     * O(n)
     * @param item Item to check if deque contains
     * @return t/f if item is contained
     */
    public boolean contains(T item) {
        int index = 0;
        Element curr = head; //starts at head
        while(index < size){
            if(curr.data.equals(item)) //if data is equal to current element's data
                return true;
            else {
                curr = curr.next; //moves curr to next element
            }
            index++; //increments list so while loop ends after getting to end of LL
        }
        return false;
    }

    /**
     * O(1)
     * @return true/false if deque is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * O(1)
     * @return size of deque
     */
    public int size() {
        return size;
    }

    /**
     * Prints each element of the deque. (Extra method)
     *
     */
   public void printDeque(){
       int index = 0;
       Element curr = head;
       System.out.print("[");
       while(index < size){
           System.out.print(curr.data + " ");
           curr = curr.next;
           index++;
       }
       System.out.println("]");
   }


    public static void main(String[] args) throws Exception {
        LLDeque<Integer> testDeque = new LLDeque<Integer>();
        System.out.println("Should be true: " + testDeque.isEmpty()); //Should be true
        System.out.println();

        testDeque.addFirst(5);
        testDeque.addLast(10);
        testDeque.addFirst(3);
        testDeque.addFirst(2);
        testDeque.addLast(14);
        testDeque.addLast(33);
        testDeque.addFirst(1);
        System.out.println("The Data:");
        testDeque.printDeque();
        System.out.println();
        System.out.println("Contains 4? Should be false: " + testDeque.contains(4));
        System.out.println("Contains 1? Should be true: " + testDeque.contains(1)); //checking first element
        System.out.println("Contains 10? Should be true: " + testDeque.contains(10)); //checking middle element
        System.out.println("Contains 33? Should be true: " + testDeque.contains(33)); //checking last element
        System.out.println();


        System.out.println("Is empty? Should be false: " + testDeque.isEmpty()); //should be false
        System.out.println("Size. Should be 7: " + testDeque.size()); //should be 7
        System.out.println("Get first. Should be 1: " + testDeque.getFirst()); //should be 1
        System.out.println("Get last. Should be 33: " + testDeque.getLast()); //should be 33

        System.out.println("Removed first. Should return 1: " + testDeque.removeFirst());
        System.out.println("Removed first. Should return 2: " + testDeque.removeFirst());
        System.out.println("Removed first. Should return 33: " + testDeque.removeLast());

        System.out.println();
        System.out.println("New Data:");
        testDeque.printDeque();

        System.out.println();
        System.out.println("Get first. Should be 3: " + testDeque.getFirst()); //should be 3
        System.out.println("Get last. Should be 14: " + testDeque.getLast()); //should be 14
        testDeque.clear();
        System.out.println("Is empty? Should be true: " + testDeque.isEmpty());
    }
}
