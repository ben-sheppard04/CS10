/**
 * MultipleVariablesArray - demonstrate using an array to hold multiple quiz scores
 * Shows that printing an array prints a value based on the starting memory address of the array
 * Also demonstrates a C-style for loop to print each array element
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class MultipleVariablesArray {
    public static void main(String[] args) {
        int numberOfScores = 5;
        double[] scores = new double[numberOfScores]; //store quiz scores
        scores[0] = 10;  //zero indexed in Java
        scores[1] = 3.2;
        scores[2] = 6.5;
        scores[3] = 7.8;
        scores[4] = 8.8;  //valid indices are 0..4
        //scores[5] = 9;    //error, index out of bounds!
        System.out.println(scores);

        System.out.print("[");
        for (int i= 0; i < numberOfScores-1; i++) {
            System.out.print(scores[i] + ", ");
        }
        System.out.println(scores[numberOfScores-1] + "]");
    }
}
