import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Holds all the shapes and their corresponding IDs.
 *
 * @author Ben Sheppard
 * @author Tara Salli
 */
public class Sketch {
    private TreeMap<Integer, Shape> shapeMap; // Map ID to shape
    private int id; // Next id to assign
    public Sketch(){
        shapeMap = new TreeMap<Integer, Shape>();
        id = 0; // start at id 0
    }

    /**
     * Get shape map
     */
    public synchronized TreeMap<Integer, Shape> getShapeMap() {
        return shapeMap;
    }

    /**
     * Get id to assign
     */
    public synchronized int getID(){
        return id;
    }

    /**
     * Increment ID by 1
     */
    public synchronized void incrementID() {
        id++;
    }

    /**
     * Add new shape with an id to map
     */

    public synchronized void addShape(int id, Shape shape) {
        shapeMap.put(id, shape);
    }

    /**
     * Get a shape of given ID
     */
    public synchronized Shape getShape(int id) {
        return shapeMap.get(id);
    }

    /**
     * Remove shape of given ID
     */
    public synchronized void removeShape(int id) {
        shapeMap.remove(id);
    }

    /**
     * Recolor shape of given ID
     */
    public synchronized void recolorShape(int id, Color color) {
        getShape(id).setColor(color);
    }

    /**
     * Move shape of given ID by dx and dy
     */
    public synchronized void moveShape(int moveID, int dx, int dy) {
        getShape(moveID).moveBy(dx, dy);
    }

    /**
     * Draw shape of given ID in Graphics g
     */
    public synchronized void drawShape(int drawID, Graphics g) {
        shapeMap.get(drawID).draw(g);
    }
}
