/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.In;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sakhnik
 */
public class BoggleSolverTest {
    
    public BoggleSolverTest() {
    }

    @Test
    public void testGetAllValidWords() {
        In in = new In(getClass().getResource("/dictionary-algs4.txt"));
        BoggleSolver solver = new BoggleSolver(in.readAllStrings());
        BoggleBoard board = new BoggleBoard(getClass().getResource("/board4x4.txt").toString());
        Iterable<String> words = solver.getAllValidWords(board);

        int count = 0;
        for (String w : words) {
            count += 1;
        }
        assertEquals(29, count);
    }
}
