import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;

public class DiGraph {
    private int V;
    private int E;
    private Bag<Integer>[] adj;

    public DiGraph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    public DiGraph(In in) {
        this(in.readInt());
        this.E = in.readInt();

        for (int e = 0; e < E; e++)
            this.addEdge(in.readInt(), in.readInt());
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public int V() {
        return this.V;
    }

    public int E() {
        return this.E;
    }

    public DiGraph reverse() {
        return null;
    }

    public String toString() {
        return "";
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        DiGraph G = new DiGraph(in);

        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                StdOut.println(v + " -> " + w);
    }
}