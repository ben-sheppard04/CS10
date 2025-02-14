/**
 * Demo to show dynamic dispatch where Instructor is a subclass of Person
 * Calling setName on an Instructor calls setName on Person because Instructor
 * does not provide a setName method (but it's base class does!)
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class DynamicDispatchExample {
    public static void main(String[] args) {
        Person alice = new Person("Alice", "f00xzy");
        Instructor bob = new Instructor("Bob","f00abc");
        System.out.println(alice);
        bob.setName("Bobby");
        System.out.println(bob);
    }

}
