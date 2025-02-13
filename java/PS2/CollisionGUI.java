import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * PS-2 provided code
 * Using a quadtree for collision detection
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2024, based on prior term code
 * @author Ben Sheppard
 * @author Tara Salli
 */
public class CollisionGUI extends InteractiveGUI {
	private static final int width=800, height=600;		// size of the window
	private List<MovingPoint> points;					// all the points
	private List<MovingPoint> colliders;				// the points that collided at this time
	private char collisionHandler = 'c';				// when there's a collision, 'c'olor them, or 'd'estroy them
	private int delay = 100;							// timer control

	public CollisionGUI() {
		super("super-collider", width, height);

		points = new ArrayList<MovingPoint>();

		// Timer drives the animation. Ticks every delay milliseconds
		startTimer();
	}

	/**
	 * Adds a MovingPoint at the (x,y) location
	 * @param x X location to add point
	 * @param y Y location to add point
	 */
	private void add(int x, int y) {
		points.add(new MovingPoint(x,y,width,height));
	}

	/**
	 * InteractiveGUI method, here creating a new MovingPoint when the mouse is clicked
	 * repaint method erases the window and calls draw
	 * @param x,y location of the mouse when pressed
	 */
    @Override
	public void handleMousePress(int x, int y) {
		add(x,y);
		repaint();
	}

	/**
	 * InteractiveGUI method, get the key pressed
	 * @param k the key pressed
	 */
    @Override
	public void handleKeyPress(char k) {
		if (k == 'f') { // faster
			if (delay>1) delay /= 2;
			setTimerDelay(delay);
			System.out.println("delay:"+delay);
		}
		else if (k == 's') { // slower
			delay *= 2;
			setTimerDelay(delay);
			System.out.println("delay:"+delay);
		}
		else if (k == 'r') { // add some new points at random positions
			for (int i=0; i<10; i++) {
				add((int)(width*Math.random()), (int)(height*Math.random()));
			}
			repaint();
		}
		else if (k == 'c' || k == 'd') { // control how collisions are handled
			collisionHandler = k;
			System.out.println("collision:"+k);
		}
	}

	/**
	 * InteractiveGUI method, here drawing all the points and then re-drawing the colliders in red
	 * @param g a reference to the window on which to draw the MovingPoints
	 */
    @Override
	public void draw(Graphics g) {
		// Ask all the points to draw themselves.
		g.setColor(Color.BLACK);
		if (points != null) {
			for (MovingPoint point : points) {
				point.draw(g);
			}
		}
		// Ask the colliders to draw themselves in red.
		if (colliders != null) {
			g.setColor(Color.RED);
			for (MovingPoint point : colliders) {
				point.draw(g);
			}
		}
	}

	/**
	 * Sets colliders to include all points in contact with another point
	 */
	private void findColliders() {
		PointQuadtree<MovingPoint> allMovingPoints = new PointQuadtree<MovingPoint>(points.get(0), 0,0, width, height); // Create a quadtree with the 0th point as root
		for (int i = 1; i < points.size(); i++) { // Insert the rest of the points into the quadtree
			allMovingPoints.insert(points.get(i));
		}
		colliders = new ArrayList<MovingPoint>(); // Create a list to hold the colliders
		for(MovingPoint point : points){ // For each point, see if it collided with another point
			int collisionCircle = 2 * point.getR(); // Circle with twice the radius of the current point
			List<MovingPoint> collisions = allMovingPoints.findInCircle(point.getX(), point.getY(), collisionCircle); // Creates list of all unique collisions by calling findInCircle.
			if(collisions.size() > 1){ // Only if the collisions list is size 2 or greater; a point can't collide with itself (size one)
                for (MovingPoint collidedPoint : collisions) { // Iterate through collisions
					colliders.add(collidedPoint); // Add each point that has not already been added to collisions to instance variable colliders.
                }
			}
		}
	}

	/**
	 * InteractiveGUI method, here moving all the points and checking for collisions
	 */
    @Override
	public void handleTimer() {
		// Ask all the points to move themselves.
		for (MovingPoint point : points) {
			point.move();
		}
		// Check for collisions
		if (points.size() > 0) {
			findColliders();
			if (collisionHandler=='d') {
				points.removeAll(colliders);
				colliders = null;
			}
		}
		// Now update the drawing
		repaint();
	}

	public static void main(String[] args) {
        new CollisionGUI();
	}
}