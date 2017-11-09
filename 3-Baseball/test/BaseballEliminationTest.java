/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sakhnik
 */
public class BaseballEliminationTest {
    
    public BaseballEliminationTest() {
    }

    @Test
    public void testNumberOfTeams() {
        URL resource = BaseballEliminationTest.class.getResource("/teams4.txt");
        BaseballElimination be = new BaseballElimination(resource.toString());
    }
}
