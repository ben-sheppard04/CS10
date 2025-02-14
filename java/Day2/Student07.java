/**
 * Student series demonstrates encapsulation by representing a student in a class
 * Student07 - removes main method, this class is intended to be used by application programs
 * This class is not intended to run by itself
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class Student07 {
    protected String studentId;
    protected String name;
    protected int graduationYear;
    double studyHours;
    double classHours;

    public Student07() {
        //default constructor: you get this by default
    }

    public Student07(String studentId, String name, int year) {
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
}
