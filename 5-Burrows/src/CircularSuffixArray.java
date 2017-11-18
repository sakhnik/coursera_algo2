
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

        class Cmp implements Comparator<Integer>
        {            
            @Override
            public int compare(Integer i1, Integer i2)
            {
                int a = i1;
                int b = i2;
                for (int i = 0; i < s.length(); ++i) {
                    char c1 = s.charAt(a);
                    char c2 = s.charAt(b);
                    if (c1 == c2) {
                        if (++a >= s.length())
                            a = 0;
                        if (++b >= s.length())
                            b = 0;
                        continue;
                    }
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