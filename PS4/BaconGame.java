import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/**
 * Bacon Game which implements the functionality described for PS-4. Closely matches example from webpage.
 * @author Ben Sheppard
 * @author Tara Salli
 */
public class BaconGame {
    private Graph<String, Set<String>> movieActorGraph; // Graph connecting all actors with sets of movies that they share
    private Graph<String, Set<String>> pathTree; // Path tree connecting center of universe to vertices that are reachable in the shortest path
    private Map<Integer, String> actorID; // Map relating IDs to actors
    private Map<Integer, String> movieID; // Mpap relating IDs to movies
    private Map<Integer, Set<Integer>> movieToActors; // Map relating ID of every movie to a set of all actors' IDs who have been in that movie
    private String centerOfUniverse; // Center of universe, will be Kevin Bacon upon startup.

    /**
     * Constructor for the Bacon Game. First initializes and creates the maps from the files. Then builds movieActorGraph
     * using these maps. Then sets center of universe to Kevin Bacon by default and computes pathTree using BFS.
     * @param actorIDFile File relating IDs to actors
     * @param movieIDFile File relating IDs to movies
     * @param actorMovieFile File relating actors' IDs to movies' IDs
     * @throws IOException Exception for reading files
     */
    /**
     * Class that relates an actor's name to the average path length of all the actors they are connected to.
     * @author Ben Sheppard
     * @author Tara Salli
     */

