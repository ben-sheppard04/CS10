
/**
 * Uses a two-dimensional array of size m rows and n columns to represent the probability that John
 * Alderidge, a fisherman who has fallen overboard, is located in each particular location in the grid.
 *
 * @author Ben Sheppard
 */
public class SearchAndRescue {
    protected int numberOfRows;
    protected int numberOfCols;
    protected double[][] gridArray;
    public SearchAndRescue(int m, int n){
        this.numberOfRows = m;
        this.numberOfCols = n;
        gridArray = new double[m][n];
        for(int r = 0; r < m; r++){
            for(int c = 0; c < n; c++){
                gridArray[r][c] = 1 / (double)(m * n);
            }
        }
    }

    /**
     * The method setGridProbability uses three parameters, row, col and probability to set the probability in the 2D
     * array at a given row and column. The method checks whether the specified row and column are within the accepted
     * range of the 2D array: that is, they are positive and less than the length of array. If the specified position
     * falls outside of this range, it prints an error message saying that the row or column is invalid. Also, the
     * method checks if the probability parameter falls between 0 and 1, inclusive, as that is the requirement for a
     * probability value. If not, the method will print an error message that the probability is invalid.
     *
     * @author Ben Sheppard
     * @param row The specified row that the probability will be updated at.
     * @param col The specified column that the probability will be updated at.
     * @param probability The specified probability that will be updated at position row, col.
     */
    public void setGridProbability(int row, int col, double probability){
        if(row >= 0 && row < numberOfRows && col >= 0 && col < numberOfCols){
            if(probability >= 0 && probability <= 1)
                gridArray[row][col] = probability;
            else
                System.out.println("The probability is invalid.");
        }
        else {
            System.out.println("The row or column is invalid.");
            if (probability < 0 || probability > 1)
                System.out.println("The probability is invalid");
        }
    }

    /**
     * The method bestLocation iterates through the 2D array to find the position (row and column) which contains the
     * greatest probability of containing Alderidge. If two positions contain the same probability, the code provides
     * the position that was first decided on while iterating.
     *
     * @author Ben Sheppard
     */
    public void bestLocation(){
        int highestRow = 0;
        int highestCol = 0;
        double highestProb = 0.0;
        for(int r = 0; r < numberOfRows; r++){
            for(int c = 0; c < numberOfCols; c++){
                if(gridArray[r][c] > highestProb){
                    highestRow = r;
                    highestCol = c;
                    highestProb = gridArray[r][c];
                }
            }
        }
        System.out.println("The coordinate the Coast Guard should send its helicopters to is (Row: "
        + highestRow + ", Column: " + highestCol + ").");
    }
}
