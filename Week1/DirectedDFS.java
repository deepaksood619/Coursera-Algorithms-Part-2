import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DirectedDFS {
    private boolean[] marked;

    public DirectedDFS(DiGraph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    private void dfs(DiGraph G, int v) {
        marked[v] = true;

        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }

    public boolean visited(int v) {
        return marked[v];
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        DiGraph G = new DiGraph(in);

        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                StdOut.println(v + " -> " + w);

        DirectedDFS directedDFS = new DirectedDFS(G, 0);
        for (int v = 0; v < G.V(); v++)
            if (directedDFS.visited(v))
                StdOut.println(v);
            else
                StdOut.println("no path");
    }
}