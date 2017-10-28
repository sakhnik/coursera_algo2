/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordnet) {         // constructor takes a WordNet object
        this.wordnet = wordnet;
    }
    
    public String outcast(String[] nouns) {   // given an array of WordNet nouns, return an outcast

        int dist = 0;
        String src = null;

        for (String n : nouns) {
            int d = dist(n, nouns);
            if (dist < d) {
                dist = d;
                src = n;
            }
        }
        return src;
    }

    private int dist(String src, String[] nouns) {
        int d = 0;
        for (String n : nouns) {
            d += wordnet.distance(src, n);
        }
        return d;
    }
}
