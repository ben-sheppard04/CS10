public class ArrayDeque<T> implements SimpleDeque<T>{

    private T[] array;
    private int first;
    private int last;
    private int size;
    private static int init_cap = 10;
    public ArrayDeque(){
        array = (T[])new Object[init_cap];
        size = 0;
        first = array.length / 2;
        last = array.length / 2;
    }
    public String toString(){
        String string = "";
        for(int i = 0; i < array.length; i++){
            string += array[i] + " ";
        }
        return string;
    }
    public void doubleAndCopy(){
        T[] newArray = (T[])new Object[array.length * 2];
        int firstIndex = 0;
        for(int i = newArray.length / 2 - array.length / 2 + first; i < newArray.length / 2 + last - array.length / 2; i++){
            newArray[i] = array[firstIndex];
            firstIndex++;
        }
        array = newArray;
        for(int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }


    }

    /**
     * @param item
     */
    @Override
    public void addFirst(T item) {
        if (first == 0){
            doubleAndCopy();
        }
        first--;
        array[first] = item;
        size++;
    }

    /**
     * @param item
     */
    @Override
    public void addLast(T item) {
        if(last == array.length){
            doubleAndCopy();
        }
        array[last] = item;
        last++;
        size++;
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public T removeFirst() throws Exception {
        if(size == 0){
            throw new Exception("Size is 0");
        }
        T removed = array[first + 1];
        array[first + 1] = null;
        first++;
        return removed;
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public T removeLast() throws Exception {
        if(size == 0){
            throw new Exception("Size is 0");
        }
        T removed = array[last - 1];
        array[last - 1] = null;
        last--;
        return removed;
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public T getFirst() throws Exception {
        if (size == 0) {
            throw new Exception("Size is 0");
        }
        return array[first + 1];
    }
    /**
     * @return
     * @throws Exception
     */
    @Override
    public T getLast() throws Exception {
        if(size == 0){
            throw new Exception("Size is 0");
        }
        return array[last - 1];
    }

    /**
     *
     */
    @Override
    public void clear() {

    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean contains(T item) {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return
     */
    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> testDeque = new ArrayDeque<Integer>();
        System.out.println("Should be true: " + testDeque.isEmpty()); //Should be true
        System.out.println(testDeque);
        System.out.println();

        testDeque.addFirst(5);
        testDeque.addLast(10);
        testDeque.addFirst(3);
        testDeque.addFirst(2);
        testDeque.addLast(14);
        testDeque.addLast(33);
        testDeque.addFirst(1);
        testDeque.addFirst(4);
        testDeque.addFirst(3);
        System.out.println("The Data:");
        System.out.println(testDeque);
        /*System.out.println();
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

*/

    }
}
