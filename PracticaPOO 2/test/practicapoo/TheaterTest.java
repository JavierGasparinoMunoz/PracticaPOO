/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package practicapoo;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Javierino
 */
public class TheaterTest {
    
    public TheaterTest() {
    }

    /**
     * Test of getFilm method, of class Theater.
     */
    @Test
    public void testGetFilm() {
        System.out.println("getFilm");
        Theater instance = null;
        Film expResult = null;
        Film result = instance.getFilm();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    
}
