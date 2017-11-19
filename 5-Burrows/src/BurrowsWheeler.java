
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class BurrowsWheeler {
    
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        String input = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(input);
        for (int i = 0; i < input.length(); ++i) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }

        for (int i = 0; i < input.length(); ++i) {
            char ch = input.charAt((csa.index(i) + csa.length() - 1) % csa.length());
            BinaryStdOut.write(ch);
        }
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        int first = BinaryStdIn.readInt();
        String t = BinaryStdIn.readString();

        // Counting sort
        int[] counts = new int[256];
        for (int i = 0; i < t.length(); ++i)
            counts[t.charAt(i)]++;
        for (int i = 1; i < 256; ++i)
            counts[i] += counts[i-1];
        char[] sorted = new char[t.length()];
        for (int i = 0, j = 0; j < counts.length; ++j) {
            for (int k = 0; k < counts[j]; ++k)
                sorted[i++] = (char) j;
        }

        int[] next = new int[t.length()];
        for (int i = 0; i < t.length(); ++i) {
            next[counts[i]] = i;
            ++counts[i];
        }

        for (int i = 0; i < t.length(); ++i) {
            BinaryStdOut.write(sorted[first]);
            first = next[first];
        }
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if (args[0].equals("-"))
            encode();
        else if (args[0].equals("+"))
            decode();
    }
}
