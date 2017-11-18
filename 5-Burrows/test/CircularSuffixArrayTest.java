/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sakhnik
 */
public class CircularSuffixArrayTest {
    
    @Test
    public void test() {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        assertEquals(12, csa.length());

        assertEquals(11, csa.index(0));
        assertEquals(10, csa.index(1));
        assertEquals(7, csa.index(2));
        assertEquals(0, csa.index(3));
        assertEquals(3, csa.index(4));
        assertEquals(5, csa.index(5));
        assertEquals(8, csa.index(6));
        assertEquals(1, csa.index(7));
        assertEquals(4, csa.index(8));
        assertEquals(6, csa.index(9));
        assertEquals(9, csa.index(10));
        assertEquals(2, csa.index(11));
    }
}
