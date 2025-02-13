import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.plaf.synth.Region;

/**
 * Code provided for PS-1
 * Webcam-based drawing
 * Dartmouth CS 10, Winter 2024
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024 (based on CamPaint from previous terms)
 * @author Tara Salli
 * @author Ben Sheppard
 */
public class CamPaint extends VideoGUI {
	private char displayMode = 'w';			// what to display: 'w': live webcam, 'r': recolored image, 'p': painting
	private RegionFinder finder;			// handles the finding
	private Color targetColor;          	// color of regions of interest (set by mouse press)
	private Color paintColor = Color.blue;	// the color to put into the painting from the "brush"
	private BufferedImage painting;			// the resulting masterpiece

	/**
	 * Initializes the region finder and the drawing
	 */
	public CamPaint() {
		finder = new RegionFinder(); //Creates new RegionFinder upon creation of CamPaint object.
		clearPainting(); //Clears painting so that initially painting is equal to a blank picture of the correct dimensions.
	}

	/**
	 * Resets the painting to a blank image
	 */
	protected void clearPainting() {
		painting = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	} //Clears painting by making new BufferedImage of correct width and height (instance variables in VideoGUI class)

	/**
	 * VideoGUI method, here drawing one of live webcam, recolored image, or painting,
	 * depending on display variable ('w', 'r', or 'p')
	 */
	@Override
	public void handleImage() {
		if (displayMode == 'w') { //If in webcam display mode
			setImage1(image); //Sets frame to  the unaltered image from camera.
		}
		else if (displayMode == 'r') { //If in recolor display mode
			if(targetColor != null) { //Checks that targetColor has a value from user clicking mouse.
				finder.setImage(image); //Sets image in finder object to the image from camera.
				finder.findRegions(targetColor); //Finds regions sufficiently close to target color.
				finder.recolorImage(); //Recolors the regions in the new BufferedImage instance variable "recoloredImage."
				setImage1(finder.getRecoloredImage()); //Sets frame to the recoloredImage instance variable from finder.
			}
			else { //If targetColor is null (user has not clicked)
				setImage1(image); //Set frame to the unaltered image from camera.
			}
		}
		else if (displayMode == 'p') { //If in painting display mode
			if(targetColor != null){ //Checks that targetColor has a value from user clicking mouse.
				finder.setImage(image); //Sets image in finder object to the image from camera.
				finder.findRegions(targetColor); //Finds regions sufficiently close to target color.
				finder.recolorImage(); //Recolors the regions in the new BufferedImage instance variable "recoloredImage."

				if(finder.largestRegion() != null){ // Checks that largestRegion() returned a region--only possible if a region exists in regions.
					ArrayList<Point> paintbrush = finder.largestRegion(); //Creates new ArrayList of Point objects representing the paintbrush--pixels making up largest region
					for(Point brushPixel : paintbrush){ // Iterates over every pixel in paintbrush
						painting.setRGB((int)brushPixel.getX(), (int)brushPixel.getY(), paintColor.getRGB()); //Set pixel in painting to the paintColor--blue.
					}
					setImage1(painting); //Sets frame to the painting.
				}
				else { //If largestRegion() returned null--regions is empty
					setImage1(painting);
				}
			}
			else { //If targetColor is null--user has not clicked yet
				setImage1(painting); //Sets frame to painting.
			}
		}

	}

	/**
	 * Overrides the Webcam method to set the track color.
	 */
	@Override
	public void handleMousePress(int x, int y) {
		targetColor = new Color (image.getRGB(x, y)); //Sets the targetColor to be a new color object of the color at x, y.
	}

	/**
	 * Webcam method, here doing various drawing commands
	 */
	@Override
	public void handleKeyPress(char k) {
		if (k == 'p' || k == 'r' || k == 'w') { // display: painting, recolored image, or webcam
			displayMode = k;
		}
		else if (k == 'c') { // clear
			clearPainting();
		}
		else if (k == 'o') { // save the recolored image
			ImageIOLibrary.saveImage(finder.getRecoloredImage(), "pictures/recolored.png", "png");
		}
		else if (k == 's') { // save the painting
			ImageIOLibrary.saveImage(painting, "pictures/painting.png", "png");
		}
		else {
			System.out.println("unexpected key "+k);
		}
	}

	public static void main(String[] args) {
		new CamPaint(); //Starts program with instantiating a new CamPaint object.
	}
}