/**
 * Student - models one undergraduate Student
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class Student extends Person {
    protected Integer graduationYear; //using Integer (object/autoboxed version) so you can set it to null instead of 0
    //this is because having a value of 0 means something in terms of date. Null has no meaning
    double studyHours;
    double classHours;

    /**
     * Constructor
     * @param name - person's name
     * @param Id - person's ID number
     */
    public Student(String name, String Id) {
        super(name, Id);
        graduationYear = null; //Don't need these lines.
        studyHours = 0;
        classHours = 0;
    }

    public Student(String name, String Id, int year) {
        super(name, Id);
        graduationYear = year;
        studyHours = 0;
        classHours = 0;
    }

    /**
     * Setters for instance variables
     */
    public void setId(String Id) { this.Id = Id; }
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
    public String getId() { return Id; }
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
    @Override
    public String toString() {
        String s = super.toString() + "\n";
        s += "\tGraduation year: " + graduationYear + "\n";
        s += "\tHours studying: " + studyHours + "\n";
        s += "\tHours in class: " + classHours;
        return s;
    }
}
