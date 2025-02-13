import java.awt.*;
import java.awt.image.*;
import java.util.*;

/**
 * Code provided for PS-1
 * Region growing algorithm: finds and holds regions in an image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Dartmouth CS 10, Winter 2024
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024, based on prior terms RegionFinder
 * @author Tara Salli
 * @author Ben Sheppard
 */
public class RegionFinderEC1 {
    private static int variableColorDiff;				// how similar a pixel color must be to the target color, to belong to a region
    private static final int minRegion = 50; 				// how many points in a region to be worth considering

    private BufferedImage image;                            // the image in which to find regions
    private BufferedImage recoloredImage;                   // the image with identified regions recolored

    private ArrayList<ArrayList<Point>> regions;			// a region is a list of points
    // so the identified regions are in a list of lists of points

    public RegionFinderEC1() {
        this.image = null; //set image to null if no parameter in constructor
        variableColorDiff = 20;
    }

    public RegionFinderEC1(BufferedImage image) {
        this.image = image;	//set image instance variable to parameter image
        variableColorDiff = 20;
    }

    /**
     * Setter method that sets the variableColorDifference to the parameter sensitivity
     * @param sensitivity Desired sensitivity for variableColorDiff.
     */
    public static void setVariableColorDiff(int sensitivity){
        variableColorDiff = sensitivity;
    }

    /**
     * Setter method that sets the image instance variable to the parameter image.
     * @param image BufferedImage object to be assigned to image instance variable
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Getter method which returns the BufferedImage stored in image instance variable.
     * @return BufferedImage image instance variable
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Getter method which returns the BufferedImage stored in recoloredImage instance variable. The recolored image is the
     * image with the flood fill algorithm applied.
     * @return BufferedImage recoloredImage instance variable
     */
    public BufferedImage getRecoloredImage() {
        return recoloredImage;
    }

    /**
     * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
     * @param targetColor Target color that the flood-fill algorithm will search for in regions greater than or equal to minRegion
     */
    public void findRegions(Color targetColor) {
        regions = new ArrayList<ArrayList<Point>>(); //Initializes the regions instance variable.
        BufferedImage visited = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB); //Creates a new image called visited of identical dimensions to image. Stores which pixels we have visited with a RGB color of 0 being unvisited, and RGB color of 1 being visited.

