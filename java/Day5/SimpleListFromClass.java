public interface SimpleListFromClass<T> { //An interface. Just a list of methods that need to be implemented.
    //simpler version of java's List interface which has a few more methods. Ours simplifies.
    public int size();
    public boolean isEmpty();
    public void add(int idx, T item) throws Exception;
    public void add(T item) throws Exception;
    public T remove(int idx) throws Exception;
    public T get(int idx) throws Exception;
    public void set(int idx, T item) throws Exception;
}
