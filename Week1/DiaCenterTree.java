/*
Diameter and center of a tree. Given a connected graph with no cycles, find
    Diameter: design a linear-time algorithm to find the longest simple path in the graph.
    Center: design a linear-time algorithm to find a vertex such that its maximum distance from any other vertex is minimized.
*/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class DiaCenterTree {

    public boolean[] marked;
    public int s;

    public DiaCenterTree(Graph G, int s) {
        marked = new boolean[G.V()];
        this.s = s;

        // dfs(G, s);
        // nonRecursiveDfs(G, s);
        // maxDepthNonRecursiveDfs(G, s);
        // maxDepthDfs(G, s, 1);
        maxDepthBfs(G, s, 1);
        // bfs(G, s);
    }

    public void maxDepthNonRecursiveDfs(Graph G, int s) {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);
        int maxDepth = 0;

        while(!stack.isEmpty()) {
            int v = stack.pop();
            maxDepth++;
            marked[v] = true;
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    stack.push(w);
                }
            }
        }
    }

    public int maxLevel = 0;
    public void maxDepthDfs(Graph G, int v, int level) {
        if (level > maxLevel)
            maxLevel = level;
        marked[v] = true;

        for (int w : G.adj(v))
            if (!marked[w]) {
                maxDepthDfs(G, w, level+1);   
            }
    }

    public void maxDepthBfs(Graph G, int v, int level) {
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(v);
        marked[v] = true;
        while(!queue.isEmpty()) {
            v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[v]) {
                    queue.enqueue(w);
                    marked[w] = true;
                }
            }
        }
    }

    public void dfs(Graph G, int v) {
        marked[v] = true;

        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }

    public void nonRecursiveDfs(Graph G, int v) {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(v);

        while(!stack.isEmpty()) {
            v = stack.pop();
            marked[v] = true;
            for (int w : G.adj(v))
                if (!marked[w])
                    stack.push(w);
        }
    }

    public void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(s);
        marked[s] = true;
        while(!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    queue.enqueue(w);
                    marked[w] = true;
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);

        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                StdOut.println(v + " - " + w);

        DiaCenterTree paths = new DiaCenterTree(G, 0);
        for (int v = 0; v < G.V(); v++)
            if (paths.hasPathTo(v))
                StdOut.println(v);
            else
                StdOut.println("no path");

        StdOut.println("Max level - " + paths.maxLevel);
    }
}