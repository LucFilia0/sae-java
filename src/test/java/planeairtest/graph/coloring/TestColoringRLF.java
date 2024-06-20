package planeairtest.graph.coloring;

import static org.junit.Assert.* ;

import org.junit.Test;

import planeair.graph.coloring.ColoringRLF;
import planeair.graph.coloring.ColoringUtilities;

/**
 * Test class for the RLF coloring algorithm
 */
public class TestColoringRLF extends TestColoring {
    
    @Test
    public void testColoringRLF() {
        ColoringRLF.coloringRLF(testGraph1) ;
        assertEquals(2, testGraph1.getNode("1").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(3, testGraph1.getNode("2").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(2, testGraph1.getNode("3").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(3, testGraph1.getNode("4").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(2, testGraph1.getNode("5").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(3, testGraph1.getNode("6").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(1, testGraph1.getNode("7").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;

        ColoringRLF.coloringRLF(testGraph2) ;
        assertEquals(1, testGraph2.getNode("1").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(2, testGraph2.getNode("2").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(1, testGraph2.getNode("3").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(2, testGraph2.getNode("4").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(1, testGraph2.getNode("5").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(2, testGraph2.getNode("6").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(1, testGraph2.getNode("7").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(2, testGraph2.getNode("8").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(2, testGraph2.getNode("9").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(2, testGraph2.getNode("10").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
    }
}
