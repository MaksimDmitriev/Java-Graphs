package algo.graphs;

/**
 * http://homepage.cs.uiowa.edu/~hzhang/c31/code/Graph.java
 * 
 * Undirected, unweighted simple graph data type
 * <p>
 * Notes:
 * <ul>
 * <li> Parallel edges are not allowed
 * <li> Self loops are allowed
 * </ul>
 * <p>
 * This Graph class was adapted from
 * <a href="http://www.cs.princeton.edu/introcs/45graph/Graph.java">Graph.java</a> by
 * by Robert Sedgewick and Kevin Wayne
 */
import algo.graphs.Vertex.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph {

    private HashMap<Vertex, LinkedList<Vertex>> myAdjList;
    private HashMap<String, Vertex> myVertices;
    private static final List<Vertex> EMPTY_LIST = new LinkedList<Vertex>();
    private static final Pattern ADJ_LIST_PATTERN = Pattern.compile("^([a-z]+):\\s(.+)$", Pattern.CASE_INSENSITIVE);
    private int myNumVertices;
    private int myNumEdges;
    private boolean mIsDirected;

    /**
     * Construct empty Graph
     * 
     * @param directed if this graph is directed
     */
    public Graph(boolean directed) {
        myAdjList = new HashMap<Vertex, LinkedList<Vertex>>();
        myVertices = new HashMap<String, Vertex>();
        myNumVertices = myNumEdges = 0;
        mIsDirected = directed;
    }

    void readFromFile(String fileName) {
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = fileReader.readLine()) != null) {
                Matcher matcher = ADJ_LIST_PATTERN.matcher(line);
                if (matcher.find()) {
                    String vertexName = matcher.group(1);
                    LinkedList<String> adjList = new LinkedList<String>(Arrays.asList(matcher.group(2).split(" ")));
                    for (String neighbor : adjList) {
                        addEdge(vertexName, neighbor);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {}
            }
        }
    }

    public HashMap<String, Vertex> getMyVertices() {
        return myVertices;
    }

    /**
     * Add a new vertex name with no neighbors (if vertex does not yet exist)
     * 
     * @param name
     * vertex to be added
     */
    public Vertex addVertex(String name) {
        Vertex v;
        v = myVertices.get(name);
        if (v == null) {
            v = new Vertex(name);
            myVertices.put(name, v);
            myAdjList.put(v, new LinkedList<Vertex>());
            myNumVertices += 1;
        }
        return v;
    }

    /**
     * Returns the Vertex matching v
     * 
     * @param name
     * a String name of a Vertex that may be in this Graph
     * @return the Vertex with a name that matches v or null if no such Vertex
     * exists in this Graph
     */
    public Vertex getVertex(String name) {
        return myVertices.get(name);
    }

    /**
     * Returns true iff v is in this Graph, false otherwise
     * 
     * @param name
     * a String name of a Vertex that may be in this Graph
     * @return true iff v is in this Graph
     */
    public boolean hasVertex(String name) {
        return myVertices.containsKey(name);
    }

    /**
     * Is from-to, an edge in this Graph
     * 
     * @param from
     * the name of the first Vertex
     * @param to
     * the name of the second Vertex
     * @return true iff from-to exists in this Graph
     */
    public boolean hasEdge(String from, String to) {
        if (!hasVertex(from) || !hasVertex(to))
            return false;
        return myAdjList.get(myVertices.get(from)).contains(myVertices.get(to));
    }

    /**
     * Add to to from's set of neighbors, and add from to to's set of neighbors.
     * Does not add an edge if another edge already exists
     * 
     * @param from
     * the name of the first Vertex
     * @param to
     * the name of the second Vertex
     */
    public void addEdge(String from, String to) {
        Vertex fromV, toV;
        if (hasEdge(from, to))
            return;
        myNumEdges += 1;
        if ((fromV = getVertex(from)) == null)
            fromV = addVertex(from);
        if ((toV = getVertex(to)) == null)
            toV = addVertex(to);
        myAdjList.get(fromV).add(toV);
        if (!mIsDirected) {
            myAdjList.get(toV).add(fromV);
        }
    }

    /**
     * Return an iterator over the neighbors of Vertex named v
     * 
     * @param name
     * the String name of a Vertex
     * @return an Iterator over Vertices that are adjacent to the Vertex named
     * v, empty set if v is not in graph
     */
    public Iterable<Vertex> adjacentTo(String name) {
        if (!hasVertex(name))
            return EMPTY_LIST;
        return myAdjList.get(getVertex(name));
    }

    /**
     * Return an iterator over the neighbors of Vertex v
     * 
     * @param v
     * the Vertex
     * @return an Iterator over Vertices that are adjacent to the Vertex v,
     * empty set if v is not in graph
     */
    public Iterable<Vertex> adjacentTo(Vertex v) {
        if (!myAdjList.containsKey(v))
            return EMPTY_LIST;
        return myAdjList.get(v);
    }

    /**
     * Returns an Iterator over all Vertices in this Graph
     * 
     * @return an Iterator over all Vertices in this Graph
     */
    public Iterable<Vertex> getVertices() {
        return myVertices.values();
    }

    public int numVertices() {
        return myNumVertices;
    }

    public int numEdges() {
        return myNumEdges;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Vertex v : myVertices.values()) {
            builder.append(v).append(Main.COLON).append(Main.SPACE);

            for (Vertex w : myAdjList.get(v)) {
                builder.append(w).append(Main.SPACE);
            }
            builder.append(Main.EOL);
        }
        return builder.toString();
    }

    public void bfs(Vertex start) {
        System.out.println(start);
        Queue<Vertex> queue = new LinkedList<Vertex>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex u = queue.remove();
            for (Vertex v : myAdjList.get(u)) {
                if (v.getColor() == Color.WHITE) {
                    v.setColor(Color.GREY);
                    v.setDistance(u.getDistance() + 1);
                    v.setPredecessor(u);
                    queue.add(v);
                    System.out.println(v);
                }
            }
            u.setColor(Color.BLACK);
        }
    }

    public void dfs(Vertex v) {
        System.out.println(v);
        v.setColor(Color.GREY); // We are going to scan v's adjacency list
        for (Vertex u : myAdjList.get(v)) {
            u.setPredecessor(v);
            if (u.getColor() == Color.WHITE) {
                dfs(u);
            }
        }
        v.setColor(Color.BLACK); // We scanned v's adjacency list
    }

    /**
     * Calls {@link Vertex#setColor(Color)} with {@link Color#WHITE} for all vertexes of the graph
     */
    public void reset() {
        for (Vertex vertex : myVertices.values()) {
            vertex.setColor(Color.WHITE);
        }
    }

    public void transpose() {

    }
}