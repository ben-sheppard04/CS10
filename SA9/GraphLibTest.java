public class GraphLibTest {
    public static void main(String[] args) {
        Graph<String, Boolean> graph = new AdjacencyMapGraph<String, Boolean>();
        System.out.println("randomWalk Method:");
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");

        graph.insertDirected("A", "B", false);
        graph.insertDirected("A", "C", false);
        graph.insertDirected("A", "D", false);
        graph.insertDirected("A", "E", false);
        graph.insertDirected("B", "A", false);
        graph.insertDirected("B", "C", false);
        graph.insertDirected("C", "A", false);
        graph.insertDirected("C", "B", false);
        graph.insertDirected("C", "D", false);
        graph.insertDirected("E", "B", false);
        graph.insertDirected("E", "C", false);
        System.out.println("All three same starting node and num steps:");
        System.out.println(GraphLib.randomWalk(graph, "A", 3));
        System.out.println(GraphLib.randomWalk(graph, "A", 3));
        System.out.println(GraphLib.randomWalk(graph, "A", 3));
        System.out.println(GraphLib.randomWalk(graph, "A", 3));
        System.out.println();
        System.out.println("Assortment of others");
        System.out.println(GraphLib.randomWalk(graph, "Z", 3)); // Z not in graph
        System.out.println(GraphLib.randomWalk(graph, "D", 3)); // Should just return D because has no outDegrees
        System.out.println(GraphLib.randomWalk(graph, "E", 4)); // Always must go to B first
        System.out.println(GraphLib.randomWalk(graph, "E", 4)); // Always must go to B first
        System.out.println(GraphLib.randomWalk(graph, "E", 4)); // Always must go to B first
        System.out.println(GraphLib.randomWalk(graph, "B", 10));
        System.out.println(GraphLib.randomWalk(graph, "B", 10));
        System.out.println(GraphLib.randomWalk(graph, "C", 1));
        System.out.println();
        System.out.println("verticesByInDegree Method:");
        System.out.println(GraphLib.verticesByInDegree(graph));
    }
}
