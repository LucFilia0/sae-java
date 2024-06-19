package planeairtest.graph.coloring;


//#region IMPORTS

	//#region JUNIT
	import org.junit.Before;
	import org.junit.Test;
	import static org.junit.Assert.*;
	//#endregion

	//#region JAVA
	import java.io.File;
import java.io.FileNotFoundException;

import planeair.exceptions.InvalidFileFormatException;
import planeair.graph.coloring.ColoringUtilities;
import planeair.graph.coloring.ColoringWelshPowell;
//#endregion
//#region PLANEAIR
	import planeair.graph.graphtype.TestGraph;
	import planeair.importation.ImportationTestGraph;
	//#endregion

//#endregion

/**
 * Test class for the Welsh Powell coloring algorithm
 */
public class TestColoringWelshPowell {
    
    /**
     * First graph we will test
     */
    private TestGraph testGraph1 ;

    /**
     * Second graph we will test
     */
    private TestGraph testGraph2 ;

    @Before
    public void setup() {
        testGraph1 = new TestGraph("test1") ;
        testGraph2 = new TestGraph("test2") ;
        try {
            ImportationTestGraph.importTestGraphFromFile(testGraph1, new File
                ("src/test/java/planeairtest/testfiles/testgraph1.txt"), 
                false) ;
            ImportationTestGraph.importTestGraphFromFile(testGraph2, new File
                ("src/test/java/planeairtest/testfiles/testgraph2.txt"), 
                false);
        }catch(FileNotFoundException fnfe) {
			System.err.println("File not found, you dummy");
		}catch(InvalidFileFormatException iffe) {
			fail("File is not well treated : " + iffe.getMessage());
		}
    }

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
