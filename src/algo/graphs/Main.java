package algo.graphs;


public class Main {

    static final String EOL = System.getProperty("line.separator");
    static final String COLON = ":";
    static final String SPACE = " ";

    public static void main(String[] args) {
        Graph G = new Graph(true);
        
        G.readFromFile("graph.txt");
        
        // print out G
        System.out.println(G);
        System.out.println("BFS");
        G.bfs(G.getMyVertices().get("A"));
        G.reset();
        System.out.println();
        System.out.println("DFS");
        G.dfs(G.getMyVertices().get("A"));

    }

}
