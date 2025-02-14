/**
 * Extends the Attendee class for graduates of Dartmouth. Graduates have names and rsvp data as well as a graduationYear
 * and department.
 * @author Ben Sheppard
 */
public class Graduate extends Attendee{
    protected Integer graduationYear;
    protected String department;
    public Graduate(String personName, boolean rsvp, Integer graduationYear, String department){
        super(personName, rsvp);
        this.graduationYear = graduationYear;
        this.department = department;
    }

    /**
     * Returns a string representation of the Graduate object including name, whether they have RSVP'd, their graduation
     * year, and department.
     * @return String representation of Graduate object.
     * @author Ben Sheppard
     */
    @Override
    public String toString(){
        return super.toString() + " '" + graduationYear + " " + department;
    }


}
