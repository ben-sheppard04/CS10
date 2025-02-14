/**
 * Student series demonstrates encapsulation by representing a student in a class
 * Student03 - adds getters for instance variables
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class Student03 {
    protected String studentId;
    protected String name;
    protected int graduationYear;

    /**
     * Setters for instance variables
     */
    public void setId(String studentId) { this.studentId = studentId; }
    public void setName(String name) { this.name = name; }
    public void setYear(int year) {
        //only accept valid years
        if (year > 1769 && year < 2100) {
            graduationYear = year;
        }
    }

    /**
     * Getters for instance variables
     */
    public String getId() { return studentId; }
    public String getName() { return name; }
    public int getGraduationYear() { return graduationYear; }




    public static void main(String[] args) {
        Student03 alice = new Student03();
        alice.setId("f00xyz");
        alice.setName("Alice");
        alice.setYear(2027);
        System.out.println("ID: " + alice.studentId +
                ", Name: " + alice.name +
                ", Year: " + alice.graduationYear);
    }
}
