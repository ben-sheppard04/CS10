import java.util.Iterator;
import java.util.ListIterator;

public class TimeTest {

    public static Long loopTest1(SinglyLinked<Integer> list, Integer targetValue) throws Exception {
        //use get, start back at head each time through loop
        long startTime = System.nanoTime();
        for (int i = 0; i < list.size(); i++) {
            Integer value = list.get(i);
            if (value == targetValue) {
                break;
            }
        }
        return System.nanoTime() - startTime;
    }

    public static Long loopTest2(SinglyLinked<Integer> list, Integer targetValue) {
        long startTime = System.nanoTime();
        //use iterator to not start back at head each time
        Iterator<Integer> iter = list.iterator();
        while (iter.hasNext()) {
            if (iter.next() == targetValue) {
                break;
            }
        }
        return System.nanoTime() - startTime;
    }
    public static void main(String[] args) throws Exception {
        //add numberOfItems to list
        SinglyLinked<Integer> list = new SinglyLinked<>();
        int numberOfItems = 1000;
        for (int i = 0; i < numberOfItems; i++) {
            list.add(i);
        }
        Long time1 = loopTest1(list,numberOfItems-1);
        System.out.printf("method 1 took %,15d nanoseconds\n",time1);
        Long time2 = loopTest2(list,numberOfItems-1);
        System.out.printf("method 2 took %,15d nanoseconds\n", time2);
        System.out.println("ratio time1/time2: " + time1/(float)time2);
    }
}
