import java.util.ArrayList;
import java.util.List;
/**
 * Sample program to demonstrate List functionality.
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class StudentTrackerAppList {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<Student>();
        students.add(new Student("Alice", "f00xyz"));
        students.add(new GraduateStudent("Bob", "f00123"));
        students.add(new InternationalStudent("Charlie", "f00abc"));

        //print students using for-each loop
        System.out.println("Before studying");
        for (Student student : students) {
            System.out.println(student);
        }

        //randomly select students to study to simulate an actual application
        for (int i = 0; i < 10; i++) {
            //pick random student
            int index = (int) (Math.random() * students.size());

            //add random studying time between 0 and 5 hours
            double time = Math.random() * 5;
            students.get(index).study(time);
        }

        //print students using C-style loop
        System.out.println("After studying");
        for (int i = 0; i < students.size(); i++) {
            System.out.println(students.get(i));
        }
    }
}
