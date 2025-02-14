/**
 * Demonstrates comparing primitive data types and objects
 *
 * @author Tim Pierson, Dartmouth CS10 Winter 2024
 */
public class CompareTest {
    public static void main(String[] args) {
        int a = 7;
        int b = 5;
        System.out.println("Check primitive variables");
        System.out.println("a=" + a + " b=" + b + " same:" + (a==b));
        b = 7;
        System.out.println("a=" + a + " b=" + b + " same:" + (a==b));

        System.out.println("\nCheck object variables");
        Person alice = new Person("Alice","f00abc");
        Person ally = alice;
        System.out.println("alice == ally: " + (alice==ally));
        System.out.println("alice equals ally: " + alice.equals(ally));
        ally = new Person("Alice", "f00xyz");
        System.out.println("alice == ally: " + (alice==ally));
        System.out.println("alice equals ally: " + alice.equals(ally));
        ally.setId("f00abc");
        System.out.println("alice == ally: " + (alice==ally));
        System.out.println("alice equals ally: " + alice.equals(ally));

        //instanceof tests
        Person bob = new Instructor("Bob", "f00000");
        Person carol = new Student("Carol", "f11111");
        if (bob instanceof Instructor) {
            System.out.println("Bob is an instructor");
        }
        if (carol instanceof Instructor) {
            System.out.println("Carol is an instructor");
        }
    }
}
