/**
 * Attendee models a person who is attending the reunion. Each attendee has a name and data that represents
 * if they have RSVP'd or not.
 *
 * @author Ben Sheppard
 */
public class Attendee {
    protected String personName;
    protected boolean rsvp;
    public Attendee(String personName, boolean rsvp){
        this.personName = personName;
        this.rsvp = rsvp;
    }

    /**
     * Method returns an attendee's name.
     * @return personName
     * @author Ben Sheppard
     */
    public String getName() {
        return personName;
    }

    /**
     * Method returns true if the attendee has RSVP'd and false if they have not.
     * @return rsvp
     * @author Ben Sheppard
     */
    public boolean getRsvp(){
        return rsvp;
    }

    /**
     * Method sets an attendee's name to the parameter newName.
     * @param newName New name for attendee.
     * @author Ben Sheppard
     */
    public void setName(String newName){
        personName = newName;
    }

    /**
     * Method sets attendee's RSVP status to the parameter newRsvp.
     * @param newRsvp New boolean value for whether attendee has RSVP'd.
     * @author Ben Sheppard
     */
    public void setRsvp(boolean newRsvp){
        rsvp = newRsvp;
    }
    public String toString(){
        return personName;
    }
}
