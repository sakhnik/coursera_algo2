
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.Digraph;
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

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();

        In in = new In(synsets);

        String line = null;
        while ((line = in.readLine()) != null) {
            String[] fields = line.split(",");
            for (String a : fields[1].split(" ")) {
                List<Integer> s = nouns.get(a);
                if (s == null) {
                    s = new ArrayList<>();
                    nouns.put(a, s);
                }
                s.add(Integer.parseInt(fields[0]));
            }
            synsetsList.add(fields[1]);
        }

        wordnet = new Digraph(synsetsList.size());

        in = new In(hypernyms);
        while ((line = in.readLine()) != null) {
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; ++i) {
                int j = Integer.parseInt(fields[i]);
                wordnet.addEdge(i, j);
            }
        }
    }
    
    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }
    
    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nouns.containsKey(word);
    }
    
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!nouns.containsKey(nounA) || !nouns.containsKey(nounB))
            throw new IllegalArgumentException();
        if ((Object)nounA == (Object)nounB || nounA.equals(nounB))
            return 0;
        SAP sap  = new SAP(wordnet);
        return sap.length(nouns.get(nounA), nouns.get(nounB));
        
    }
    
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!nouns.containsKey(nounA) || !nouns.containsKey(nounB))
            throw new IllegalArgumentException();

        SAP sap = new SAP(wordnet);
        int vert = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
        if (-1 == vert)
            return null;
        return synsetsList.get(vert);
    }
}