        for (int c = 0; c < image.getWidth(); c++) { //Loops over the columns of the pixels in image
            for (int r = 0; r < image.getHeight(); r++) { //Loops over the rows of the pixels in image
                if (visited.getRGB(c, r) == 0) { //Check if pixel at c, r is unvisited.
                    if (colorMatch(targetColor, new Color(image.getRGB(c, r)))) { //Checks if targetColor and pixel color of image are sufficiently close.
                        ArrayList<Point> region = new ArrayList<Point>(); //Region of neighboring pixels that are all sufficiently close in color to targetColor.
                        ArrayList<Point> toVisit = new ArrayList<Point>(); //Represents pixels that we need to visit to check if they are sufficiently close in color to targetColor
                        toVisit.add(new Point(c, r)); //Adds the current pixel as a Point object to toVisit because it was determined to be close in color in if-statement.
                        while (!toVisit.isEmpty()) { //Loops through toVisit while it has a Point that needs to be visited
                            Point currentPoint = toVisit.get(0); //Current point that is being the seed pixel--all of its neighbors will be visited.
                            region.add(currentPoint); //Adds the current point to the region ArrayList
                            int xCord = (int) currentPoint.getX(), yCord = (int) currentPoint.getY(); //Creates int variables to represent x and y coordinates of the currentPoint
                            visited.setRGB(xCord, yCord, 1); //Updates visited to represent that we have visited the current point
                            toVisit.remove(0); //Removes the current point -- point at index 0 -- from toVisit
                            for (int neighborCol = xCord - 1; neighborCol <= xCord + 1; neighborCol++) { //Loops from the column to left to the column to the right, all-inclusive, of the xCord (from the starting pixel)
                                for (int neighborRow = yCord - 1; neighborRow <= yCord + 1; neighborRow++) { //Loops from the row above to the row to the below, all-inclusive, of the yCord (from the starting pixel)
                                    if (neighborCol != xCord || neighborRow != yCord) { //Ensures that you don't include the starting pixel while iterating.
                                        if (neighborCol >= 0 && neighborCol < image.getWidth() && neighborRow >= 0 && neighborRow < image.getHeight()) { //Checks to ensure that the pixel defined by neighborCol, neighborRow is within the bounds of the image.
                                            if (visited.getRGB(neighborCol, neighborRow) == 0 && colorMatch(targetColor, new Color(image.getRGB(neighborCol, neighborRow)))) { //Executes if the pixel is unvisited and is sufficiently close in color to the targetColor
                                                toVisit.add(new Point(neighborCol, neighborRow)); //Adds that pixel as a Point object to the toVisit array so that you can visit it in the next iteration of the while loop.
                                                visited.setRGB(neighborCol, neighborRow, 1); // Mark neighbor as visited to ensure it never gets added to toVisit again.
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (region.size() > minRegion) { // Add region to regions list if it's length and therefore the size of the region is above the threshold
                            regions.add(region); //Adds region to regions.
                        }
                    }
                }
            }
        }
    }
    /**
     * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
     * @param c1 First color to be compared.
     * @param c2 Second color, which will be compared to the first color (c1).
     * @return Returns boolean which represents if the two Color objects are similar enough to within maxColorDiff.
     */
    protected static boolean colorMatch (Color c1, Color c2){
        int redC1 = c1.getRed(); //Gets red component of Color c1
        int greenC1 = c1.getGreen(); //Gets green component of Color c1
        int blueC1 = c1.getBlue(); //Gets blue component of Color c1
        int redC2 = c2.getRed(); //Gets red component of Color c2
        int greenC2 = c2.getGreen(); //Gets green component of Color c2
        int blueC2 = c2.getBlue(); //Gets blue component of Color c2

        if (Math.abs(redC1 - redC2) > variableColorDiff) { //Checks if the two red components are similar enough to within maxColorDiff
            return false; //returns false if they are not sufficiently close
        }
        if (Math.abs(greenC1 - greenC2) > variableColorDiff) { //Checks if the two green components are similar enough to within maxColorDiff
            return false; //returns false if they are not sufficiently close
        }
        return Math.abs(blueC1 - blueC2) <= variableColorDiff; //Checks if the two blue components are similar enough to within maxColorDiff, returns true if they are.
    }

    /**
     * Returns the largest region detected (if any region has been detected)
     *
     * @return ArrayList representing the pixels that make up the largest region in regions.
     */
    public ArrayList<Point> largestRegion () {
        int largestIndex = 0;
        int largestRegion = 0;

        if(!regions.isEmpty()) { //Checks that regions is not empty--if it were, there would be no largest region to return
            for (int i = 0; i < regions.size(); i++) { //Loops through regions
                if (regions.get(i).size() > largestRegion) { //Checks if size of this region is larger than the largestRegion
                    largestRegion = regions.get(i).size(); //Updates largest region to be this region.
                    largestIndex = i; //Updates index of largest region to be this index.
                }
            }
            return regions.get(largestIndex); //Returns the largest region
        }
        else{
            return null; //returns null in the case that regions is empty.
        }
    }

    /**
     * Sets recoloredImage to be a copy of image,
     * but with each region a uniform random color,
     * so we can see where they are
     */
    public void recolorImage () {
        recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null); //Initializes the recoloredImage instance variable as a copy of image.

        for (ArrayList<Point> r : regions) { //Loops through every region r in regions
            int randRed = (int) (Math.random() * 256); //Random int between 0 and 255, all-inclusive. Represents the random red component of color.
            int randGreen = (int) (Math.random() * 256); //Random int between 0 and 255, all-inclusive. Represents the random green component of color.
            int randBlue = (int) (Math.random() * 256);//Random int between 0 and 255, all-inclusive. Represents the random blue component of color.
            Color randColor = new Color(randRed, randGreen, randBlue); //Creates Color object with the random RGB components.
            for (Point p : r) { //Loops through every Point p in region r, representing a pixel in the region.
                int col = (int) p.getX(), row = (int) p.getY(); //Col and row variables to represent the x and y components of Point p.
                recoloredImage.setRGB(col, row, randColor.getRGB()); //Sets the color in recoloredImage at Point p to the random color.
            }
        }
    }
}