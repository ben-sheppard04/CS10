public class StudentTrackerApp {
    public static void main(String[] args) {
        int numberOfStudents = 3;
        Student07[] students = new Student07[numberOfStudents];
        students[0] = new Student07("f00xyz", "Alice", 2027);
        students[1] = new Student07("f00123", "Bob", 2024);
        students[2] = new Student07("f00abc", "Charlie", 2025);

        //print students
        System.out.println("Before studying");
        for (Student07 student : students) {
            System.out.println(student);
        }

        //randomly select students to study to simulate an actual application
        for (int i = 0; i < 10; i++) {
            //pick random student
            int index = (int)(Math.random() * numberOfStudents);
            Student07 student07 = students[index];

            //add random studying time between 0 and 5 hours
            double time = Math.random() * 5;
            student07.study(time);
        }

        //print students
        System.out.println("After studying");
        for (Student07 student : students) {
            System.out.println(student);
        }
    }
}
