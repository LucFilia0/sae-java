package planeairtest.graph.coloring;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import planeair.graph.coloring.ColoringUtilities;

/**
 * Test class for methods in ColoringUtilities
 */
public class TestColoringUtilities extends TestColoring {
    @Test
    public void testGestLeastConflictColor() {
        testGraph1.getNode("1").setAttribute(
            ColoringUtilities.NODE_COLOR_ATTRIBUTE, 2);
        testGraph1.getNode("2").setAttribute(
            ColoringUtilities.NODE_COLOR_ATTRIBUTE, 2);
        testGraph1.getNode("3").setAttribute(
            ColoringUtilities.NODE_COLOR_ATTRIBUTE, 1); 
        testGraph1.getNode("4").setAttribute(
            ColoringUtilities.NODE_COLOR_ATTRIBUTE, 2);
        testGraph1.getNode("6").setAttribute(
            ColoringUtilities.NODE_COLOR_ATTRIBUTE, 1);
        testGraph1.getNode("7").setAttribute(
            ColoringUtilities.NODE_COLOR_ATTRIBUTE, 1);

        assertArrayEquals(new int[]{2, 1}, ColoringUtilities
            .getLeastConflictingColor(testGraph1.getNode("5"), 2)) ;
    }
}
