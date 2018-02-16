/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 16-Feb-2018
 *  Last updated           : 16-Feb-2018
 * 
 *  Compilation            : javac-algs4 WordNet.java
 *  Execution              : java-algs4 WordNet
 *
 *  Purpose of the program : A program for creating WordNet digraph. Use SAP for finding Shortest Ancestoral Path.
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/wordnet.html
 *  Checklist link         : http://coursera.cs.princeton.edu/algs4/checklists/wordnet.html
 *  Score                  : 100/100
 *  Reference              : https://github.com/CtheSky/Coursera-Algorithms/blob/master/Assignment6_WordNet
**********************************************************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;

public class WordNet {

    private final Map<String, Set<Integer>> noun2synsetIdSet;
    private final Map<Integer, String> id2synset;
    private final Digraph digraph;
    private final SAP sap;

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {
        if (synsets == null || synsets.equals("") || hypernyms == null || hypernyms.equals("")) throw new NullPointerException("file names must be passed");

        noun2synsetIdSet = new HashMap<>();
        id2synset = new HashMap<>();

        // adding synsets data to noun2synsetIdSet and id2synset
        In synsetsInput = new In(synsets);
        for (String line : synsetsInput.readAllLines()) {
            String[] arr = line.split(",");
            int synsetId = Integer.parseInt(arr[0]);
            
            for (String noun : arr[1].split(" ")) {
                if (noun2synsetIdSet.get(noun) == null)
                    noun2synsetIdSet.put(noun, new HashSet<>());
                noun2synsetIdSet.get(noun).add(synsetId);
            }
            id2synset.put(synsetId, arr[1]);
        }

        // adding hypernyms data
        In hypernymsInput = new In(hypernyms);
        String[] lines = hypernymsInput.readAllLines();
        digraph = new Digraph(id2synset.size());

        for (String line : lines) {
            String[] arr = line.split(",");
            int synsetId = Integer.parseInt(arr[0]);

            for (int i = 1; i < arr.length; i++)
                digraph.addEdge(synsetId, Integer.parseInt(arr[i]));
        }

        // check whether the given digraph has two roots
        int count = 0;
        for (int v = 0; v < digraph.V(); v++) {
            if (digraph.outdegree(v) == 0) {
                count++;
                if (count > 1) {
                    throw new IllegalArgumentException();
                }
            }
        }

        // check whether the give digraph has cycles
        DirectedCycle circleChecker = new DirectedCycle(digraph);
        if (circleChecker.hasCycle()) throw new IllegalArgumentException();

        // initialize sap
        sap = new SAP(digraph);
   }

   // returns all WordNet nouns
   public Iterable<String> nouns() {
       return noun2synsetIdSet.keySet();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
       if (word == null) throw new IllegalArgumentException();

       return noun2synsetIdSet.containsKey(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
       if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

       Set<Integer> synsetA = noun2synsetIdSet.get(nounA);
       Set<Integer> synsetB = noun2synsetIdSet.get(nounB);

       return sap.length(synsetA, synsetB);
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
       if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

       Set<Integer> synsetA = noun2synsetIdSet.get(nounA);
       Set<Integer> synsetB = noun2synsetIdSet.get(nounB);

       int ancestorId = sap.ancestor(synsetA, synsetB);
       return id2synset.get(ancestorId);
   }

   // do unit testing of this class
   public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets15.txt", "hypernyms15Tree.txt");
        // WordNet wordNet = new WordNet("synsets3.txt", "hypernyms3InvalidTwoRoots.txt");
        // WordNet wordNet = new WordNet("synsets6.txt", "hypernyms6InvalidTwoRoots.txt");
        // WordNet wordNet = new WordNet("synsets3.txt", "hypernyms3InvalidCycle.txt");
        StdOut.println(wordNet.distance("b", "f"));
   }
}
