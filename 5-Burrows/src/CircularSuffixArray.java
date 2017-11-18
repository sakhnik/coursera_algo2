
import java.util.Arrays;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class CircularSuffixArray {
    Integer[] idx;

    public CircularSuffixArray(String s) {  // circular suffix array of s
        if (s == null)
            throw new IllegalArgumentException();
        idx = new Integer[s.length()];
        for (int i = 0; i < idx.length; ++i)
            idx[i] = i;

        String buffer = s + s;

        class Cmp implements Comparator<Integer>
        {            
            @Override
            public int compare(Integer i1, Integer i2)
            {
                for (int i = i1, n = i + idx.length, j = i2;
                        i < n; ++i, ++j) {
                    char c1 = buffer.charAt(i);
                    char c2 = buffer.charAt(j);
                    if (c1 == c2)
                        continue;
                    return c1 < c2 ? -1 : 1;
                }

                return 0;
            }
        }

        Arrays.sort(idx, new Cmp());
    }

    public int length() {                   // length of s
        return idx.length;
    }

    public int index(int i) {               // returns index of ith sorted suffix
        if (i < 0 || i >= idx.length)
            throw new IllegalArgumentException();
        return idx[i];
    }
}