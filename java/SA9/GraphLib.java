import java.util.*;

/**
 * Library for graph analysis
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2016
 * @author Tim Pierson, Dartmouth CS10, provided for Winter 2024
 * @author Ben Sheppard
 */
public class GraphLib {
    /**
     * Takes a random walk from a vertex, up to a given number of steps
     * So a 0-step path only includes start, while a 1-step path includes start and one of its out-neighbors,
     * and a 2-step path includes start, an out-neighbor, and one of the out-neighbor's out-neighbors
     * Stops earlier if no step can be taken (i.e., reach a vertex with no out-edge)
     * @param g		graph to walk on
     * @param start	initial vertex (assumed to be in graph)
     * @param steps	max number of steps
     * @return		a list of vertices starting with start, each with an edge to the sequentially next in the list;
     * 			    null if start isn't in graph
     */
    public static <V,E> List<V> randomWalk(Graph<V,E> g, V start, int steps) {
        List<V> walk = new ArrayList<V>();
        if(!g.hasVertex(start)){
            return null;
        }
        walk.add(start);
        V curr = start;
        int stepsTaken = 0;
        while(stepsTaken < steps) { // Only has to add steps more vertices to walk, not steps + 1 because start vertex was already added.
            if(g.outDegree(curr) != 0){
                Iterable<V> iter = g.outNeighbors(curr);
                List<V> neighborList = new ArrayList<V>();
                for (V v : iter) {
                    neighborList.add(v);
                }
                V next = neighborList.get((int)(Math.random() * neighborList.size())); // Gets random element V from the list of all neighbors
                walk.add(next); // Adds this random element to walk
                curr = next; // Makes curr into that random element so will check that one's neighbors next time while loop iterates
                stepsTaken++; // Increases steps taken.
            }
            else{
                break;
            }
        }
        return walk;
    }

    /**
     * Orders vertices in decreasing order by their in-degree
     * @param g		graph
     * @return		list of vertices sorted by in-degree, decreasing (i.e., largest at index 0)
     */
    public static <V,E> List<V> verticesByInDegree(Graph<V,E> g) {
        List<V> vertexList = new ArrayList<V>();
        Iterable<V> vertexSet = g.vertices();
        for(V vertex : vertexSet){
            vertexList.add(vertex);
        }
        vertexList.sort((V v1, V v2) -> (g.inDegree(v2) - g.inDegree(v1))); // in line function for comparator.
        return vertexList;
    }
}