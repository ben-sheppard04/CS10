import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Fun with the webcam, built on JavaCV
 * Replaces background (as denoted by mouse press) with scenery
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024, based on WebcamBg from prior terms
 * 
 * Usage: after camera image appears, click mouse with only the background visible (e.g., you are not visible),
 * this will set the background to be removed, then move into the frame.  The background should be replaced with
 * Baker tower and the previous background subtracted from the current image.
 */

public class WebcamBg extends VideoGUI {
	private static final int backgroundDiff=250;	// setup: threshold for considering a pixel to be background

	private BufferedImage background;		// the stored background grabbed from the webcam
	private BufferedImage scenery;			// the replacement background (e.g., Baker)
	
	public WebcamBg(BufferedImage scenery) {
		this.scenery = scenery;
	}

	/**
	 * VideoGUI method, here setting background as a copy of the current image.
	 */
	@Override
	public void handleMousePress(int x, int y) {
		if (image != null) {
			//save background image that we will subtract out
			background = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
			System.out.println("background set");
		}
	}

	/**
	 * Webcam method, here subtracting background.
	 * Updates image so that pixels that look like background are replaced with scenery.
	 */
	@Override
	public void handleImage() {
		if (background != null) {
			// Nested loop over every pixel
			for (int y = 0; y < Math.min(image.getHeight(), scenery.getHeight()); y++) {
				for (int x = 0; x < Math.min(image.getWidth(), scenery.getWidth()); x++) {
					// Euclidean distance squared between colors
					Color c1 = new Color(image.getRGB(x,y));
					Color c2 = new Color(background.getRGB(x,y));
					int d = (c1.getRed() - c2.getRed()) * (c1.getRed() - c2.getRed())
							+ (c1.getGreen() - c2.getGreen()) * (c1.getGreen() - c2.getGreen())
							+ (c1.getBlue() - c2.getBlue()) * (c1.getBlue() - c2.getBlue());
					//check if distance less than threshold to replace image with scenery, otherwise, keep image
					if (d < backgroundDiff) { //uses threshold
						// Close enough to background, so replace
						image.setRGB(x,y,scenery.getRGB(x,y));
					}
				}
			}
		}
		//update image on screen
		setImage1(image);
	}

	public static void main(String[] args) {
		BufferedImage image = ImageIOLibrary.loadImage("Pictures/baker-960-720.jpeg");
		new WebcamBg(image);
	}
}
