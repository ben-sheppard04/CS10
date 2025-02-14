/**
 * InternationalStudent - models one international student, inherits from Student class
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class InternationalStudent extends Student {
    String homeCountry;

    public InternationalStudent(String name, String Id) {
        super(name, Id);
        homeCountry = null;
    }

    public InternationalStudent(String name, String Id, String homeCountry) {
        super(name, Id);
        this.homeCountry = homeCountry;
    }


    /**
     * Getter and setter
     */
    public String getHomeCountry() { return homeCountry; }
    public void setHomeCountry(String homeCountry) { this.homeCountry = homeCountry;}

    /**
     * Return a String representation of one international student.  Use student toString then add visa number
     * @return - string representing the international student
     */
    @Override
    public String toString() {
        return super.toString() + "\n\tHome country: " + homeCountry;
    }
}
