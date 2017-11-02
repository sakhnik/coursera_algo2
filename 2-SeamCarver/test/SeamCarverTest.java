/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.Picture;
import java.net.URL;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author sakhnik
 */
public class SeamCarverTest {
    
    public SeamCarverTest() {
    }

    @Test
    public void testFindVerticalSeam() {
        URL resource = SeamCarverTest.class.getResource("/6x5.png");
        Picture p = new Picture(resource.toString());
        SeamCarver carver = new SeamCarver(p);
        int[] seam = carver.findVerticalSeam();
        assertNotNull(seam);
        assertEquals(5, seam.length);
    }
}