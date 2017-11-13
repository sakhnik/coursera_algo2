
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
    private static int[] WORD_SCORE;
    private final TST<String> dictionary;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (WORD_SCORE == null) {
            WORD_SCORE = new int[8];
            WORD_SCORE[0] = 0;
            WORD_SCORE[1] = 0;
            WORD_SCORE[2] = 0;
            WORD_SCORE[3] = 1;
            WORD_SCORE[4] = 1;
            WORD_SCORE[5] = 2;
            WORD_SCORE[6] = 3;
            WORD_SCORE[7] = 5;
        }

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
                if (row < 0 || row >= board.rows())
                    return;
                if (col < 0 || col >= board.cols())
                    return;
                if (visited[row][col])
                    return;
                if (dictionary.longestPrefixOf(prefix).length() < prefix.length())
                    return;

                visited[row][col] = true;

                char nextLetter = board.getLetter(row, col);
                String candidate = prefix + nextLetter;
                if (nextLetter == 'Q')
                    candidate += 'U';
                if (dictionary.contains(candidate) && !words.contains(candidate)) {
                    words.add(candidate);
                }

                for (int drow = -1; drow <= 1; ++drow) {
                    for (int dcol = -1; dcol <= 1; ++dcol) {
                        search(row + drow, col + dcol, candidate);
                    }
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
        if (word.length() >= WORD_SCORE.length)
            return 11;
        return WORD_SCORE[word.length()];
    }
}
