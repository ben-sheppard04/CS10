/**
 * Student series demonstrates encapsulation by representing a student in a class
 * Student06 - adds toString method to return a String representation of a Student
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class Student06 {
    protected String studentId;
    protected String name;
    protected int graduationYear;
    double studyHours;
    double classHours;

    public Student06() {
        //default constructor: you get this by default
    }

    public Student06(String studentId, String name, int year) {
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
        System.out.println("Hi Mom!  It's " + name + ". I'm studying!");
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

    /**
     * Return a String representation of a student
     * @return - string representing the student
     */
    public String toString() {
        String s = "Student ID: " + studentId + ", name: " + name + ", graduation year: " + graduationYear + "\n";
        s += "\tHours studying: " + studyHours + "\n";
        s += "\tHours in class: " + classHours;
        return s;
    }

    public static void main(String[] args) {
        Student06 abby = new Student06(); //calls first constructor, default instance variables
        Student06 alice = new Student06("f00xyz", "Alice", 2027); //calls second constructor
        System.out.println(abby);
        alice.study(1.5);
        alice.attendClass(1.1);
        System.out.println(alice);
    }
}
