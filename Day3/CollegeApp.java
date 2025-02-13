/**
 * Demo to show an array can hold items of a base class and subclasses
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class CollegeApp {
    public static void main(String[] args) {
        //define some people
        int numberOfPeople = 5;
        Person[] people = new Person[numberOfPeople];
        Instructor tjp = new Instructor("Tim Pierson", "f00zzz");
        tjp.setDepartment("Computer Science");
        people[0] = tjp;
        people[1] = new Student("Alice", "f00xyz");
        people[2] = new GraduateStudent("Bob", "f00abc", "Computer Science", "Tim Pierson");
        ((Student)people[2]).graduationYear = 2028; //must cast to student to access that functionality.
        people[3] = new InternationalStudent("Charlie", "f00123","Germany");
        people[4] = new InternationalGraduateStudent("Denise", "f00987");
        ((InternationalGraduateStudent)people[4]).setDepartment("Computer Science");
        ((InternationalGraduateStudent)people[4]).setAdvisorName("Alan Turing"); //works if cast to GraduateStudent too
        ((InternationalGraduateStudent)people[4]).setHomeCountry("Spain");

        //print all people
        for (Person p: people) {
            System.out.println(p);
        }
    }
}
