package algo.graphs;

/**
 * http://homepage.cs.uiowa.edu/~hzhang/c31/code/Vertex.java
 * 
 * A C-style struct definition of a Vertex to be used with the Graph class.
 * <p>
 * The distance field is designed to hold the length of the shortest unweighted
 * path from the source of the traversal
 * <p>
 * The predecessor field refers to the previous field on the shortest path from
 * the source (i.e. the vertex one edge closer to the source).
 *
 */
public class Vertex implements Comparable<Vertex> {
	/**
	 * label for Vertex
	 */
	private String name;
	/**
	 * length of shortest path from source
	 */
	private int distance;
	/**
	 * previous vertex on path from sourxe
	 */
	private Vertex predecessor; // previous vertex

	private Color color;
	/**
	 * Infinite distance indicates that there is no path from the source to this
	 * vertex
	 */
	public static final int INFINITY = Integer.MAX_VALUE;

	public Vertex(String v) {
		name = v;
		distance = INFINITY; // start as infinity away
		predecessor = null;
		color = Color.WHITE;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Vertex getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(Vertex predecessor) {
		this.predecessor = predecessor;
	}

	/**
	 * The name of the Vertex is assumed to be unique, so it is used as a
	 * HashCode
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Compare on the basis of distance from source first and then
	 * lexicographically
	 */
	@Override
	public int compareTo(Vertex other) {
		int diff = distance - other.distance;
		if (diff != 0)
			return diff;
		else
			return name.compareTo(other.name);
	}

	enum Color {
		WHITE,
		GREY,
		BLACK
	}
}