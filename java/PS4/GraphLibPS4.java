import java.util.*;
/**
 * Library with functionality for PS 4
 * @author Ben Sheppard
 * @author Tara Salli
 */
public class GraphLibPS4 {
    /**
     * Runs BFS on a graph, given a source vertex
     * @param g graph to run on
     * @param source the source vertex in the graph
     * @return a graph which represents a tree of the shortest paths from every vertex to the source
     * @param <V> vertex type
     * @param <E> edge type
     */
    public static <V,E> Graph<V,E> bfs(Graph<V,E> g, V source) {
        Graph<V,E> graphTree = new AdjacencyMapGraph<V,E>(); // Graph to return
        graphTree.insertVertex(source); // Insert source to Graph
        Set<V> visited = new HashSet<V>(); // Set of visited vertices
        Queue<V> queue = new LinkedList<V>(); // Queue for BFS

        queue.add(source); // Add source to queue initially
        visited.add(source); // Mark source as visited

        while (!queue.isEmpty()) {
            V current = queue.remove(); // Remove from queue
            for (V v : g.outNeighbors(current)) { // Loop over neighbors of current
                if (!visited.contains(v)) { // If unvisited
                    visited.add(v); // Mark as visited
                    queue.add(v); // Add neighbor to queue
                    graphTree.insertVertex(v); // Insert neighbor to graph
                    graphTree.insertDirected(v, current, g.getLabel(v, current)); // Node between neighbor and current, same edge label as in main graph
                }
            }
        }
        return graphTree;
    }

    /**
     * Gets a path from given vertex to the source node of the shortest path tree
     * @param tree shortest path tre
     * @param v vertex to find path from
     * @return List representing path
     * @param <V> Vertex type
     * @param <E> Edge type
     */
    public static <V,E> List<V> getPath(Graph<V,E> tree, V v) {
        List<V> path = new ArrayList<V>(); // Initializes List for path
        path.add(v); // Adds the starting vertex
        V currentNode = v;
        while (tree.outDegree(currentNode)!=0){ // While not at the root of shortest path tree
            for (V neighbor : tree.outNeighbors(currentNode)) { // Go to neighbor (each vertex only has one out neighbor)
                path.add(neighbor); // Add that neighbor on the path
                currentNode = neighbor; // Update the current node to be that neighbor
            }
        }
        return path;
    }

    /**
     * Find vertices that are in a graph, but not in the subgraph
     * @param graph graph
     * @param subgraph subgraph
     * @return set of vertices
     * @param <V> vertex type
     * @param <E> edge label type
     */
    public static <V,E> Set<V> missingVertices(Graph<V,E> graph, Graph<V,E> subgraph) {
        Set<V> missing = new HashSet<V>(); // initializes set

        for (V v : graph.vertices()) { // adds all vertices in the graph to the set
            missing.add(v);
        }

        for (V v : subgraph.vertices()) { // removes all vertices in the subgraph from the set
            missing.remove(v);
        }
        return missing;
    }

    /**
     * Find the average-distance from root in a shortest path Tree. Does not include the root vertex in the average.
     * @param tree Shortest path tree
     * @param root Root vertex
     * @return Average distance from root
     * @param <V> vertex type
     * @param <E> edge label type
     */
    public static <V,E> double averageSeparation(Graph<V,E> tree, V root) {
        int totalEdgesOnPaths = averageSeparationHelper(tree, root, 1);
        return (double)totalEdgesOnPaths / (tree.numVertices() - 1);
    }

    /**
     * Helper for averageSeparation method.
     * @param tree shortest path tree
     * @param root root
     * @param level current level - increases as helper is recursively called
     * @return total edges from root to nodes at level
     * @param <V> vertex type
     * @param <E> edge type
     */
    public static <V,E> int averageSeparationHelper(Graph<V,E> tree, V root, int level){
        int inDegree = tree.inDegree(root);
        int total = level * inDegree; //  Uses formula: total edges = inDegree x level
        for(V neighbor : tree.inNeighbors(root)){
            total += averageSeparationHelper(tree, neighbor, level + 1); // recursively calls helper on neighbor at the next level out
        }
        return total;
    }

    /**
     * Tester for the Library
     * @param args
     */
    public static void main(String[] args) {
        Graph<String, Set<String>> g = new AdjacencyMapGraph<String, Set<String>>();
        g.insertVertex("Dartmouth (Earl thereof)");
        g.insertVertex("Charlie");
        g.insertVertex("Bob");
        g.insertVertex("Alice");
        g.insertVertex("Kevin Bacon");
        g.insertVertex("Nobody");
        g.insertVertex("Nobody's friend");

        Set<String> edge1 = new HashSet<String>();
        edge1.add("A Movie");

        Set<String> edge2 = new HashSet<String>();
        edge2.add("A Movie");
        edge2.add("E Movie");

        Set<String> edge3 = new HashSet<String>();
        edge3.add("C Movie");

        Set<String> edge4 = new HashSet<String>();
        edge4.add("D Movie");

        Set<String> edge5 = new HashSet<String>();
        edge5.add("B Movie");

        Set<String> edge6 = new HashSet<String>();
        edge6.add("F Movie");

        g.insertUndirected("Kevin Bacon", "Bob", edge1);
        g.insertUndirected("Kevin Bacon", "Alice", edge2);
        g.insertUndirected("Alice", "Bob", edge1);
        g.insertUndirected("Charlie", "Bob", edge3);
        g.insertUndirected("Alice", "Charlie", edge4);
        g.insertUndirected("Charlie", "Dartmouth (Earl thereof)", edge5);
        g.insertUndirected("Nobody", "Nobody's friend", edge6);

        System.out.println("Get Path: " + getPath(bfs(g, "Kevin Bacon"), "Dartmouth (Earl thereof)"));
        System.out.println("BFS " + bfs(g, "Kevin Bacon"));
        System.out.println("Average Separation Kevin Bacon: " + averageSeparation(bfs(g, "Kevin Bacon"), "Kevin Bacon")); // Should be 1.75
        System.out.println("Average Separation Nobody: " + averageSeparation(bfs(g, "Nobody"), "Nobody")); // Should be 1 - only connected to one other
        System.out.println("Missing Vertices Kevin Bacon: " + missingVertices(g, bfs(g, "Kevin Bacon"))); // Should be Nobody and Nobody's Friend
        System.out.println("Missing Vertices Nobody: " + missingVertices(g, bfs(g, "Nobody"))); // Should be all except Nobody and Nobody's Friend
    }
}
