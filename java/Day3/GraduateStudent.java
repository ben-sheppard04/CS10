/**
 * GraduateStudent - models one graduate student, inherits from Student class
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class GraduateStudent extends Student {
    String department;
    String advisorName;
    double labHours;

    public GraduateStudent(String name, String Id) {
        super(name, Id);
        this.department = null;
        this.advisorName = null;
        labHours = 0;
    }

    public GraduateStudent(String name, String Id, String department, String advisorName) {
        super(name, Id);
        this.department = department;
        this.advisorName = advisorName;
        labHours = 0;
    }

    /**
     * Getters
     */
    public String getDepartment() { return department; }
    public String getAdvisorName() { return advisorName; }
    public double getLabHours() { return labHours; }

    /**
     * Setters
     */
    public void setDepartment(String department) { this.department = department; }
    public void setAdvisorName(String advisorName) { this.advisorName = advisorName; }
    public void setLabHours(double hours) { labHours = hours; }

    /**
     * adds hours parmeters to the hoursSpentInLab to track time this student spent in the lab
     * @param hours - number of hours spent in the lab (can have decimal component)
     * @return - total number of hours spent in the lab including the new hours passed in
     */
    public double experiment(double hours) {
        if (advisorName != null) {
            System.out.println("Hello, " + advisorName + ". It's " + name + ".  I've spent " + hours + " in the lab");
        }
        else {
            System.out.println("Hello, it's " + name + ".  I've spent " + hours + " in the lab");
        }
        labHours += hours;
        return labHours;
    }

    /**
     * Return a String representation of one grad student.  Use student toString then add hours in the lab.
     * @return - string representing the grad student
     */
    @Override
    public String toString() {
        String s =  super.toString() + "\n";
        s += "\tHours in the lab: " + labHours + "\n";
        s += "\tDepartment: " + department + "\n";
        s += "\tAdvisor: " + advisorName;
        return s;
    }
}
