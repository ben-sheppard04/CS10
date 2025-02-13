import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Demonstrate using ImageIOLibrary loadImage and ImageGUI. Displays Baker tower and a black and white version side by side.
 * Use loadImage to read a graphic file (e.g., png, jpg) into memory stored in a BufferedImage
 * Use ImageGUI to display the image
 *      pass one image to the contructor to see just that image
 *      pass two imges to the constructor to see both images side by side (image1 then image2)
 *      can change images using setImage1 or setImage2
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class ShowImages {
    public static void main(String[] args) {
        //load two images
        BufferedImage baker = ImageIOLibrary.loadImage("pictures/baker.png");
        BufferedImage bakerbw = ImageIOLibrary.loadImage("pictures/baker-black-and-white.png");

        //create an ImageGUI object
        //we could pass both images in the constructor (and normally would)
        //here we demonstrate that we can add image2 after the constructor runs by using setImage2
        ImageGUI gui = new ImageGUI("Show images", baker);
        gui.setImage2("pictures/baker-black-and-white.png"); //can add a second image if desired

    }
}
