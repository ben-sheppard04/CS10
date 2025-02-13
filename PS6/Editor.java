import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Client-server graphical editor
 *
 * @author Ben Sheppard
 * @author Tara Salli
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; loosely based on CS 5 code by Tom Cormen
 * @author CBK, winter 2014, overall structure substantially revised
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 * @author CBK, spring 2016 and Fall 2016, restructured Shape and some of the GUI
 * @author Tim Pierson Dartmouth CS 10, provided for Winter 2024
 */

public class Editor extends JFrame {	
	private static String serverIP = "localhost";			// IP address of sketch server
	// "localhost" for your own machine;
	// or ask a friend for their IP address

	private static final int width = 800, height = 800;		// canvas size

	// Current settings on GUI
	public enum Mode {
		DRAW, MOVE, RECOLOR, DELETE
	}
	private Mode mode = Mode.DRAW;				// drawing/moving/recoloring/deleting objects
	private String shapeType = "ellipse";		// type of object to add
	private Color color = Color.black;			// current drawing color

	// Drawing state
	// these are remnants of my implementation; take them as possible suggestions or ignore them
	private Shape curr = null;					// current shape (if any) being drawn
	private Sketch sketch;						// holds and handles all the completed objects
	private Point drawFrom = null;				// where the drawing started
	private Point moveFrom = null;				// where object is as it's being dragged
	private int movingId = -1;					// current shape id (if any; else -1) being moved
	private int recolorID = -1;					// current shape id (if any; else -1) being recolored
	private int deleteID = -1;					// current shape id (if any; else -1) being deleted
	// Communication
	private EditorCommunicator comm;			// communication with the sketch server

	public Editor() {
		super("Graphical Editor");

		sketch = new Sketch();

		// Connect to server
		comm = new EditorCommunicator(serverIP, this);
		comm.start();

		// Helpers to create the canvas and GUI (buttons, etc.)
		JComponent canvas = setupCanvas();
		JComponent gui = setupGUI();

		// Put the buttons and canvas together into the window
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);
		cp.add(gui, BorderLayout.NORTH);

