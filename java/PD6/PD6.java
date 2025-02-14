import com.sun.security.jgss.GSSUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PD6 {
    /**
     * Find all connected groups in a graph of cities.
     *
     * @author Tim Pierson, Dartmouth CS10, Winter 2024, based on idea from Pratim Chowdhary
     *

        /**
         * Find all groups (sometimes called cliques) of cities connected to each other
         *
         * @param graph AdjacencyMapGraph of cities as String vertices
         * @return List where each entry is a List of connected cities.
         */
    //loop over all vertices in graph
        //skip if vertex already visited
        //if found unvisited vertex, create new list of connected cities
            //create list of vertices toVisit, initial just this one
            //loop until no vertices toVisit
                //remove vertex to visit, mark visited, add to list of connected cities
                //loop over neighbors
                    //add unvisited neighbors toVisit
                    //add list to groups
        public static List<ArrayList<String>> findGroups(Graph<String, String> graph) {
            List<ArrayList<String>> groups = new ArrayList<ArrayList<String>>(); //each group is a clique
            Set<String> visited = new HashSet<String>(); //track cities visited
            //TODO: Your code here
            for (String v : graph.vertices()) {
                if (!visited.contains(v)) {
                    ArrayList<String> connectedCities = new ArrayList<String>();
                    ArrayList<String> toVisit = new ArrayList<String>();
                    toVisit.add(v);
                    while (!toVisit.isEmpty()) {
                        String removed = toVisit.remove(0);
                        visited.add(removed);
                        connectedCities.add(removed);
                        for (String neighbor : graph.outNeighbors(v)) {
                            if(!visited.contains(neighbor))
                                toVisit.add(neighbor);
                        }
                    }
                    groups.add(connectedCities);
                }
            }
            return groups;
        }

        public static void main(String[] args) {
            Graph<String, String> g = new AdjacencyMapGraph<String, String>();

            //add vertices
            g.insertVertex("Hanover");
            g.insertVertex("Boston");
            g.insertVertex("Washington D.C.");
            g.insertVertex("Los Angeles");
            g.insertVertex("Paris");
            g.insertVertex("Berlin");
            g.insertVertex("Sydney");
            g.insertVertex("Melbourne");
            g.insertVertex("Honolulu");

            //add edges (not using edge lables in this graph)
            g.insertUndirected("Hanover", "Boston", null);
            g.insertUndirected("Boston", "Washington D.C.", null);
            g.insertUndirected("Washington D.C.", "Los Angeles", null);
            g.insertUndirected("Los Angeles", "Hanover", null);
            g.insertUndirected("Paris", "Berlin", null);
            g.insertUndirected("Sydney", "Melbourne", null);
            //find connected groups
            System.out.println(PD6.findGroups(g));
        }
}
