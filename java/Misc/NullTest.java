import java.util.HashMap;
import java.util.Map;

public class NullTest {
    public static void main(String[] args) {
        Map<Integer, Boolean> myMap = new HashMap<Integer, Boolean>();
        System.out.println(myMap == null);
    }
}