		// Usual initialization
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/**
	 * Creates a component to draw into
	 */
	private JComponent setupCanvas() {
		JComponent canvas = new JComponent() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawSketch(g);
			}
		};
		
		canvas.setPreferredSize(new Dimension(width, height));

		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				handlePress(event.getPoint());
			}

			public void mouseReleased(MouseEvent event) {
				handleRelease();
			}
		});		

		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent event) {
				handleDrag(event.getPoint());
			}
		});
		
		return canvas;
	}

	/**
	 * Creates a panel with all the buttons
	 */
	private JComponent setupGUI() {
		// Select type of shape
		String[] shapes = {"ellipse", "freehand", "rectangle", "segment"};
		JComboBox<String> shapeB = new JComboBox<String>(shapes);
		shapeB.addActionListener(e -> shapeType = (String)((JComboBox<String>)e.getSource()).getSelectedItem());

		// Select drawing/recoloring color
		// Following Oracle example
		JButton chooseColorB = new JButton("choose color");
		JColorChooser colorChooser = new JColorChooser();
		JLabel colorL = new JLabel();
		colorL.setBackground(Color.black);
		colorL.setOpaque(true);
		colorL.setBorder(BorderFactory.createLineBorder(Color.black));
		colorL.setPreferredSize(new Dimension(25, 25));
		JDialog colorDialog = JColorChooser.createDialog(chooseColorB,
				"Pick a Color",
				true,  //modal
				colorChooser,
				e -> { color = colorChooser.getColor(); colorL.setBackground(color); },  // OK button
				null); // no CANCEL button handler
		chooseColorB.addActionListener(e -> colorDialog.setVisible(true));

		// Mode: draw, move, recolor, or delete
		JRadioButton drawB = new JRadioButton("draw");
		drawB.addActionListener(e -> mode = Mode.DRAW);
		drawB.setSelected(true);
		JRadioButton moveB = new JRadioButton("move");
		moveB.addActionListener(e -> mode = Mode.MOVE);
		JRadioButton recolorB = new JRadioButton("recolor");
		recolorB.addActionListener(e -> mode = Mode.RECOLOR);
		JRadioButton deleteB = new JRadioButton("delete");
		deleteB.addActionListener(e -> mode = Mode.DELETE);
		ButtonGroup modes = new ButtonGroup(); // make them act as radios -- only one selected
		modes.add(drawB);
		modes.add(moveB);
		modes.add(recolorB);
		modes.add(deleteB);
		JPanel modesP = new JPanel(new GridLayout(1, 0)); // group them on the GUI
		modesP.add(drawB);
		modesP.add(moveB);
		modesP.add(recolorB);
		modesP.add(deleteB);

		// Put all the stuff into a panel
		JComponent gui = new JPanel();
		gui.setLayout(new FlowLayout());
		gui.add(shapeB);
		gui.add(chooseColorB);
		gui.add(colorL);
		gui.add(modesP);
		return gui;
	}

	/**
	 * Getter for the sketch instance variable
	 */
	public Sketch getSketch() {
		return sketch;
	}

	/**
	 * Draws all the shapes in the sketch,
	 * along with the object currently being drawn in this editor (not yet part of the sketch)
	 */
	public void drawSketch(Graphics g) {
		for(Integer id : sketch.getShapeMap().navigableKeySet()) { // Loops over every shape in the local sketch from lowest to highest ID
			sketch.drawShape(id, g); // draws shape with given ID
		}
		if(mode == Mode.DRAW && curr != null) {
			curr.draw(g); // Draw current object being created
		}
	}

	// Helpers for event handlers
	
	/**
	 * Helper method for press at point
	 * In drawing mode, start a new object;
	 * in moving mode, (request to) start dragging if clicked in a shape;
	 * in recoloring mode, (request to) change clicked shape's color
	 * in deleting mode, (request to) delete clicked shape
	 */
	private void handlePress(Point p) {
		if (mode == Mode.DRAW) {
			if(shapeType.equals("ellipse")) {
				curr = new Ellipse(p.x, p.y, color); // create ellipse with only point as click point
			}
			else if(shapeType.equals("rectangle")) {
				curr = new Rectangle(p.x, p.y, color); // create rect with only point as click point
			}
			else if(shapeType.equals("segment")) {
				curr = new Segment(p.x, p.y, color); // create segment with only point as click point
			}
			else if(shapeType.equals("freehand")) {
				curr = new Polyline(p.x, p.y, color); // create freehand with only point as click point
			}
			drawFrom = p; // stores the initial click point in drawFrom
		}
		else if (mode == Mode.MOVE) {
			moveFrom = p; // remember where initial click was
			for(Integer id : sketch.getShapeMap().descendingKeySet()) { // loop over every shape in the local sketch by descending ID (recentness)
				Shape currentShape = sketch.getShape(id); // Get shape
				if (currentShape.contains(p.x, p.y)) { // If the current shape contains the click point
					movingId = id; // Update moving ID -- shape you will move
					break; // break; want most recent shape that contains point of click
				}
			}
		}
		else if (mode == Mode.RECOLOR) {
			for(Integer id : sketch.getShapeMap().descendingKeySet()) { // loop over every shape in the local sketch by descending ID (recentness)
				Shape currentShape =  sketch.getShape(id);  // Get shape
				if (currentShape.contains(p.x, p.y)) { // If the current shape contains the click point
					recolorID = id; // Update recolor ID -- shape you will move
					break; // break; want most recent shape that contains point of click
				}
			}
			if(recolorID != -1){
				comm.requestRecolor(recolorID, color); // Requests for server to recolor the object with given recolorID and color
			}
			recolorID = -1; // Reset recolorID
		}
		else if (mode == Mode.DELETE){
			for(Integer id : sketch.getShapeMap().descendingKeySet()) {  // loop over every shape in the local sketch by descending ID (recentness)
				Shape currentShape =  sketch.getShape(id); // Get shape
				if (currentShape.contains(p.x, p.y)) { // If the current shape contains the click point
					deleteID = id; // Update recolor ID -- shape you will move
					break; // break; want most recent shape that contains point of click
				}
			}
			if(deleteID != -1){
				comm.requestDelete(deleteID); // Requests for server to delete the object with given delete ID
			}
			deleteID = -1; // Reset deleteID
		}
		repaint();
	}

	/**
	 * Helper method for drag to new point
	 * In drawing mode, update the other corner of the object;
	 * in moving mode, (request to) drag the object
	 */
	private void handleDrag(Point p) {
		if (mode == Mode.DRAW) {
			if(shapeType.equals("ellipse")) {
				((Ellipse)curr).setCorners(drawFrom.x, drawFrom.y, p.x, p.y); // Update ellipse corners as user drags
			}
			else if(shapeType.equals("rectangle")) {
				((Rectangle)curr).setCorners(drawFrom.x, drawFrom.y, p.x, p.y); // Update rectangle corners as user drags
			}
			else if(shapeType.equals("segment")) {
				((Segment)curr).setEnd(p.x, p.y); // Update segment end as user drags
			}
			else if(shapeType.equals("freehand")) {
				((Polyline)curr).addPoint(p.x, p.y); // Update freehand points as user drags
			}
		}
		else if (mode == Mode.MOVE) {
			if(movingId != -1){ // If there is a shape where click was initially
				comm.requestMove(movingId, p.x - moveFrom.x, p.y - moveFrom.y); // Request a move of movingID by dx, dy, which are determined by difference between initial click and place where user dragged to
				moveFrom = p; // Update moveFrom to be place user dragged to
			}
		}
		repaint();
	}

	/**
	 * Helper method for release
	 * In drawing mode, pass the add new object request on to the server;
	 * in moving mode, release it		
	 */
	private void handleRelease() {
		if(mode == Mode.DRAW) {
			comm.send("toServer add " + curr.toString()); // If drawing, send request to server for adding a shape
		}
		if(mode == Mode.MOVE){
			moveFrom = null; // Reset moveFrom
			movingId = -1; // Reset movingID
		}
		curr = null; // reset curr to null after release.
		repaint();
	}
	// If drawing mode: send curr to server. Need to figure out protocol for sending as String
	// If in moving mode:
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Editor();
			}
		});	
	}
}
