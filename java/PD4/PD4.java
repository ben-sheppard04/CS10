import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PD4 {

    public static void main(String[] args) {
        Map<String, ArrayList<String>> students = new HashMap<String, ArrayList<String>>();
        ArrayList<String> courseList = new ArrayList<String>();
        students.put("Ben", courseList);
        students.get("Ben").add("CS10");
    }

}
