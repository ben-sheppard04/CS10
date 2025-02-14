import java.awt.*;

/**
 * Turn images from web cam blue by brightening blue color (1.5X blue) and dimming red and
 * green (0.5X red and green) for each pixel, every time the camera takes an image.
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024, inspired by prior term's WebcamProcessing
 */

public class VideoProcessing extends VideoGUI {
    /**
     * Dims/brightens the current image by scaling each pixel value by the specified amount.
     * @param scaleR/B/G		how much to scale the color component values
     */
    public void scaleColor(double scaleR, double scaleG, double scaleB) {
        //safety check
        if (image == null) { return; }

        // Nested loop over every pixel
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                // Get current color; scale each channel (but don't exceed 255); put new color
                Color color = new Color(image.getRGB(x, y));
                int red = (int)(Math.min(255, color.getRed()*scaleR));
                int green = (int)(Math.min(255, color.getGreen()*scaleG));
                int blue = (int)(Math.min(255, color.getBlue()*scaleB));//makes sure you don't go over 255
                Color newColor = new Color(red, green, blue);
                image.setRGB(x, y, newColor.getRGB());
            }
        }
    }


    /**
     * Called back when the camera takes an image.  Latest camera image is stored in image instance variable
     * Highlight blue color by scaling color (above), then set image1 on ImageGUI
     */
    @Override
    public void handleImage() {
        scaleColor(0.5, 0.5, 1.5);
        setImage1(image);
    }
    public static void main(String[] args) {
        new VideoProcessing();
    }
}
