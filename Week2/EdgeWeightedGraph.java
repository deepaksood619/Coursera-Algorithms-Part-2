import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class EdgeWeightedGraph {
    private final int V;
    private final int E;
    private final Bag<Edge>[] adj;

    public EdgeWeightedGraph(int V, int E) {
        this.V = V;
        this.E = E;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Edge>();
    }

    public EdgeWeightedGraph(In in) {
        this(in.readInt(), in.readInt());

        for (int e = 0; e < E; e++) {
            this.addEdge(new Edge(in.readInt(), in.readInt(), in.readDouble()));
        }
    }

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops= 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                } else if (e.other(v) == v) {
                    if (selfLoops%2 == 0) 
                        list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    public int V() {
        return this.V;
    }

    public int E() {
        return 0;
    }

    public String toString() {
        return "";
    }

}