
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
public class MoveToFront {

    static class Encoder {
        char[] abc = new char[256];
        Encoder() {
            for (int i = 0; i < abc.length; ++i)
                abc[i] = (char) i;
        }
        int encode(char ch) {
            char cur = abc[0];
            int i = 1;
            for (; ch != cur; ++i) {
                char tmp = abc[i];
                abc[i] = cur;
                cur = tmp;
            }
            abc[0] = ch;
            return i - 1;
        }
    }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        Encoder encoder = new Encoder();
        while (!BinaryStdIn.isEmpty()) {
            BinaryStdOut.write((char) encoder.encode(BinaryStdIn.readChar()));
        }
    }

    static class Decoder {
        char[] abc = new char[256];
        Decoder() {
            for (int i = 0; i < abc.length; ++i)
                abc[i] = (char) i;
        }
        char decode(int i) {
            char cur = abc[i];
            while (i > 0) {
                abc[i] = abc[i-1];
                --i;
            }
            abc[0] = cur;
            return cur;
        }
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        Decoder decoder = new Decoder();
        while (!BinaryStdIn.isEmpty()) {
            BinaryStdOut.write(decoder.decode(BinaryStdIn.readChar()));
        }
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-"))
            encode();
        else if (args[0].equals("+"))
                decode();
    }
}
