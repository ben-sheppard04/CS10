/**
 * Person - models one person
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class Person {
    String name;
    String Id;

    /**
     * Constructor
     * @param Id
     * @param name
     */
    public Person(String name, String Id) {
        this.name = name;
        this.Id = Id;
    }

    /**
     * Getters
     */
    public String getName() { return name; }
    public String getId() { return Id; }

    /**
     * Setters
     */
    public void setName(String name) {this.name = name; }
    public void setId(String Id) { this.Id = Id;}

    /**
     * Return  type of person.  Demonstrates instanceof
     * @return String with the person's position
     */
    public String getPosition() {
        if (this instanceof InternationalGraduateStudent) {
            return "International grad student";
        }
        else if (this instanceof GraduateStudent) {
            return "Domestic grad student";
        }
        else if (this instanceof InternationalStudent) {
            return "International undergraduate student";
        }
        else if (this instanceof Student) {
            return "Domestic undergraduate student";
        }
        else if (this instanceof Instructor) {
            return "Instructor";
        }
        else {
            return "Unkown";
        }
    }

    /**
     * Comare two Person objects and decide if they are the same.  Use Id to decide
     * @param other compare this person's Id
     * @return true if Ids are the same, false otherwise
     */
    public boolean equals(Person other) {
        return Id.equals(other.Id);
//        if (Id.length() != other.Id.length()) { DONE BY String Class automatically
//            return false;
//        }
//        for (int i = 0; i < Id.length(); i++) {
//            if (Id.charAt(i) != other.Id.charAt(i)) {
//                return false;
//            }
//        }
//        return true;
    }

    /**
     * Returns a String representation of a Person
     * @return String
     */
    public String toString() { //need it so that you can give string representation of object
        String s = "Name: " + name + " (" + Id + ")";
        return s; //it returns, doesn't print
    }

}
