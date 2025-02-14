import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 *
 * @author Ben Sheppard
 * @author Tara Salli
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, updated Fall 2016
 * @author Tim Pierson Dartmouth CS 10, provided for Winter 2024
 */
public class Polyline implements Shape {
	private ArrayList<Point> points; // ArrayList of points that make up the polyline
	private Color color;

	/**
	 * Creates a new polyline object with only (startX, startY) in the points initially.
	 */
	public Polyline(int startX, int startY, Color color) {
		points = new ArrayList<Point>();
		points.add(new Point(startX, startY));
		this.color = color;
	}

	/**
	 * Creates a new polyline with a given points ArrayList
	 */
	public Polyline(ArrayList<Point> points, Color color) {
		this.points = points;
		this.color = color;
	}

	/**
	 * Adds new point to points
	 */
	public void addPoint(int x, int y){
		points.add(new Point(x, y));
	}
	@Override
	public void moveBy(int dx, int dy) {
		for(Point point : points) {
			point.setLocation(point.x + dx, point.y + dy);
		}
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public boolean contains(int x, int y) {
		for(int i = 0; i < points.size() - 1; i++) {
			if(Segment.pointToSegmentDistance(x, y, points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y) <= 3) return true;
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		for(int i = 0; i < points.size() - 1; i++) {
			new Segment(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y, color).draw(g);
		}
	}

	@Override
	public String toString() {
		String output =  "freehand ";
		for(int i = 0; i < points.size(); i++){
			output += points.get(i).x + " " + points.get(i).y  + " ";
		}
		output += color.getRGB();
		return output;
	}
}
