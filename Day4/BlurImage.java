import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Blurs each pixel in an image by averaging each pixel's color with its neighbors with "radius" r
 * where radius extends r rows above to r row below, r cols left to r cols right.
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024, based on code from prior terms
 */

public class BlurImage {
    public static void main(String[] args) {
        int radius = 1; //average r row above to r rows below, r cols left to r cols right

        //load image and create a blank image called result
        BufferedImage image = ImageIOLibrary.loadImage("pictures/baker.png");
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        //IMPORTANT: You have to create a new object called result because otherwise, you will blur by calculating
        // average based off of the picture which has been blurred already.
        // Nested loop over every pixel in original image
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int sumR = 0, sumG = 0, sumB = 0;
                int n = 0;
                // Nested loop over neighbors
                // but be careful not to go outside image (max, min stuff).
                for (int ny = Math.max(0, y - radius); //could have done y - radius. However, the issue is that when you
                     //are on the edges, you wil lend up with negative. So you do the maximum of zero and y-radius. Therefore,
                     //on edge, it will just do 0 instead of
                     ny < Math.min(image.getHeight(), y + 1 + radius);//deals with case when it is on right side,
                     //You do y+1 because you do less than symbol
                     ny++) {
                    for (int nx = Math.max(0, x - radius);
                         nx < Math.min(image.getWidth(), x + 1 + radius);
                         nx++) {
                        // Add all the neighbors (& self) to the running totals
                        Color c = new Color(image.getRGB(nx, ny));
                        sumR += c.getRed();
                        sumG += c.getGreen();
                        sumB += c.getBlue();
                        n++;
                    }
                }
                Color newColor = new Color(sumR / n, sumG / n, sumB / n);
                result.setRGB(x, y, newColor.getRGB());
            }
        }

        //display images
        ImageGUI gui = new ImageGUI("Blurred image", image, result);
    }
}
