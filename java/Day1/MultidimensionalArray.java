/**
 * MultidimensionalArray - demonstrate multidimensional array using an array of quiz scores
 * Each row represents one student, each column represents that student's score on the quiz
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class MultidimensionalArray {
    public static void main(String[] args) {
        int numberOfStudents = 10;
        int numberOfQuizes = 5;
        double scores[][] = new double[numberOfStudents][numberOfQuizes];

        //set score for student 3 on quiz 2
        scores[2][1] = 9.2; //remember zero-indexing!

        //print all scores
        int quiz;
        for (int student = 0; student < numberOfStudents; student++) {
            for (quiz = 0; quiz < numberOfQuizes-1; quiz++) {
                System.out.print(scores[student][quiz] + ", ");
            }
            System.out.println(scores[student][quiz]);
        }
    }
}
