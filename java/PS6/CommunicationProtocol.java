import java.awt.*;
import java.util.ArrayList;

/**
 * Handles incoming messages to either the client or the server. Has an overloaded constructor which either takes in
 * a server or an editor, depending on where the protocol is being used.
 *
 * @author Ben Sheppard
 * @author Tara Salli
 */
public class CommunicationProtocol {
    private SketchServer server; // server handling for
    private Editor editor; // editor handling for

    /**
     * Constructor for handler on server side
     */
    public CommunicationProtocol(SketchServer server) {
        this.server = server;
    }

    /**
     * Constructor for handler on editor side
     */
    public CommunicationProtocol(Editor editor) {
        this.editor = editor;
    }

    /**
     * Handles a message either to client or to server. All messages begin with either toServer or toClient.
     * Then, the message has a command, i.e. add/move. Then message may or may not have ID (depending on message). Finally,
     * it has the data to construct the shape.
     *
     * @param message message to handle
     */
    public void handleMessage(String message) {
        String[] splitMessage = message.split(" ");
        if(splitMessage[1].equals("add")) {
            handleAdd(splitMessage);
        }
        else if(splitMessage[1].equals("move")){
            handleMove(splitMessage);
        }
        else if(splitMessage[1].equals("recolor")){
            handleRecolor(splitMessage);
        }
        else if(splitMessage[1].equals("delete")){
            handleDelete(splitMessage);
        }
    }

    /**
     * Handles a message which requests an add
     * @param splitMessage message
     */
    public void handleAdd(String[] splitMessage) {
        if(splitMessage[0].equals("toServer")) { // Message addressed to server
            if(splitMessage[2].equals("ellipse")) {
                // Add ellipse with given parameters to the server master sketch
                server.getSketch().addShape(server.getSketch().getID(), new Ellipse(Integer.parseInt(splitMessage[3]), Integer.parseInt(splitMessage[4]), Integer.parseInt(splitMessage[5]), Integer.parseInt(splitMessage[6]), new Color(Integer.parseInt(splitMessage[7]))));
            }
            else if(splitMessage[2].equals("rectangle")) {
                // Add rectangle with given parameters to the server master sketch
                server.getSketch().addShape(server.getSketch().getID(), new Rectangle(Integer.parseInt(splitMessage[3]), Integer.parseInt(splitMessage[4]), Integer.parseInt(splitMessage[5]), Integer.parseInt(splitMessage[6]), new Color(Integer.parseInt(splitMessage[7]))));
            }
            else if(splitMessage[2].equals("segment")) {
                // Add segment with given parameters to the server master sketch
                server.getSketch().addShape(server.getSketch().getID(), new Segment(Integer.parseInt(splitMessage[3]), Integer.parseInt(splitMessage[4]), Integer.parseInt(splitMessage[5]), Integer.parseInt(splitMessage[6]), new Color(Integer.parseInt(splitMessage[7]))));
            }
            else if(splitMessage[2].equals("freehand")) {
                ArrayList<Point> polylinePoints = new ArrayList<Point>(); // List to hold all points sent from client
                for(int i = 3; i < splitMessage.length - 2; i+=2){ // Iterate over the splitMessage to get the points
                    polylinePoints.add(new Point(Integer.parseInt(splitMessage[i]), Integer.parseInt(splitMessage[i+1]))); // Add point
                }
                // Add freehand with given parameters to the server master sketch
                server.getSketch().addShape(server.getSketch().getID(), new Polyline(polylinePoints, new Color(Integer.parseInt(splitMessage[splitMessage.length - 1]))));
            }
            // Build message to broadcast back to all clients
            String toBroadcast = "toClient add " + server.getSketch().getID() + " "; // Add new shape with correct ID
            for(int i = 2; i < splitMessage.length-1; i++){
                toBroadcast += (splitMessage[i] + " "); // Add all the data to reconstruct added shape on server side
            }
            toBroadcast += splitMessage[splitMessage.length - 1]; // Ensures that there is no space at end of message
            server.getSketch().incrementID(); // Increment ID for next time you add
            server.broadcast(toBroadcast); // Broadcast message to all clients
        }
        else { // Message was addressed to client
            int ID = Integer.parseInt(splitMessage[2]); // Gets ID of shape to add
            if(splitMessage[3].equals("ellipse")) {
                // Add ellipse with given parameters to the local sketch
                editor.getSketch().addShape(ID, new Ellipse(Integer.parseInt(splitMessage[4]), Integer.parseInt(splitMessage[5]), Integer.parseInt(splitMessage[6]), Integer.parseInt(splitMessage[7]), new Color(Integer.parseInt(splitMessage[8]))));
            }
            else if(splitMessage[3].equals("rectangle")) {
                // Add rectangle with given parameters to the local sketch
                editor.getSketch().addShape(ID, new Rectangle(Integer.parseInt(splitMessage[4]), Integer.parseInt(splitMessage[5]), Integer.parseInt(splitMessage[6]), Integer.parseInt(splitMessage[7]), new Color(Integer.parseInt(splitMessage[8]))));
            }
            else if(splitMessage[3].equals("segment")) {
                // Add segment with given parameters to the local sketch
                editor.getSketch().addShape(ID, new Segment(Integer.parseInt(splitMessage[4]), Integer.parseInt(splitMessage[5]), Integer.parseInt(splitMessage[6]), Integer.parseInt(splitMessage[7]), new Color(Integer.parseInt(splitMessage[8]))));
            }
            else if(splitMessage[3].equals("freehand")) {
                // Add freehand with given parameters to the local sketch
                ArrayList<Point> polylinePoints = new ArrayList<Point>(); // List to hold points of freehand
                for(int i = 4; i < splitMessage.length - 2; i+=2){ // Iterate over the splitMessage to get the points
                    polylinePoints.add(new Point(Integer.parseInt(splitMessage[i]), Integer.parseInt(splitMessage[i+1]))); // Add point to polylinePoints
                }
                // Add freehand with given parameters to the local sketch
                editor.getSketch().addShape(ID, new Polyline(polylinePoints, new Color(Integer.parseInt(splitMessage[splitMessage.length - 1]))));
            }
        }
    }

