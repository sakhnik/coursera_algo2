
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

        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        int first = BinaryStdIn.readInt();
        String t = BinaryStdIn.readString();

        // Counting sort
        final int ABC_SIZE = 256;
        int[] counts = new int[ABC_SIZE + 1];
        for (int i = 0; i < t.length(); ++i)
            counts[1 + t.charAt(i)]++;
        char[] sorted = new char[t.length()];
        for (int i = 0, j = 0; j < ABC_SIZE; ++j) {
            for (int k = 0; k < counts[j+1]; ++k) {
                sorted[i++] = (char) j;
            }
        }

        for (int i = 0; i < ABC_SIZE; ++i)
            counts[i+1] += counts[i];

        int[] next = new int[t.length()];
        for (int i = 0; i < t.length(); ++i) {
            int j = t.charAt(i);
            next[counts[j]] = i;
            ++counts[j];
        }

        for (int i = 0; i < t.length(); ++i) {
            BinaryStdOut.write(sorted[first]);
            first = next[first];
        }

        BinaryStdOut.close();
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
