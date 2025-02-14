import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BSTSet<T extends Comparable<T>> implements SimpleSet<T>, Iterable<T> {
    private BST<T, Integer> tree; //BST which stores the BSTSet
    private int size; //size of BSTSet
    private static final Integer value = 0; //Sets the value in BST as 0; unneeded for functionality.

    public BSTSet() { //confusing: don't have angle brackets here. Ask why?
        size = 0;
    }

    /**
     * Add an item to the Set if it is not already present
     *
     * @param item item to add to set
     * @return true if the item is added, false otherwise
     */
    public boolean add(T item) {
        if (size() == 0) {
            tree = new BST<T, Integer>(item, value); //Makes new BST with item as root
            size++;
            return true;
        } else {
            if (!contains(item)) { //If item not already in tree
                tree.insert(item, value); //Insert item to tree
                size++;
                return true;
            } else { //Handle duplicate
                return false;
            }
        }
    }

    /**
     * Remove an item from the Set
     *
     * @param item Item which is to be removed
     * @return true if the item is removed, false otherwise
     */
    public boolean remove(T item) {
        if (contains(item)) {
            try { //used try/catch because the delete method throws an exception. Even though I checked if the item is contained in the
                //tree, it still gave an error unless I either did try/catch or made the remove method thrown an exception too,
                //which would require modifying SimpleSet (not allowed).
                tree = tree.delete(item);
            } catch (Exception e) {
            }
            size--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Makes this BSTSet a Set containing no items
     */
    public void clear() {
        tree = null;
        size = 0;
    }

    /**
     * Returns whether an item is contained in set
     *
     * @param item item to check if in set
     * @return true if the item is in the Set, false otherwise
     */
    public boolean contains(T item) {
        if (tree != null) { //Checks to ensure that tree has value. If it didn't next() would throw exception
            for (Iterator<T> iter = iterator(); iter.hasNext(); ) {
                if (iter.next().equals(item)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * Checks if set is empty
     *
     * @return true if the Set is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Size of set
     *
     * @return # elements in the set
     */
    public int size() {
        return size;
    }

    /**
     * Returns an iterator to loop over the Set
     */
    public Iterator<T> iterator() {
        return new TreeIterator();
    }

    private class TreeIterator implements Iterator<T> {
        private List<T> keyList; //Stores all keys in BST in list format
        private int currIndex; //Current index of iterator

        public TreeIterator() {
            keyList = new ArrayList<T>();
            currIndex = 0;
            if (tree != null) traverse(tree); //If the tree is not null, traverses it to build keyList
        }

        /**
         * Traverses the BST using inorder, adding each key of the tree to keyList
         *
         * @param root tree to traverse
         */
        public void traverse(BST<T, Integer> root) {
            if (root.hasLeft()) traverse(root.getLeft());
            keyList.add(root.getKey()); //Adds the key of root to keyList
            if (root.hasRight()) traverse(root.getRight());
        }

        public boolean hasNext() {
            return currIndex < keyList.size(); //Checks bounds
        }

        public T next() {
            T curr = keyList.get(currIndex); //Gets the element at currIndex from keyList
            currIndex++; //goes to next item in arrayList
            return curr;
        }
    }

    public static void main(String[] args) {
        //TEST CASES:
        SimpleSet<Integer> testSet = new BSTSet<Integer>(); //create new BSTSet
        testSet.add(4);
        testSet.add(3);
        testSet.add(3);
        testSet.add(1);
        testSet.add(6);
        testSet.add(4);

        System.out.println("Is empty?" + testSet.isEmpty());
        System.out.println("Size: " + testSet.size()); // Should be 4 if didn't add duplicates
        System.out.println("Contains: " + testSet.contains(3)); //Should be true
        System.out.println("Contains: " + testSet.contains(9)); //Should be true
        System.out.print("Remove: " + testSet.remove(4));//Should be true
        System.out.print("Remove: " + testSet.remove(4));//Should be false (already removed)
        System.out.println("Size: " + testSet.size()); // Should be 3
        System.out.println("Contains " + testSet.contains(4)); //Now should be false, we removed 4
        System.out.println("Clear Set");
        testSet.clear();
        System.out.println("Size: " + testSet.size()); //Now size should be 0
        System.out.println("Is empty? " + testSet.isEmpty()); //Should now be true
        System.out.println("Contains number 3? " + testSet.contains(3)); //Should be false
    }
}