    /**
     * Handles a message which requests a move
     * @param splitMessage
     */
    public void handleMove(String[] splitMessage) {
        int ID = Integer.parseInt(splitMessage[2]); // ID of shape to move
        int dx = Integer.parseInt(splitMessage[3]); // dx to move by
        int dy = Integer.parseInt(splitMessage[4]); // dy to move by

        if(splitMessage[0].equals("toServer")) { // Message to server
            server.getSketch().moveShape(ID, dx, dy); // Moves the shape on the master sketch
            server.broadcast("toClient move " + ID + " " + dx + " " + dy); // Broadcasts to all clients to move shape on local sketch.
        }
        else { // Message to client
            editor.getSketch().moveShape(ID, dx, dy); // Move shape on local sketch
        }
    }

    /**
     * Handles a request to recolor shape
     * @param splitMessage
     */
    public void handleRecolor(String[] splitMessage) {
        int ID = Integer.parseInt(splitMessage[2]); // id of shape to recolor
        int color = Integer.parseInt(splitMessage[3]); // Color to recolor to

        if(splitMessage[0].equals("toServer")) { // If to server
            server.getSketch().recolorShape(ID, new Color(color)); // Update master sketch with new color
            server.broadcast("toClient recolor " + ID + " " + color); // Broadcast to all clients to recolor shape of id to color
        }
        else {
            editor.getSketch().recolorShape(ID, new Color(color)); // Recolor shape of id on local sketch.
        }
    }

    /**
     * Handle deleting a shape
     */
    public void handleDelete(String[] splitMessage) {
        int ID = Integer.parseInt(splitMessage[2]); // id of shape to delete
        if(splitMessage[0].equals("toServer")) { // message to server
            server.getSketch().removeShape(ID); // Remove shape of given id
            server.broadcast("toClient delete " + ID); // broadcast to all client to remove shape of given id
        }
        else { // to client
            editor.getSketch().removeShape(ID); // Remove shape on local sketch
        }
    }


    // For SENDING requests from client to server (From EditorCommunicator file)
    // ADD:
    // adds shape with these parameters
    // "toServer add ellipse x1 y1 x2 y2 color"
    // "toServer add rect x1 y1 x2 y2 color"
    // "toServer add segment x1 y1 x2 y2 color"
    // "toServer add  polyline x1 y1 x2 y2 ... color" (note, will have to construct ArrayList on server side, pass to constructor of polyline)
    //
    // MOVE CHECK: (note: this is only sent to server upon mouse click to check if there is a shape to move)
    // check if there is a shape to move. If so, sends back id of that shape. Send -1 if no shape.
    // "toServer moveCheck x y"
    //
    // MOVE: (performed under mouseDrag)
    // move whatever shape is on top from p1 to p2
    // "toServer move id dx dy"
    //
    // RECOLOR:
    // recolor whatever shape is on top at p
    // "toServer recolor x y color"
    //
    // Delete
    // delete whatever shape is on top at p
    // "toServer delete x y"



    // For reading from client on server side (CommunicationProtocol class handles this. maybe static method? discuss.)
    // 1. Split by spaces
    // 2. Check if a, mc, m, r, or d
    // 3. If a, add the shape described to the mastersketch.
    // 4. If mc, iterate over mastersketch, try to find id of shape in point. If find one, send it back to the EditorCommunicator. If not, send -1
    // 5. If m, get the id of correct object in mastersketch. Move position of that shape from p1 to p2
    // 6. If r, iterate over the mastersketch. Try to find id of shape in point. If found, change color to color. If not, do nothing.
    // 7. If d, iterate over mastersketch. Try to find id of shape in point. If found, delete it from the sketch.



    // For sending instructions from server to client (done in server class)
    //
    // Tells all clients to add to their sketch with a new shape with this id
    // "toClient add id ellipse x1 y1 x2 y2 color"
    // etc. for other shapes
    //
    // Tells just the client that sent move request the id  of shape to move (or -1 if no shape to move)
    // "toClient moveCheck id"
    //
    // Tells all clients to move the shape with id from p1 to p2
    // "toClient id dx dy"
    //
    // Tells all clients to recolor shape with id and this color
    // "toClient recolor id color"
    //
    // Tells all clients to delete shape with id
    // "toClient delete id"
    //
    //
    // id back from server of shape to move (or -1 if no shape to move)

    // For reading from server on client side (CommunicationProtocol class handles this. maybe static method? discuss.)
    // 1. Split by spaces
    // 2. Check if a, mc, m, r, or d
    // 3. If a, add the shape described to the clientsketch.
    // 4. If mc, then update movingID instance var. Then, when mouse is dragged will check if i.v. is -1
    // 5. If m, get the id of correct object in clientsketch. Move position of that shape from p1 to p2
    // 6. If r, recolor shape of id in clientsketch.
    // 7. If d, delete shape of id in clientsketch.




}
