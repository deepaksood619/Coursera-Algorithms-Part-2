/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 16-Feb-2018
 *  Last updated           : 16-Feb-2018
 * 
 *  Compilation            : javac-algs4 SAP.java
 *  Execution              : java-algs4 SAP digraph.txt
 *
 *  Purpose of the program : A program for finding Shortest Ancestoral Path from the given digraph.
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/wordnet.html
 *  Checklist link         : http://coursera.cs.princeton.edu/algs4/checklists/wordnet.html
 *  Score                  : 100/100
 *  Reference              : https://github.com/CtheSky/Coursera-Algorithms/blob/master/Assignment6_WordNet
**********************************************************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

public class SAP {
    private static final boolean REQUIRELENGTH = true;
    private static final boolean REQUIREANCESTOR = false;
    private final Digraph digraph;
    private final Set<Integer> markedVSet;
    private final Set<Integer> markedWSet;
    private int[] distToV;
    private int[] distToW;

   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G) {
        if (G == null) throw new NullPointerException();

        digraph = new Digraph(G);
        markedVSet = new HashSet<>();
        markedWSet = new HashSet<>();
        distToV = new int[G.V()];
        distToW = new int[G.V()];
        for (int i = 0; i < distToV.length; i++) {
            distToV[i] = Integer.MAX_VALUE;
            distToW[i] = Integer.MAX_VALUE;
        }
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w) {
       if (v < 0 || w < 0 || v > digraph.V() - 1 || w > digraph.V() - 1) throw new IllegalArgumentException();

       return bfs(v, w, REQUIRELENGTH);
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
       if (v < 0 || w < 0 || v > digraph.V() - 1 || w > digraph.V() - 1) throw new IllegalArgumentException();

       return bfs(v, w, REQUIREANCESTOR);
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();

        for (int x : v)
            if (x < 0 || x > digraph.V() - 1) throw new IllegalArgumentException();
        
        for (int x : w)
            if (x < 0 || x > digraph.V() - 1) throw new IllegalArgumentException();

       return bfs(v, w, REQUIRELENGTH);
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
       if (v == null || w == null) throw new IllegalArgumentException();

       for (int x : v)
            if (x < 0 || x > digraph.V() - 1) throw new IllegalArgumentException();
        
        for (int x : w)
            if (x < 0 || x > digraph.V() - 1) throw new IllegalArgumentException();

       return bfs(v, w, REQUIREANCESTOR);
   }

   private int bfs(int v, int w, boolean requiredValue) {
       List<Integer> vList = new ArrayList<>();
       List<Integer> wList = new ArrayList<>();
       vList.add(v);
       wList.add(w);
       return bfs(vList, wList, requiredValue);
   }

   private int bfs(Iterable<Integer> v, Iterable<Integer> w, boolean requiredValue) {
       if (v == null || w == null) throw new NullPointerException();

       Queue<Integer> queueV = new LinkedList<>();
       Queue<Integer> queueW = new LinkedList<>();
       int minlength = -1;
       int ancestor = -1;

       for (int s : v) {
           markedVSet.add(s);
           distToV[s] = 0;
           queueV.add(s);
       }
       for (int s : w) {
           markedWSet.add(s);
           distToW[s] = 0;
           queueW.add(s);
       }

       int layer = 0;
       while (!queueV.isEmpty() || !queueW.isEmpty()) {
           while (!queueV.isEmpty() && distToV[queueV.peek()] <= layer) {
               int current = queueV.remove();
               for (int nextLayer : digraph.adj(current)) {
                   if (!markedVSet.contains(nextLayer)) {
                       markedVSet.add(nextLayer);
                       distToV[nextLayer] = distToV[current] + 1;
                       queueV.add(nextLayer);
                   }
               }

               if (markedWSet.contains(current)) {
                   int length = distToV[current] + distToW[current];
                   if (ancestor == -1 || length < minlength) {
                       minlength = length;
                       ancestor = current;
                   }
               }
           }

           while (!queueW.isEmpty() && distToW[queueW.peek()] <= layer) {
               int current = queueW.remove();
               for (int nextLayer : digraph.adj(current)) {
                   if (!markedWSet.contains(nextLayer)) {
                       markedWSet.add(nextLayer);
                       distToW[nextLayer] = distToW[current] + 1;
                       queueW.add(nextLayer);
                   }
               }

               if (markedVSet.contains(current)) {
                   int length = distToV[current] + distToW[current];
                   if (ancestor == -1 || length < minlength) {
                       minlength = length;
                       ancestor = current;
                   }
               }
           }

           layer++;
           if (ancestor != -1 && minlength < layer) {
               queueV.clear();
               queueW.clear();
           }
       }

        for (int changed : markedVSet)
            distToV[changed] = Integer.MAX_VALUE;
        for (int changed : markedWSet)
            distToW[changed] = Integer.MAX_VALUE;
        markedVSet.clear();
        markedWSet.clear();

        if (requiredValue == REQUIRELENGTH)
            return minlength;
        else
            return ancestor;
   }

   // do unit testing of this class
   public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
