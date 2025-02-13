/**
 * Class contains a guestList array which is an array of people that will be taking part in the reunion. Contains
 * methods for adding guests the guestList, updating guests' RSVP status, and printing guests, sorted by RSVP status.
 *
 * @author Ben Sheppard
 */
public class Reunion {
    protected int maxAttendees;
    protected Attendee[] guestList;
    public Reunion(int maxAttendees){
        this.maxAttendees = maxAttendees;
        guestList = new Attendee[maxAttendees];
    }

    /**
     * Adds the myAttendee object to the guestList array if there is space. Prints who was added if this was successful.
     * Prints that the guestList was unable to accommodate another guest if the guestList is full.
     *
     * @param myAttendee The attendee object that is to be added to the guestList.
     * @author Ben Sheppard
     */
    public void addAttendee(Attendee myAttendee){
        if(guestList[maxAttendees - 1] == null){
            for(int i = 0; i < maxAttendees; i++){
                if(guestList[i] == null){
                    guestList[i] = myAttendee;
                    System.out.println("Added " + myAttendee.toString() + ", rsvp: " + myAttendee.getRsvp());
                    i = maxAttendees;
                }
            }
        }
        else{
            System.out.println("Unable to accommodate " + myAttendee.toString() + ", max attendees is " + maxAttendees);
        }
    }

    /**
     * Method updates the RSVP status of the guest who is named inputName to inputRsvp. If the guest is found, it prints
     * the name and updated status. If no guest of the inputName is found, it prints that the name is not found.
     *
     * @param inputName Name of guest whose RSVP status will be altered
     * @param inputRsvp Desired RSVP status of the inputName after method runs
     * @author Ben Sheppard
     */
    public void rsvp(String inputName, boolean inputRsvp){
        boolean tester = false;
        int location = 0;
        for(int i = 0; i < maxAttendees; i++){
            if(guestList[i].getName().equals(inputName)) {
                tester = true;
                location = i;
                i = maxAttendees;
            }
        }
        if(tester){
            guestList[location].setRsvp(inputRsvp);
            System.out.println("Set RSVP to " + inputRsvp + " for " + inputName);
        }
        else{
            System.out.println(inputName + " not found.");
        }
    }

    /**
     * Prints a list of guests who have and have not RSVP'd in the desired format
     *
     * @return A blank string (method is intended to print, not return, but cannot have void method that overrides the
     * non-void toString method of object class)
     *
     * @author Ben Sheppard
     */
    public String toString() {
        String haveRsvp = "Reunion attendees that have RSVP:\n";
        String noRsvp = "Reunion attendees that have NOT RSVP:\n";
        for(int i = 0; i < maxAttendees; i++){
            if(guestList[i].getRsvp()){
                haveRsvp += "\t" + guestList[i].toString() + "\n";
            }
            else{
                noRsvp += "\t" + guestList[i].toString() + "\n";
            }
        }
        return haveRsvp + noRsvp;
    }

    /**Short Assignment 2 (SA-2) provided code
     * Model attendees and graduates at a reunion.
     *
     * @author Tim Pierson, Dartmouth CS10, Winter 2024
     */
    public static void main(String[] args) {
        Reunion r = new Reunion(5);
        r.addAttendee(new Attendee("Alice", true));
        r.addAttendee(new Attendee("Bob", false));
        r.addAttendee(new Graduate("Charlie", true, 22, "Government"));
        r.addAttendee(new Graduate("Denise", false, 21, "Econ"));
        r.addAttendee(new Graduate("Elvis", true, 23, "Computer Science"));
        r.addAttendee(new Graduate("Falcon", false, 26, "Biology"));
        System.out.println(r);
        System.out.println();

        System.out.println("Update rsvp status");
        r.rsvp("George", false);  //print not found
        r.rsvp("Bob", true);  //update
        System.out.println(r);
    }
}

