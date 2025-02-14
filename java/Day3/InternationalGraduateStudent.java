/**
 * InternationalGraduateStudent - models one student who is both an international student and a grad student
 * In Java we can only inherit from one base class.  We could choose either GraduateStudent or InternationalStudent
 * Because GraduateStudent has more code, here we choose to inherit from GraduateStudent.  We will, however,
 * need to duplicate the code from International student, adding a Visa Number and duplicating code in toString
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class InternationalGraduateStudent extends GraduateStudent {
    String homeCountry;

    /**
     * Constructors
     * @param name - person's name
     * @param Id - person's Id number
     */
    public InternationalGraduateStudent(String name, String Id) {
        super(name, Id);
        homeCountry = null;
    }

    public InternationalGraduateStudent(String name, String Id, String homeCountry) {
        super(name, Id);
        this.homeCountry = homeCountry;
    }

    public InternationalGraduateStudent(String name, String Id, String department, String advisorName, String homeCountry) {
        super(name, Id, department, advisorName);
        this.homeCountry = homeCountry;
    }


    /**
     * Getter and setter
     */
    public String getHomeCountry() { return homeCountry; }
    public void setHomeCountry(String homeCountry) { this.homeCountry = homeCountry; }

    /**
     * Return a String representation of one international grad student.  Add visa number to graduate student
     * @return - string representing the international grad student
     */
    @Override
    public String toString() {
        return super.toString() + "\n\tHome country: " + homeCountry;
    }
}
