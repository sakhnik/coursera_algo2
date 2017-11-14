
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
    private final Trie26 dictionary;

    private static class Trie26 {
        boolean isPresent = false;
        Object[] children = new Object[26];

        private Trie26 get(char c) {
            Trie26 sub = check(c);
            if (sub == null) {
                sub = new Trie26();
                children[c - 'A'] = sub;
            }
            return sub;
        }

        private Trie26 check(char c) {
            return (Trie26) children[c - 'A'];
        }

        private void add(String s, int offset) {
            if (offset == s.length()) {
                isPresent = true;
                return;
            }

            Trie26 sub = get(s.charAt(offset));
            sub.add(s, offset + 1);
        }

        void add(String s) {
            add(s, 0);
        }

        private boolean has(String s, int offset) {
            if (offset == s.length())
                return isPresent;
            Trie26 sub = check(s.charAt(offset));
            if (sub == null)
                return false;
            return sub.has(s, offset + 1);
        }

        boolean has(String s) {
            return has(s, 0);
        }
    }

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] words) {
        dictionary = new Trie26();
        for (String word : words) {
            dictionary.add(word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        boolean[][] visited = new boolean[board.rows()][board.cols()];
        Set<String> words = new HashSet<>();

        class Searcher {
            void search(int row, int col, String prefix, Trie26 t) {
                if (visited[row][col])
                    return;

                char nextLetter = board.getLetter(row, col);
                t = t.check(nextLetter);
                if (t == null)
                    return;

                // TODO: any way to avoid creation of new strings?
                String candidate = prefix + nextLetter;
                if (nextLetter == 'Q') {
                    t = t.check(nextLetter);
                    if (t == null)
                        return;
                    candidate += 'U';
                }

                if (candidate.length() > 2 && dictionary.has(candidate) && !words.contains(candidate)) {
                    words.add(candidate);
                }

                visited[row][col] = true;
                if (row > 0) {
                    if (col > 0)
                        search(row-1, col-1, candidate, t);
                    search(row-1, col, candidate, t);
                    if (col+1 < board.cols())
                        search(row-1, col+1, candidate, t);
                }
                if (col > 0)
                    search(row, col-1, candidate, t);
                if (col+1 < board.cols())
                    search(row, col+1, candidate, t);
                if (row+1 < board.rows()) {
                    if (col > 0)
                        search(row+1, col-1, candidate, t);
                    search(row+1, col, candidate, t);
                    if (col+1 < board.cols())
                        search(row+1, col+1, candidate, t);
                }

                visited[row][col] = false;
            }
        }

        Searcher searcher = new Searcher();

        // Try starting from any position
        for (int row = 0; row < board.rows(); ++row) {
            for (int col = 0; col < board.cols(); ++col) {
                searcher.search(row, col, "", dictionary);
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
