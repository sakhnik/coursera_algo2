
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class WordNet {
    
    private final Map<String, List<Integer>> nouns = new HashMap<>();
    private final List<String> synsetsList = new ArrayList<>();
    private final Digraph wordnet;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();

        In in = new In(synsets);

        while (true) {
            String line = in.readLine();
            if (line == null)
                break;
            String[] fields = line.split(",");
            for (String a : fields[1].split(" ")) {
                List<Integer> s = nouns.get(a);
                if (s == null) {
                    s = new ArrayList<>();
                    nouns.put(a, s);
                }
                s.add(Integer.valueOf(fields[0]));
            }
            synsetsList.add(fields[1]);
        }

        wordnet = new Digraph(synsetsList.size());

        in = new In(hypernyms);
        while (true) {
            String line = in.readLine();
            if (line == null)
                break;
            String[] fields = line.split(",");
            int synsetId = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; ++i) {
                int j = Integer.parseInt(fields[i]);
                wordnet.addEdge(synsetId, j);
            }
        }

        DirectedCycle cycle = new DirectedCycle(wordnet);
        if (cycle.hasCycle())
            throw new IllegalArgumentException();

        int rootCount = 0;
        for (int v = 0; v < wordnet.V(); ++v) {
            if (wordnet.outdegree(v) == 0)
                ++rootCount;
        }

        if (rootCount != 1)
            throw new IllegalArgumentException();

        sap = new SAP(wordnet);
    }
    
    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }
    
    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        return nouns.containsKey(word);
    }
    
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!nouns.containsKey(nounA) || !nouns.containsKey(nounB))
            throw new IllegalArgumentException();
        if (nounA.equals(nounB))
            return 0;
        return sap.length(nouns.get(nounA), nouns.get(nounB));
        
    }
    
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!nouns.containsKey(nounA) || !nouns.containsKey(nounB))
            throw new IllegalArgumentException();

        int vert = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
        if (-1 == vert)
            return null;
        return synsetsList.get(vert);
    }
}
