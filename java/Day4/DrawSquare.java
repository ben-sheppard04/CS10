import java.awt.*;
import java.awt.image.BufferedImage;

/**
* Demonstrates operating around a pixel, draws a square of squareColor around cx,cy, with "radius" r
* where radius extends r rows above to r row below, r cols left to r cols right.
*
* @author Tim Pierson, Dartmouth CS10, Winter 2024, based on code from prior terms
*/

public class DrawSquare {

    public static void main(String[] args) {
        int cx = 100, cy= 100; //center x and y coordinates to draw a square around
        int r = 5; //"radius" of the square; r rows above to r row below, r cols left to r cols right
        Color squareColor = new Color(255, 0, 0); //color for square, red here

        //load image
        BufferedImage image = ImageIOLibrary.loadImage("pictures/baker.png");

        // Nested loop over nearby pixels. make sure not to go outside image boundaries
        for (int y = Math.max(0, cy-r); y < Math.min(image.getHeight(), cy+r+1); y++) {
            for (int x = Math.max(0, cx-r); x < Math.min(image.getWidth(), cx+r+1); x++) {
                image.setRGB(x, y, squareColor.getRGB());
            }
        }

        ImageGUI gui = new ImageGUI("Image with square", image);
    }

}
