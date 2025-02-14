/**
 * Instructor - models one instructor, inherits from Person class
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class Instructor extends Person { //extend keyword tells Java that it inherits IV and methods.
    boolean tenured;
    int yearsEmployed;
    String department;

    /**
     * Constructors
     */
    public Instructor(String name, String Id) { //these are OVERLOADED (on test)
        super(name, Id); //runs constructor of parent class.
        this.tenured = false;   //not required, Java initializes boolean instance variables to false
        this.yearsEmployed = 0; //not required, Java initializes numeric values instance variables to 0
        this.department = null;  //not required, Java initializes objects to null
    }
    public Instructor(String name, String Id, boolean tenured, int yearsEmployed, String department) {
        super(name, Id);
        this.tenured = tenured;
        this.yearsEmployed = yearsEmployed;
        this.department = department;
    }


    /**
     * Getters
     */
    public boolean getTenuredStatus() { return tenured;} //base class does not have these getters/setters
    public int getYearsEmployed() { return yearsEmployed;}
    public String getDepartment() { return department; }

    /**
     * Setters
     */
    public void setTenured(boolean tenured) { this.tenured = tenured; }
    public void setYearsEmployed(int yearsEmployed) { this.yearsEmployed = yearsEmployed; }
    public void setDepartment(String department) { this.department = department;}

    /**
     * Return a String representation of an instructor
     * @return - string representing the instructor
     */
    @Override //this "decorator": Not required, but saying to Java "I intend to override base class." Useful in case you
    //make a mistake and wrote toSTring instead of toString, for example. Good habit.
    public String toString() { //subclasses can change the behavior of methods defined in base class "OVERRIDING"
        String s = super.toString() + "\n"; //calling super.toString() calls the method in the class above (or as far up as necessary)
        //if you left off the super, it will call it recursively and call its own method.
        s += "\tTenured: " + tenured + "\n";
        s += "\tYears Employed: " + yearsEmployed + "\n";
        s += "\tDepartment: " + department;
        return s;
    }
}
