
import edu.princeton.cs.algs4.TST;
import java.util.HashSet;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class BoggleSolver {
    private final TST<String> dictionary;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        TST<String> dict = new TST<>();
        for (String word : dictionary) {
            dict.put(word, "");
        }
        this.dictionary = dict;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        boolean[][] visited = new boolean[board.rows()][board.cols()];
        Set<String> words = new HashSet<>();

        class Searcher {
            void search(int row, int col, String prefix) {
                if (visited[row][col])
                    return;
                // TODO: we don't need a collection, just check
                if (prefix.length() > 0 && dictionary.keysWithPrefix(prefix) == null)
                    return;

                visited[row][col] = true;

                // TODO: any way to avoid creation of new strings?
                char nextLetter = board.getLetter(row, col);
                String candidate = prefix + nextLetter;
                if (nextLetter == 'Q')
                    candidate += 'U';
                if (candidate.length() > 2 && dictionary.contains(candidate) && !words.contains(candidate)) {
                    words.add(candidate);
                }

                if (row > 0) {
                    if (col > 0)
                        search(row-1, col-1, candidate);
                    search(row-1, col, candidate);
                    if (col+1 < board.cols())
                        search(row-1, col+1, candidate);
                }
                if (col > 0)
                    search(row, col-1, candidate);
                if (col+1 < board.cols())
                    search(row, col+1, candidate);
                if (row+1 < board.rows()) {
                    if (col > 0)
                        search(row+1, col-1, candidate);
                    search(row+1, col, candidate);
                    if (col+1 < board.cols())
                        search(row+1, col+1, candidate);
                }

                visited[row][col] = false;
            }
        }

        Searcher searcher = new Searcher();

        // Try starting from any position
        for (int row = 0; row < board.rows(); ++row) {
            for (int col = 0; col < board.cols(); ++col) {
                searcher.search(row, col, "");
            }
        }

        return words;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {

        switch (word.length()) {
            case 0:
            case 1:
            case 2:
                return 0;
            case 3:
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 5;
            default:
                return 11;
        }
    }
}
