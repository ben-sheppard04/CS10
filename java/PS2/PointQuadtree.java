import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * PS-2 provided code
 * A point quadtree: stores an element at a 2D position, with children at the subdivided quadrants
 * E extends Point2D to ensure whatever the PointQuadTree holds, it implements getX and getY
 * 
 * @author Tim Pierson, Dartmouth CS10, Winter 2024, based on prior term code
 * @author Ben Sheppard
 * @author Tara Salli
 */
public class PointQuadtree<E extends Point2D> {
	private E point;							// the point anchoring this node
	private int x1, y1;							// upper-left corner of the region
	private int x2, y2;							// bottom-right corner of the region
	private PointQuadtree<E> c1, c2, c3, c4;	// children

	/**
	 * Initializes a leaf quadtree, holding the point in the rectangle
	 * @param point Point in root node
	 * @param x1 x1 of root node
	 * @param y1 y1 of root node
	 * @param x2 x2 of root node
	 * @param y2 y2 of root node
	 */
	public PointQuadtree(E point, int x1, int y1, int x2, int y2) {
		this.point = point;
		this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
	}

	// Getters
	public E getPoint() { return point; }
	public int getX1() { return x1; }
	public int getY1() { return y1; }
	public int getX2() { return x2; }
	public int getY2() { return y2; }

	/**
	 * Returns the child (if any) at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 * @return child for quadrant
	 */
	public PointQuadtree<E> getChild(int quadrant) {
		if (quadrant==1) return c1;
		if (quadrant==2) return c2;
		if (quadrant==3) return c3;
		if (quadrant==4) return c4;
		return null;
	}

	/**
	 * Returns whether there is a child at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 * @return Whether there is a child at the given quadrant, 1-4
	 */
	public boolean hasChild(int quadrant) {
		return (quadrant==1 && c1!=null) || (quadrant==2 && c2!=null) || (quadrant==3 && c3!=null) || (quadrant==4 && c4!=null);
	}

	/**
	 * Inserts the point into the tree
	 * @param p2 The point of type E which is to be inserted into the tree.
	 */
	public void insert(E p2) {
		if((int)p2.getX() >= (int)point.getX() && (int)p2.getY() < (int)point.getY()) { // If the point is in quadrant 1 or lies on vertical
			if (hasChild(1)) { // If the current node has a child in quadrant 1
				c1.insert(p2); // Then recurse the insert method on that child.
			} else { // If current node does not have a child in quadrant 1
				c1 = new PointQuadtree<E>(p2, (int)point.getX(), y1, x2, (int)point.getY()); // Create a child in quadrant 1 with point p2 and set up the new top left and bottom right corners
			}
		}
		if((int)p2.getX() < (int)point.getX() && (int)p2.getY() <= (int)point.getY()) { // If the point is in quadrant 2 or lies on horizontal
			if (hasChild(2)) { // If the current node has a child in quadrant 2
				c2.insert(p2); // Then recurse the insert method on that child.
			} else { // If current node does not have a child in quadrant 2
				c2 = new PointQuadtree<E>(p2, x1, y1, (int)point.getX(), (int)point.getY()); // Create a child in quadrant 2 with point p2 and set up the new top left and bottom right corners
			}
		}
		if((int)p2.getX() <= (int)point.getX() && (int)p2.getY() > (int)point.getY()) { // If the point is in quadrant 3 or lies on vertical
			if (hasChild(3)) { // If the current node has a child in quadrant 3
				c3.insert(p2); // Then recurse the insert method on that child.
			} else { // If current node does not have a child in quadrant 3
				c3 = new PointQuadtree<E>(p2, x1, (int)point.getY(), (int)point.getX(), y2); // Create a child in quadrant 3 with point p2 and set up the new top left and bottom right corners
			}
		}
		if((int)p2.getX() > (int)point.getX() && (int)p2.getY() >= (int)point.getY()) { // If the point is in quadrant 4 or lies on horizontal
			if (hasChild(4)) { // If the current node has a child in quadrant 4
				c4.insert(p2); // Then recurse the insert method on that child.
			} else { // If current node does not have a child in quadrant 4
				c4 = new PointQuadtree<E>(p2, (int)point.getX(), (int)point.getY(), x2, y2); // Create a child in quadrant 4 with point p2 and set up the new top left and bottom right corners
			}
		}
		//Note: will not insert if p2 is the same as the root node
	}
	
