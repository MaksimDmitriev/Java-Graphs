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
import algo.graphs.Vertex.Color;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {

	private HashMap<Vertex, LinkedList<Vertex>> myAdjList;
	private HashMap<String, Vertex> myVertices;
	private static final List<Vertex> EMPTY_LIST = new LinkedList<Vertex>();
	private int myNumVertices;
	private int myNumEdges;
	private final boolean mIsDirected;

	/**
	 * Construct empty Graph
	 * @param directed if this graph is directed
	 */
	public Graph(boolean directed) {
		myAdjList = new HashMap<Vertex, LinkedList<Vertex>>();
		myVertices = new HashMap<String, Vertex>();
		myNumVertices = myNumEdges = 0;
		mIsDirected = directed;
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
			myAdjList.put(v, new LinkedList<Vertex>());
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
	 * Is from-to, an edge in this Graph
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
	 *            the String name of a Vertex
	 * @return an Iterator over Vertices that are adjacent to the Vertex named
	 *         v, empty set if v is not in graph
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
	 *            the Vertex
	 * @return an Iterator over Vertices that are adjacent to the Vertex v,
	 *         empty set if v is not in graph
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
}