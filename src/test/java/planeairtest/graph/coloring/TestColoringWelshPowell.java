package planeairtest.graph.coloring;


//#region IMPORTS
import org.junit.Test;
import static org.junit.Assert.*;

import planeair.graph.coloring.ColoringUtilities;
import planeair.graph.coloring.ColoringWelshPowell;
//#endregion

//#endregion

/**
 * Test class for the Welsh Powell coloring algorithm
 */
public class TestColoringWelshPowell extends TestColoring {

    @Test
    public void testColoringWelshPowell() {
        ColoringWelshPowell.coloringWelshPowell(testGraph1) ;
        assertEquals(2, testGraph1.getNode("1").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(3, testGraph1.getNode("2").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(2, testGraph1.getNode("3").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(3, testGraph1.getNode("4").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(2, testGraph1.getNode("5").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(3, testGraph1.getNode("6").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
        assertEquals(1, testGraph1.getNode("7").getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;

        ColoringWelshPowell.coloringWelshPowell(testGraph2) ;
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