	/**
	 * Finds the number of nodes in the quadtree (including its descendants)
	 * @return Size of the quadtree
	 */
	public int size() {
		int num = 1; // Initializes the size of the quadtree to one, representing the root.
		if(hasChild(1)) num += c1.size(); // If the current node has a child in quadrant 1, recurse the size method on the child and add the value to num.
		if(hasChild(2)) num += c2.size(); // If the current node has a child in quadrant 2, recurse the size method on the child and add the value to num.
		if(hasChild(3)) num += c3.size(); // If the current node has a child in quadrant 3, recurse the size method on the child and add the value to num.
		if(hasChild(4)) num += c4.size(); // If the current node has a child in quadrant 4, recurse the size method on the child and add the value to num.
		return num; // return num, representing the size of the quadtree.
	}
	
	/**
	 * Builds a list of all the points in the quadtree (including its descendants)
	 * @return List of all points in the quadtree
	 */
	public List<E> allPoints() {
		List<E> points = new ArrayList<E>(); // Initializes an empty list of points of type E.
		allPointsHelper(points); // Calls the helper method on the empty list, which will then recursively fill the list with all the points in the quadtree.
		return points; // Returns the list of points in the quadtree.
	}	

	/**
	 * Uses the quadtree to find all points within the circle
	 * @param cx	circle center x
	 * @param cy  	circle center y
	 * @param cr  	circle radius
	 * @return    	the points in the circle (and the qt's rectangle)
	 */
	public List<E> findInCircle(double cx, double cy, double cr) {
		List<E> pointsInCircle = new ArrayList<E>(); // Initializes an empty list of points of type E that are within a given circle's radius.
		this.findInCircleHelper(pointsInCircle, cx, cy, cr); // Calls the helper method on the empty list, which will then recursively fill the list with all the points that are within the circle's radius.
		return pointsInCircle; // Returns the list of points that are within the given circle's radius.
	}

	// HELPER METHODS:
	/**
	 * Helper method for allPoints method
	 * Builds a list of all the points in the quadtree (including its descendants)
	 * @param points list of points that is being built up as the method is recursively called
	 */
	public void allPointsHelper (List<E> points) {
		points.add(point); // Adds the current point to the points list.
		if(hasChild(1))c1.allPointsHelper(points); // If the current point has a child in quadrant 1, recursively calls the allPointsHelper method on the child.
		if(hasChild(2))c2.allPointsHelper(points); // If the current point has a child in quadrant 2, recursively calls the allPointsHelper method on the child.
		if(hasChild(3))c3.allPointsHelper(points); // If the current point has a child in quadrant 3, recursively calls the allPointsHelper method on the child.
		if(hasChild(4))c4.allPointsHelper(points); // If the current point has a child in quadrant 4, recursively calls the allPointsHelper method on the child.
	}

	/**
	 * Helper method for findInCircle method
	 * Uses the quadtree to find all points within the circle
	 * @param pointsInCircle list that is being built up as the method is recursively called to contain points that are within a given circle's radius
	 * @param cx	circle center x
	 * @param cy  	circle center y
	 * @param cr  	circle radius
	 */
	public void findInCircleHelper (List<E> pointsInCircle, double cx, double cy, double cr) {
		if (Geometry.circleIntersectsRectangle(cx, cy, cr, x1, y1, x2, y2)) { // If a given circle intersects a quadrant given by x1, y1, etc.
			if (Geometry.pointInCircle(this.point.getX(), this.point.getY(), cx, cy, cr)) { // If the point is in the circle
				pointsInCircle.add(point); // Then add the point to the list of points in the circle
			}
			if(hasChild(1)) {
				c1.findInCircleHelper(pointsInCircle, cx, cy, cr);
			}// Recursively call the method on child 1 if child 1 is not null.
			if(hasChild(2)) {
				c2.findInCircleHelper(pointsInCircle, cx, cy, cr); // Recursively call the method on child 2 if child 2 is not null.
			}
			if(hasChild(3)) {
				c3.findInCircleHelper(pointsInCircle, cx, cy, cr); // Recursively call the method on child 3 if child 3 is not null.
			}
			if(hasChild(4)) {
				c4.findInCircleHelper(pointsInCircle, cx, cy, cr); // Recursively call the method on child 4 if child 4 is not null.
			}
		}
	}
}
