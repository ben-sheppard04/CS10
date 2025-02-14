import java.awt.*;

/**
 * Point class with a compareTo method where Points higher on the window are bigger
 * 
 * @author Tim Pierson, Dartmouth CS 10, Winter 2024
 */

public class PointWithCompareTo extends Point implements Comparable<PointWithCompareTo>{
	public PointWithCompareTo() {
		super();
	}
	public PointWithCompareTo(int x, int y) {
		super(x,y);
	}
		
	/**
	 * Compare this blob with another blob
	 * @param comparePoint point to compare to this point
	 * @return   0 if same, 
	 * 			 1 if this point is higher up than comparePoint,
	 * 			-1 otherwise
	 */
	public int compareTo(PointWithCompareTo comparePoint) {
		if (this.y < comparePoint.getY())
			return 1; //this Point is higher up, so it's bigger
		else if (this.y > comparePoint.getY())
			return -1; //this Point is lower, so it's smaller
		else return 0; //at same height, so they are the same
	}
}
