import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class NonRecursiveDFS {

    private boolean[] marked;
    private int s;

    public NonRecursiveDFS(Graph G, int s) {
        marked = new boolean[G.V()];
        this.s = s;
        dfs(G, s);
    }

    public void dfs(Graph G, int v) {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(v);

        while(!stack.isEmpty()) {
            v = stack.pop();
            marked[v] = true;
            for (int w : G.adj(v)) {
                if (!marked[w])
                    stack.push(w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    // test client
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);

        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                StdOut.println(v + " - " + w);

        NonRecursiveDFS paths = new NonRecursiveDFS(G, 1);
        for (int v = 0; v < G.V(); v++)
            if (paths.hasPathTo(v))
                StdOut.println(v);
            else
                StdOut.println("no path");
    }
}