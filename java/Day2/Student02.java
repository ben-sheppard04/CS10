/**
 * Student series demonstrates encapsulation by representing a student in a class
 * Student02 - adds check in setYear to only accept valid years
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class Student02 {
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
        else{
            System.out.println("Invalid year"); //in this case, year stays at 0 (default)
        }
    }


    public static void main(String[] args) {
        Student02 alice = new Student02();
        alice.setId("f00xyz");
        alice.setName("Alice");
        alice.setYear(100);
        System.out.println("ID: " + alice.studentId +
                ", Name: " + alice.name +
                ", Year: " + alice.graduationYear);
    }
}
