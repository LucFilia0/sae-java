package planeairtest.importation;

//#region IMPORTS

	//#region JUNIT
	import org.junit.Before;
	import org.junit.Test;
	import static org.junit.Assert.*;
	//#endregion

	//#region JAVA
	import java.io.File;
	//#endregion

	//#region PLANEAIR
	import planeair.graph.graphtype.TestGraph;
	import planeair.importation.ImportationTestGraph;
	//#endregion

	//#region EXCEPTIONS
	import planeair.exceptions.InvalidFileFormatException;
	import java.io.FileNotFoundException;
	//#endregion

//#endregion

/**
 * Test for the importation of the TestGraphs
 * 
 * @author Luc le Manifik
 */
public class TestGraphImportationTest {

	//#region ATTRIBUTES

	/**
	 * The TestGraph we wish to test
	 */
	private TestGraph testGraph;

	//#endregion
	
	/**
	 * This procedure is launched before the importation test
	 * 
	 * @author Luc le Manifik
	 */
	@Before
	public void init() {
		this.testGraph = new TestGraph("Test-TestGraph");
	}
	
	/**
	 * This method tests the importation of the TestGraph
	 * 
	 * @author Luc le Manifik
	 */
	@Test
	public void testImportTestGraphFromFile() {
		File file = null;
		try {
			file = new File("src/test/java/planeairtest/testfiles/shitty-graph1.txt");
		}catch(NullPointerException e) {
			e.printStackTrace();
		}

		try {
			ImportationTestGraph.importTestGraphFromFile(testGraph, file, false);
		}catch(FileNotFoundException fnfe) {
			System.err.println("File not found, you dummy");
		}catch(InvalidFileFormatException iffe) {
			fail("File is not well treated : " + iffe.getMessage());
		}

		assertTrue(this.testGraph.getNbMaxNodes() == 11);
		assertTrue(this.testGraph.getNodeCount() == 11);
		assertTrue(this.testGraph.getEdgeCount() == 20);
		assertTrue(this.testGraph.getKMax() == 2);
	}
}
