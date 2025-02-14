/**
 * Student series demonstrates encapsulation by representing a student in a class
 * Student04 - adds two constructors
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class Student04 {
    protected String studentId;
    protected String name;
    protected int graduationYear;

    public Student04() {  //When you provide
        //default constructor: you get this by default
    }

    public Student04(String studentId, String name, int year) { //when you provide this constructor, doesn't create default constructor
        this.studentId = studentId;
        this.name = name;
        graduationYear = year;
    }

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
        Student04 abby = new Student04(); //calls first constructor
        Student04 alice = new Student04("f00xyz", "Alice", 2027); //calls second constructor
        System.out.println("ID: " + abby.getId() +//same as doing abby.getID()
                ", Name: " + abby.getName() +
                ", Year: " + abby.getGraduationYear());
        System.out.println("ID: " + alice.studentId +
                ", Name: " + alice.name +
                ", Year: " + alice.graduationYear);
    }
}
