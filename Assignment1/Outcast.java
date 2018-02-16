/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 16-Feb-2018
 *  Last updated           : 16-Feb-2018
 * 
 *  Compilation            : javac-algs4 Outcast.java
 *  Execution              : java-algs4 Outcast synsets.txt hypernyms.txt outcast5.txt outcast8.txt outcast11.txt
 *
 *  Purpose of the program : A program for finding the outcast using semantic relatedness from a given set of nouns.
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/wordnet.html
 *  Checklist link         : http://coursera.cs.princeton.edu/algs4/checklists/wordnet.html
 *  Score                  : 100/100
 *  Reference              : https://github.com/CtheSky/Coursera-Algorithms/blob/master/Assignment6_WordNet
**********************************************************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int max = -1;
        String result = null;

        for (String noun : nouns) {
            int sum = 0;
            for (String other : nouns)
                sum += wordnet.distance(noun, other);

            if (sum > max) {
                max = sum;
                result = noun;
            } 
        }

        return result;
    }

    // test client
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