    public BaconGame (String actorIDFile, String movieIDFile, String actorMovieFile) throws IOException {
        // Initializes the Graphs and Maps
        movieActorGraph = new AdjacencyMapGraph<String, Set<String>>();
        actorID = new HashMap<Integer, String>();
        movieID = new HashMap<Integer, String>();
        movieToActors = new HashMap<Integer, Set<Integer>>();

        // Buffered reader to read first document. Makes actorID Map

        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(actorIDFile)); // Tries to read actorIDFile
        }
        catch (Exception e) {
            throw new IOException("Cannot open actor ID file\n" + e);
        }
        try { // Tries to read line of file
            String line;
            while ((line = in.readLine()) != null) {
                String[] splitLine = line.split("\\|"); // Splits line by the pipe character
                try{ // Try to parse ID on left side to int and put to actorID map
                    actorID.put(Integer.parseInt(splitLine[0]), splitLine[1]);
                }
                catch(Exception e){
                    throw new Exception("Cannot parse ID to an int\n" + e);
                }
                movieActorGraph.insertVertex(splitLine[1]); // adds vertices (actors) to the movieActorGraph
            }
        }
        catch(Exception e){
            throw new IOException("Cannot read line in file\n" + e);
        }

        // Buffered reader to read second document. Makes movieID Map

        BufferedReader in2;
        try {
            in2 = new BufferedReader(new FileReader(movieIDFile)); // Tries to read movieIDFile
        }
        catch (Exception e) {
            throw new IOException("Cannot open movie ID file\n" + e);
        }
        try {
            String line;
            while ((line = in2.readLine()) != null) {
                String[] splitLine = line.split("\\|"); // Splits line by the pipe character
                try{ // Try to parse ID on left side to int and put to actorID map
                    movieID.put(Integer.parseInt(splitLine[0]), splitLine[1]);
                }
                catch(Exception e){
                    throw new Exception("Cannot parse ID to an int\n" + e);
                }
            }
        }
        catch(Exception e){
            throw new IOException("Cannot read line in file\n" + e);
        }

        // Buffered reader to read third document. Makes movieToActors map.

        BufferedReader in3;
        try {
            in3 = new BufferedReader(new FileReader(actorMovieFile)); // Tries to read actorMovieFile
        }
        catch (Exception e) {
            throw new IOException("Cannot open actor movie file\n" + e);
        }
        try {
            String line;
            while ((line = in3.readLine()) != null) {
                String[] splitLine = line.split("\\|"); // Splits line by the pipe character
                try{
                    if(!movieToActors.containsKey(Integer.parseInt(splitLine[0]))){ // If the movie ID is not already contained within the Map
                        Set<Integer> actorSet = new HashSet<Integer>(); // Initialize set to hold actors that have been in that movie
                        actorSet.add(Integer.parseInt(splitLine[1])); // Add the current actor to that set
                        movieToActors.put(Integer.parseInt(splitLine[0]), actorSet); // Puts in map
                    }
                    else{ // movie ID already contained in Map
                        movieToActors.get(Integer.parseInt(splitLine[0])).add(Integer.parseInt(splitLine[1])); // Get the set of actor that were in the movie, add the current actor to that set.
                    }
                }
                catch(Exception e){
                    throw new Exception("Cannot parse ID to an int\n" + e);
                }
            }
        }
        catch(Exception e){
            throw new IOException("Cannot read line in file\n" + e);
        }

        // Creates edge labels between vertices (already added while reading actorID file)

        for (Integer movie : movieToActors.keySet()) { // loops over every movie
            List<Integer> actorsInMovie = new ArrayList<Integer>(movieToActors.get(movie)); // initializes a new list that holds the set of actors that starred in current movie
            for (int i = 0; i < actorsInMovie.size() - 1; i++) { // checks pairs of actors in list of actors starring in movie
                for (int j = i + 1; j < actorsInMovie.size(); j++) {
                    String vertex1 = actorID.get(actorsInMovie.get(i));
                    String vertex2 = actorID.get(actorsInMovie.get(j));

                    if (!movieActorGraph.hasEdge(vertex1, vertex2)) { // if the edge between the two actors does NOT exist
                        Set<String> movies = new HashSet<>(); // initialize set
                        movies.add(movieID.get(movie)); // add the movie to the set
                        movieActorGraph.insertUndirected(vertex1, vertex2, movies); // insert that set as the edge label between the two actors
                    }
                    else {
                        movieActorGraph.getLabel(vertex1, vertex2).add(movieID.get(movie)); // if a set between the actors already exists, add the movie to the set
                    }
                }
            }
        }
        startGame(); // now that the graph is created, start the game
    }

    /**
     * Prints out the directions for the game. Initializes the center of the universe to Kevin Bacon. Prompts user for input for the first time.
     */
    public void startGame() {
        System.out.println("Commands:\nc <#>: list top (positive number) or bottom (negative) <#> centers of the universe, sorted by average separation\nd <low> <high>: list actors sorted by degree, with degree between low and high\ni: list actors with infinite separation from the current center\np <name>: find path from <name> to current center of the universe\ns <low> <high>: list actors sorted by non-infinite separation from the current center, with separation between low and high\nu <name>: make <name> the center of the universe\nq: quit game\n");

        updateCenter("Kevin Bacon"); // Upon startup, center is Kevin Bacon
        promptUser();
    }

    /**
     * Method to scan for and handle user input. Is called each time user is prompted with option to play game.
     */
    public void promptUser() {
        Scanner in = new Scanner(System.in);
        String line;
        System.out.println(centerOfUniverse + " game >");
        line = in.nextLine(); // user input

        if(line.isEmpty()) { // handles case when user input is blank
            System.out.println("Please enter in the correct format.");
            promptUser();
        }

        else if(line.charAt(0) == 'c') { // If mode c. Example input: "c 5" --> lists top 5 centers of universe of the clique you are in, sorted by avg separation
            String[] splitLine = line.split(" ");
            if (splitLine.length != 2) { // if more than one space in input
                System.out.println("Please enter in the correct format.");
                promptUser();
            }
            else {
                try {
                    int number = Integer.parseInt(splitLine[1]); // number of top/bottom x averages
                    if (Math.abs(number) > pathTree.numVertices()) { // checks that the number is not greater than number of vertices in the clique
                        System.out.println("The number must be less than or equal to " + pathTree.numVertices());
                        promptUser();
                    }
                    else{
                        List<ActorAverage> averages = new ArrayList<ActorAverage>(); // list that holds every average in the clique, and their corresponding actor name

                        for (String vertex : pathTree.vertices()) { // loop over all actors in clique
                            if(movieActorGraph.outDegree(vertex) != 0) { // If out degree is 0, can't run averagePath because it is disconnected from everyone.
                                Graph<String, Set<String>> path = GraphLibPS4.bfs(movieActorGraph, vertex); // creates path tree with vertex as root
                                double avgSeparation = GraphLibPS4.averageSeparation(path, vertex); // computes the average separation from the vertex
                                averages.add(new ActorAverage(vertex, avgSeparation)); // adds it to the list of averages/actors
                            }
                        }
                        averages.sort((ActorAverage a, ActorAverage b) -> Double.compare(a.getAverage(), b.getAverage())); // sort averages in ascending average order

                        List<String> actorList = new ArrayList<String>(); // resulting list of top/bottom x actors by average
                        if(number == 0){
                            System.out.println("Number can't be 0.");
                            promptUser();
                        }
                        else{
                            if (number > 0) { // top x averages
                                for (int i = averages.size() - 1; i > averages.size() - number - 1; i--) { // iterates from right side of sorted list x times
                                    actorList.add(averages.get(i).getActorName());
                                }
                            }
                            else{ // bottom x averages
                                for (int i = 0; i < Math.abs(number); i++) { // iterates from left side of sorted list x times
                                    actorList.add(averages.get(i).getActorName());
                                }
                            }
                            System.out.println(actorList);
                            promptUser();
                        }

                    }
                }
                catch(Exception e){
                    System.out.println("Please enter in the correct format.");
                    promptUser();
                }
            }
        }

        else if(line.charAt(0) == 'd') { // If mode d. Example input: "d 5 10" --> list actors sorted by degree with degree between 5 and 10
            String[] splitLine = line.split(" ");
            if (splitLine.length != 3) { // if more than 2 spaces
                System.out.println("Please enter in the correct format.");
                promptUser();
            }
            else {
                try {

                    int low = Integer.parseInt(splitLine[1]);
                    int high = Integer.parseInt(splitLine[2]);
                    if(low > high){ // Ensures low is not greater than high
                        System.out.println("Low cannot be greater than high.");
                        promptUser();
                    }
                    else {
                        List<String> actorDegreeList = new ArrayList<String>(); // stores actors within range, sorted by degree
                        for (String vertex : movieActorGraph.vertices()) { // iterate over every actor
                            if (movieActorGraph.inDegree(vertex) >= low && movieActorGraph.inDegree(vertex) <= high) { // if in range, add it to the list
                                actorDegreeList.add(vertex);
                            }
                        }
                        actorDegreeList.sort((String a, String b) -> movieActorGraph.inDegree(a) - movieActorGraph.inDegree(b)); // sort by ascending degree
                        System.out.println(actorDegreeList);
                        promptUser();
                    }
                }
                catch (Exception e){
                    System.out.println("Please enter in the correct format.");
                    promptUser();
                }
            }
        }

        else if(line.charAt(0) == 'i') { // If mode i. Example input: "i" --> list actors with infinite separation from current center
            if (line.length() > 1) { // checks if more than one character
                System.out.println("Please enter in correct format.");
                promptUser();
            }
            else{
                System.out.println(new ArrayList<String>(GraphLibPS4.missingVertices(movieActorGraph, pathTree))); // print a list which is created by the set of missing vertices
                promptUser();
            }
        }

        else if(line.charAt(0) == 'p') { // If mode p. Example input: "p Sean Connery" --> find path from Sean Connery to current center of the universe
            String name = line.substring(2); // extracts the name
            if (!pathTree.hasVertex(name)) { // if the name is not contained in the clique
                System.out.println("There is no path from " + name + " to " + centerOfUniverse);
                promptUser();
            }
            else {
                List<String> actorPath = GraphLibPS4.getPath(pathTree, name); // list of path to center
                String output = ""; // output string to be built

                for (int i = 0; i < actorPath.size() - 1; i++) { // iterates over everything in the path list
                    output += actorPath.get(i) + " appeared in " + pathTree.getLabel(actorPath.get(i), actorPath.get(i + 1)) + " with " + actorPath.get(i + 1) + "\n"; // add the name of actor and the movies they have been in with another actor to output
                }
                System.out.println(name + "'s number is " + (actorPath.size() - 1)); // gets the actor number
                System.out.println(output);
                promptUser();
            }
        }

        else if(line.charAt(0) == 's') { // If mode s. Example input: "s 5 10" --> list actors, sorted by non-infinite separation from the current center with separation between 5 and 10
            String[] splitString = line.split(" ");
            if(splitString.length != 3){ // if more than 2 spaces
                System.out.println("Please enter in the correct format.");
                promptUser();
            }
            else {
                try {
                    int low = Integer.parseInt(splitString[1]);
                    int high = Integer.parseInt(splitString[2]);
                    if(low > high){ // Ensures low is not greater than high
                        System.out.println("Low cannot be greater than high.");
                        promptUser();
                    }
                    else{
                        String ouput = "";
                        List<String> actorsInRange = new ArrayList<String>(); // list of actors within range, sorted
                        for (String actor : pathTree.vertices()) { // loop over actors in clique
                            List<String> path = GraphLibPS4.getPath(pathTree, actor); // get the path from actor to center of universe
                            if (path.size() - 1 <= high && path.size() - 1 >= low) { // if within range, add to list
                                actorsInRange.add(actor);
                            }
                        }
                        actorsInRange.sort((String a, String b) -> GraphLibPS4.getPath(pathTree, a).size() - GraphLibPS4.getPath(pathTree, b).size()); // Sort based off size of path (actor number + 1)
                        System.out.println(actorsInRange);
                        promptUser();
                    }
                } catch (Exception e) {
                    System.out.println("Please enter in the correct format.");
                    promptUser();
                }
            }
        }

        else if(line.charAt(0) == 'u') { // If mode u. Example input: "u Sean Connery" --> make Sean Connery the center of the universe
            String name = line.substring(2); // extracts the actor name
            if(!movieActorGraph.hasVertex(name)){ // if graph does not contain actor
                System.out.println("Cannot update actor; not in dataset");
                promptUser();
            }
            else{
                updateCenter(name); // updates the center of the universe to new name
                promptUser();
            }
        }

        else if(line.charAt(0) == 'q') { // If mode q. Example input: "q" --> quit game
            System.out.println("Good bye!");
            // NOTE: no recursive prompt user call

        }

        else { // if the user-inputted first letter is not a command
            System.out.println("Please enter a valid character based off the functionality above.\n");
            promptUser();
        }
    }

    /**
     * Updates the center of the universe by running BFS. (Called upon start of the game with Kevin Bacon).
     * @param newCenter new center of the universe
     */
    public void updateCenter(String newCenter){
        centerOfUniverse = newCenter; // updates center of the universe
        pathTree = GraphLibPS4.bfs(movieActorGraph, centerOfUniverse); // recomputes the path tree

        int totalNumActors = movieActorGraph.numVertices();
        int numDisconnected = GraphLibPS4.missingVertices(movieActorGraph, pathTree).size(); // missing vertices returns set of vertices not connected to the center of the universe
        int numConnected = totalNumActors - numDisconnected;
        System.out.println(centerOfUniverse + " is now the center of the acting universe, connected to " + numConnected + "/" + totalNumActors + " actors with average separation " + GraphLibPS4.averageSeparation(pathTree, centerOfUniverse) + "\n");
    }
    public class ActorAverage {
        private String actorName;
        private double average;

        public ActorAverage(String actorName, double average) {
            this.actorName = actorName;
            this. average = average;
        }
        public String getActorName() {
            return actorName;
        }
        public double getAverage() {
            return average;
        }
        public String toString(){
            return "[" + actorName + " " + average + "]";
        }
    }
    public static void main(String[] args) throws IOException {
        BaconGame game = new BaconGame("PS4/actors.txt", "PS4/movies.txt", "PS4/movie-actors.txt"); // creates a new game

    }

}
