package algo.graphs;

/**
 * http://homepage.cs.uiowa.edu/~hzhang/c31/code/Graph.java
 * 
 * Undirected, unweighted simple graph data type
 * <p>
 *  Notes:
 *  <ul>
 *   <li> Parallel edges are not allowed
 *   <li> Self loops are allowed
 *  </ul>
 *  <p>
 *  This Graph class was adapted from 
 *  <a href="http://www.cs.princeton.edu/introcs/45graph/Graph.java">Graph.java</a> by 
 *  by Robert Sedgewick and Kevin Wayne
 */
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

import algo.graphs.Vertex.Color;

public class Graph {

	private HashMap<Vertex, TreeSet<Vertex>> myAdjList;
	private HashMap<String, Vertex> myVertices;
	private static final TreeSet<Vertex> EMPTY_SET = new TreeSet<Vertex>();
	private int myNumVertices;
	private int myNumEdges;

	/**
	 * Construct empty Graph
	 */
	public Graph() {
		myAdjList = new HashMap<Vertex, TreeSet<Vertex>>();
		myVertices = new HashMap<String, Vertex>();
		myNumVertices = myNumEdges = 0;

	}

	public HashMap<String, Vertex> getMyVertices() {
		return myVertices;
	}

	/**
	 * Add a new vertex name with no neighbors (if vertex does not yet exist)
	 * 
	 * @param name
	 *            vertex to be added
	 */
	public Vertex addVertex(String name) {
		Vertex v;
		v = myVertices.get(name);
		if (v == null) {
			v = new Vertex(name);
			myVertices.put(name, v);
			myAdjList.put(v, new TreeSet<Vertex>());
			myNumVertices += 1;
		}
		return v;
	}

	/**
	 * Returns the Vertex matching v
	 * 
	 * @param name
	 *            a String name of a Vertex that may be in this Graph
	 * @return the Vertex with a name that matches v or null if no such Vertex
	 *         exists in this Graph
	 */
	public Vertex getVertex(String name) {
		return myVertices.get(name);
	}

	/**
	 * Returns true iff v is in this Graph, false otherwise
	 * 
	 * @param name
	 *            a String name of a Vertex that may be in this Graph
	 * @return true iff v is in this Graph
	 */
	public boolean hasVertex(String name) {
		return myVertices.containsKey(name);
	}

	/**
	 * Is from-to, an edge in this Graph. The graph is undirected so the order
	 * of from and to does not matter.
	 * 
	 * @param from
	 *            the name of the first Vertex
	 * @param to
	 *            the name of the second Vertex
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
	 *            the name of the first Vertex
	 * @param to
	 *            the name of the second Vertex
	 */
	public void addEdge(String from, String to) {
		Vertex v, w;
		if (hasEdge(from, to))
			return;
		myNumEdges += 1;
		if ((v = getVertex(from)) == null)
			v = addVertex(from);
		if ((w = getVertex(to)) == null)
			w = addVertex(to);
		myAdjList.get(v).add(w);
		myAdjList.get(w).add(v);
	}

	/**
	 * Return an iterator over the neighbors of Vertex named v
	 * 
	 * @param name
	 *            the String name of a Vertex
	 * @return an Iterator over Vertices that are adjacent to the Vertex named
	 *         v, empty set if v is not in graph
	 */
	public Iterable<Vertex> adjacentTo(String name) {
		if (!hasVertex(name))
			return EMPTY_SET;
		return myAdjList.get(getVertex(name));
	}

	/**
	 * Return an iterator over the neighbors of Vertex v
	 * 
	 * @param v
	 *            the Vertex
	 * @return an Iterator over Vertices that are adjacent to the Vertex v,
	 *         empty set if v is not in graph
	 */
	public Iterable<Vertex> adjacentTo(Vertex v) {
		if (!myAdjList.containsKey(v))
			return EMPTY_SET;
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

	public void bfs(Vertex root) {
		System.out.println(root);
		Queue<Vertex> queue = new LinkedList<Vertex>();
		queue.add(root);
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

	public void dfs(Vertex root) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		Graph G = new Graph();
		G.addEdge("A", "B");
		G.addEdge("A", "C");
		G.addEdge("C", "D");
		G.addEdge("D", "E");
		G.addEdge("D", "G");
		G.addEdge("E", "G");
		G.addVertex("H");
		// print out G
		System.out.println(G);

		G.bfs(G.myVertices.get("D"));
	}
}