import java.util.PriorityQueue;

public class PQtest {

    public static void main(String[] args) {
        PriorityQueue<Integer> test = new PriorityQueue<Integer>((a,b) -> (a - b));
        test.add(1);
        test.add(2);
        test.add(3);
        System.out.println(test.remove());
        System.out.println(test.remove());
        System.out.println(test.remove());
    }
}
