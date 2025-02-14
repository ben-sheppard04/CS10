import java.awt.image.BufferedImage;
/**
 * Demonstrates reading an image, copying pixels from the original to a new image so that the new image
 * appears to fade in.  For fun (by some definition of fun), we only copy the pixel at each location one time.
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class FadeIn {
    public FadeIn(BufferedImage original) {
        //keep track of pixels that have been copied
        boolean[][] picked = new boolean[original.getWidth()][original.getHeight()];
        int remainingPixels = original.getHeight() * original.getWidth();

        //make blank image the same size as the original image
        BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);

        //set up gui
        ImageGUI gui = new ImageGUI("Fade In", result);

        //until all pixels copied
        while (remainingPixels > 0) {
            //pick random pixel
            int x = (int)(Math.random() * original.getWidth());
            int y = (int)(Math.random() * original.getHeight());

            //copy to result if this pixel has not already been copied
            if (!picked[x][y]) {
                //update result image
                result.setRGB(x, y, original.getRGB(x, y));
                gui.setImage1(result);

                //update tracking which pixels have not been transferred
                picked[x][y] = true;
                remainingPixels--;
            }
        }

    }
    public static void main(String[] args)  {
        BufferedImage image = ImageIOLibrary.loadImage("pictures/baker.png");
        new FadeIn(image);
    }
}
