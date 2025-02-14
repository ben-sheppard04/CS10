public interface MinPriorityQueue<E extends Comparable<E>> {
    /**
     * Is priority queue empty?
     * @return True if empty, false if not.
     */
    public boolean isEmpty();

    /**
     * Insert an element into the queue.
     * @param element element to insert
     */
    public void insert(E element);

    /**
     * return the element with the minimum key, without removing it from the queue
     * @return Minimum element
     */
    public E minimum();

    /**
     * Return the element with the minimum key, adn remove it from the queue.
     * @return minimum lement
     */
    public E extractMin();

}
