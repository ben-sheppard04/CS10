/**
 * Student series demonstrates encapsulation by representing a student in a class
 * Student0 - base example with student ID, name, and graduatation year
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */

public class Student0 {
    String studentId;
    String name;
    int graduationYear;

    public static void main(String[] args) {
        Student0 alice = new Student0();
        alice.studentId = "f00xyz";
        alice.name = "Alice";
        alice.graduationYear = 2027;
        System.out.println("ID: " + alice.studentId +
                ", Name: " + alice.name +
                ", Year: " + alice.graduationYear);
    }
}
