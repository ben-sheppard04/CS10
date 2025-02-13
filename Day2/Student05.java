/**
 * Student series demonstrates encapsulation by representing a student in a class
 * Student05 - adds instance variables to track hours spent studying and in class
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class Student05 {
    protected String studentId;
    protected String name;
    protected int graduationYear;
    double studyHours;
    double classHours;

    public Student05() {
        //default constructor: you get this by default
    }

    public Student05(String studentId, String name, int year) {
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
    public double getStudyHours() { return studyHours; }
    public double getClassHours() { return classHours; }


    /**
     * adds hoursSpent to the hoursSpentStudying to track time this student spent studying
     * @param hoursSpent - number of hours spent studying (can have decimal component)
     * @return - total number of hours spent studying including the new hours passed in
     */
    public double study(double hoursSpent) {
        System.out.println("Hi Mom! It's " + name + ". I'm studying!");
        studyHours += hoursSpent;
        return studyHours;
    }

    /**
     * adds hoursSpent to the hoursSpentInClass to track time this student spent in class
     * @param hoursSpent - number of hours spent in class (can have decimal component)
     * @return - total number of hours spent in class including the new hours passed in
     */
    public double attendClass(double hoursSpent) {
        System.out.println("Hi Dad! It's " + name +". I'm in class!");
        classHours += hoursSpent;
        return classHours;
    }

    public static void main(String[] args) {
        Student05 abby = new Student05(); //calls first constructor, default instance variables
        Student05 alice = new Student05("f00xyz", "Alice", 2027); //calls second constructor
        alice.study(1.5);
        alice.attendClass(1.1);
    }
}
