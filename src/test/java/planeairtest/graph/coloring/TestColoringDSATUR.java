package planeairtest.graph.coloring;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import planeair.graph.coloring.ColoringDSATUR;
import planeair.graph.coloring.ColoringUtilities;

/**
 * Test class for the DSATUR coloring algorithm
 */
public class TestColoringDSATUR extends TestColoring {
    @Test
    public void testColoringDSATUR() {
        ColoringDSATUR.coloringDsatur(testGraph1) ;
        assertEquals(2, testGraph1.getNode("1").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(3, testGraph1.getNode("2").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(2, testGraph1.getNode("3").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(3, testGraph1.getNode("4").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(2, testGraph1.getNode("5").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(3, testGraph1.getNode("6").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(1, testGraph1.getNode("7").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;

        ColoringDSATUR.coloringDsatur(testGraph2) ;
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
